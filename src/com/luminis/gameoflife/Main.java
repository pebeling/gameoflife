package com.luminis.gameoflife;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.util.Duration;

public class Main extends Application {
	private GuiField field = new GuiField(30,30);
	private SimpleBooleanProperty evolutionStarted = new SimpleBooleanProperty();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		KeyFrame frame = new KeyFrame(Duration.millis(50), e -> field.evolve());
		Timeline timer = new Timeline(frame);
		timer.setCycleCount(Timeline.INDEFINITE);
		evolutionStarted.addListener((ObservableValue<? extends Boolean> value, Boolean oldValue, Boolean newValue) -> {
			if(newValue) { timer.play(); } else { timer.stop(); }
		});
		evolutionStarted.set(false);

		ScrollPane sPane = new ScrollPane(field.fieldGrid);
		sPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		sPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		sPane.setPannable(true);

		Button evolveButton = new Button();
		evolveButton.setText("Evolve");
		evolveButton.setOnAction(e -> field.evolve());
		evolveButton.setMaxWidth(Double.MAX_VALUE);
		evolveButton.disableProperty().bind(evolutionStarted);

		Button loadGliderButton = new Button();
		loadGliderButton.setText("Add Glider");
		loadGliderButton.setOnAction(e -> field.insertIntoField(1, 1, new String[]{
						".O.",
						"..O",
						"OOO"
				}));
		loadGliderButton.setMaxWidth(Double.MAX_VALUE);

		Button loadSpaceshipButton = new Button();
		loadSpaceshipButton.setText("Add Spaceship");
		loadSpaceshipButton.setOnAction(e -> field.insertIntoField(6, 3, new String[]{
						"O..O.",
						"....O",
						"O...O",
						".OOOO"
				}));
		loadSpaceshipButton.setMaxWidth(Double.MAX_VALUE);

		Button resetFieldButton = new Button();
		resetFieldButton.setText("Reset");
		resetFieldButton.setOnAction(e -> field.setField(0, 0, new String[]{}));
		resetFieldButton.setMaxWidth(Double.MAX_VALUE);

		Button startEvolutionButton = new Button();
		startEvolutionButton.setText("Start");
		startEvolutionButton.setMaxWidth(Double.MAX_VALUE);
		startEvolutionButton.disableProperty().bind(evolutionStarted);
		startEvolutionButton.setOnAction(e -> evolutionStarted.set(true));

		Button stopEvolutionButton = new Button();
		stopEvolutionButton.setText("Stop");
		stopEvolutionButton.setMaxWidth(Double.MAX_VALUE);
		stopEvolutionButton.setDisable(true);
		stopEvolutionButton.disableProperty().bind(Bindings.not(evolutionStarted));
		stopEvolutionButton.setOnAction(e -> evolutionStarted.set(false));

		Slider evolutionSpeed = new Slider(5, 500, 50);
		Label evolutionSpeedLabel = new Label("Speed: ");
		evolutionSpeed.setOrientation(Orientation.HORIZONTAL);
		evolutionSpeed.setPrefHeight(50);
		evolutionSpeed.valueProperty().addListener(
				(observable, oldValue, newValue) -> resetTimer(timer, newValue)
		);

		Slider fieldScale = new Slider(5, 50, 20);
		Label fieldScaleLabel = new Label("Scale: ");
		fieldScale.setOrientation(Orientation.HORIZONTAL);
		fieldScale.setPrefHeight(50);
		fieldScale.valueProperty().addListener(
				(observable, oldValue, newValue) -> {
					field.cellGuiSize = newValue.intValue();
					field.update();
				});

		GridPane sliderPane = new GridPane();
		sliderPane.add(evolutionSpeedLabel, 0, 0);
		sliderPane.add(evolutionSpeed, 1, 0);
		sliderPane.add(fieldScaleLabel, 0, 1);
		sliderPane.add(fieldScale, 1, 1);
		ColumnConstraints column1 = new ColumnConstraints();
		ColumnConstraints column2 = new ColumnConstraints();
		column1.setHgrow(Priority.NEVER);
		column2.setHgrow(Priority.ALWAYS);
		sliderPane.getColumnConstraints().addAll(column1, column2);

		VBox leftPanel = new VBox(sPane, sliderPane);
		VBox rightPanel = new VBox(loadGliderButton, loadSpaceshipButton, evolveButton, startEvolutionButton, stopEvolutionButton, resetFieldButton);
		rightPanel.setMinWidth(120);

		HBox rootPane = new HBox(leftPanel, rightPanel);
		rootPane.setPadding(new Insets(5, 5, 5, 5));
		rootPane.setSpacing(5);

		Scene scene = new Scene(rootPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Game Of Life");
		primaryStage.show();
		primaryStage.setMaxHeight(primaryStage.getHeight());
		primaryStage.setMaxWidth(primaryStage.getWidth());

	}

	private void resetTimer(Timeline timer, Number newValue) {
		KeyFrame k = new KeyFrame(Duration.millis((Double) newValue), e -> field.evolve());
		Animation.Status status = timer.getStatus();
		timer.stop();
		timer.getKeyFrames().setAll(k);
		if (status.equals(Animation.Status.RUNNING)) {
			timer.play();
		}
	}
}
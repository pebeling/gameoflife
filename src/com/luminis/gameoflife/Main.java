package com.luminis.gameoflife;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
	private GuiField field = new GuiField(20,20);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		KeyFrame frame = new KeyFrame(Duration.millis(50), e -> field.evolve());
		Timeline timer = new Timeline(frame);
		timer.setCycleCount(Timeline.INDEFINITE);

		ScrollPane sPane = new ScrollPane(field.fieldGrid);
		sPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		sPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

		VBox controlPanel = new VBox();

		Button evolveButton = new Button();
		evolveButton.setText("Evolve");
		evolveButton.setOnAction(e -> evolveButtonClick());
		evolveButton.setMaxWidth(Double.MAX_VALUE);


		Button loadGliderButton = new Button();
		loadGliderButton.setText("Add Glider");
		loadGliderButton.setOnAction(e -> loadGliderButtonClick());
		loadGliderButton.setMaxWidth(Double.MAX_VALUE);

		Button loadSpaceshipButton = new Button();
		loadSpaceshipButton.setText("Add Spaceship");
		loadSpaceshipButton.setOnAction(e -> loadSpaceshipButtonClick());
		loadSpaceshipButton.setMaxWidth(Double.MAX_VALUE);

		Button resetFieldButton = new Button();
		resetFieldButton.setText("Reset");
		resetFieldButton.setOnAction(e -> resetFieldButtonClick());
		resetFieldButton.setMaxWidth(Double.MAX_VALUE);

		Button startEvolutionButton = new Button();
		startEvolutionButton.setText("Start");
		startEvolutionButton.setMaxWidth(Double.MAX_VALUE);

		Button stopEvolutionButton = new Button();
		stopEvolutionButton.setText("Stop");
		stopEvolutionButton.setMaxWidth(Double.MAX_VALUE);
		stopEvolutionButton.setDisable(true);

		Slider evolutionSpeed = new Slider(5, 500, 50);
		evolutionSpeed.setOrientation(Orientation.HORIZONTAL);
		evolutionSpeed.setPrefHeight(100);
		evolutionSpeed.valueProperty().addListener(
				(observable, oldValue, newValue) -> resetTimer(timer, newValue)
		);

		//Label evolutionSpeedLabel = new Label("Speed:");

		controlPanel.getChildren().addAll(loadGliderButton, loadSpaceshipButton, evolveButton, startEvolutionButton, stopEvolutionButton, resetFieldButton);

		BorderPane pane = new BorderPane();
		pane.setLeft(sPane);
		pane.setMargin(sPane, new Insets(5, 5, 0, 5));
		pane.setRight(controlPanel);
		pane.setMargin(controlPanel, new Insets(5, 5, 0, 0));
		pane.setBottom(evolutionSpeed);


		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Game Of Life");
		primaryStage.show();
		primaryStage.setMaxHeight(primaryStage.getHeight());
		primaryStage.setMaxWidth(primaryStage.getWidth());

		startEvolutionButton.setOnAction(
				e -> {
					evolveButton.setDisable(true);
					startEvolutionButton.setDisable(true);
					stopEvolutionButton.setDisable(false);
					timer.play();

				});
		stopEvolutionButton.setOnAction(
				e -> {
					evolveButton.setDisable(false);
					stopEvolutionButton.setDisable(true);
					startEvolutionButton.setDisable(false);
					timer.stop();
				});
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

	private void resetFieldButtonClick() {
		field.setField(0, 0, new String[]{});
	}

	private void evolveButtonClick() {
		field.evolve();
	}

	private void loadGliderButtonClick() {
		field.insertIntoField(1, 1, new String[]{
				".O.",
				"..O",
				"OOO"
		});
	}

	private void loadSpaceshipButtonClick() {
		field.insertIntoField(6, 3, new String[]{
				"O..O.",
				"....O",
				"O...O",
				".OOOO"
		});
	}
}

class GuiCell extends Rectangle {
	int verticalCoordinate;
	int horizontalCoordinate;

	GuiCell(int verticalCoordinate, int horizontalCoordinate, int size) {
		super(size, size);
		this.verticalCoordinate = verticalCoordinate;
		this.horizontalCoordinate = horizontalCoordinate;
	}
}

class GuiField extends Field {
	private List<GuiCell> guiCells;
	GridPane fieldGrid;

	GuiField(int height, int width) {
		super(height, width);

		guiCells = new ArrayList<>();

		fieldGrid = new GridPane();
		fieldGrid.setHgap(1);
		fieldGrid.setVgap(1);

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				GuiCell cell = new GuiCell(i, j, 20);
				cell.setFill(Color.BLACK);
				cell.setOnMousePressed(e -> toggleCell((GuiCell) e.getSource()));
				guiCells.add(cell);
				fieldGrid.add(cell, j, i);
			}
		}
	}

	private void toggleCell (GuiCell cell) {
		setCell(cell, !getCell(cell));
	}

	private void update() {
		for(GuiCell cell : guiCells) {
			cell.setFill(getCell(cell.verticalCoordinate, cell.horizontalCoordinate) ? Color.RED : Color.BLACK);
		}
	}

	@Override
	void evolve() {
		super.evolve();
		update();
	}

	@Override
	void setField(int verticalOffset, int horizontalOffset, String[] lines) {
		super.setField(verticalOffset, horizontalOffset, lines);
		update();
	}

	@Override
	void insertIntoField(int verticalOffset, int horizontalOffset, String[] lines) {
		super.insertIntoField(verticalOffset, horizontalOffset, lines);
		update();
	}

	private boolean getCell(GuiCell cell) {
		return super.getCell(cell.verticalCoordinate, cell.horizontalCoordinate);
	}

	private void setCell(GuiCell cell, boolean value) {
		super.setCell(cell.verticalCoordinate, cell.horizontalCoordinate, value);
		cell.setFill(value ? Color.RED : Color.BLACK);
	}
}

package com.luminis.gameoflife;

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

public class Main extends Application {
	private Field current_field = new Field(20,20);
	private Rectangle[][] r = new Rectangle[current_field.height][current_field.width];

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		TilePane lifeTile = new TilePane();
		lifeTile.setHgap(1);
		lifeTile.setVgap(1);
		lifeTile.setPrefColumns(current_field.height);
		lifeTile.setPrefRows(current_field.width);

		for (int i = 0; i < current_field.height; i++) {
			for (int j=0; j < current_field.width; j++) {
				r[i][j] = new Rectangle(20,20);
				r[i][j].setFill(Color.BLACK);

				lifeTile.getChildren().add(r[i][j]);
			}
		}

		ScrollPane sPane = new ScrollPane(lifeTile);
		sPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		sPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

		VBox buttons = new VBox();

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
		
		buttons.getChildren().addAll(loadGliderButton, loadSpaceshipButton, evolveButton, startEvolutionButton, stopEvolutionButton, resetFieldButton);

		BorderPane pane = new BorderPane();
		pane.setLeft(sPane);
		pane.setMargin(sPane, new Insets(5, 5, 5, 5));
		pane.setRight(buttons);
		pane.setMargin(buttons, new Insets(5, 5, 5, 0));

		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Game Of Life");
		primaryStage.show();
		primaryStage.setMaxHeight(primaryStage.getHeight());
		primaryStage.setMaxWidth(primaryStage.getWidth());

		KeyFrame k = new KeyFrame(Duration.millis(50),
			e -> {
				current_field.evolve();
				updateTileColors();

			} );
		Timeline t = new Timeline(k);
		t.setCycleCount(Timeline.INDEFINITE);
		startEvolutionButton.setOnAction(
				e -> {
					evolveButton.setDisable(true);
					startEvolutionButton.setDisable(true);
					stopEvolutionButton.setDisable(false);
					t.play();

				});
		stopEvolutionButton.setOnAction(
				e -> {
					evolveButton.setDisable(false);
					stopEvolutionButton.setDisable(true);
					startEvolutionButton.setDisable(false);
					t.stop();
				});
	}

	private void resetFieldButtonClick() {
		current_field.setField(0, 0, new String[]{});
		updateTileColors();
	}

	private void updateTileColors() {
		for (int i = 0; i < current_field.height; i++) {
			for (int j=0; j < current_field.width; j++) {
				r[i][j].setFill(current_field.getCell(i,j) ? Color.RED : Color.BLACK);
			}
		}
	}

	private void evolveButtonClick() {
		current_field.evolve();
		updateTileColors();
	}

	private void loadGliderButtonClick() {
		current_field.insertIntoField(1, 1, new String[]{
				".O.",
				"..O",
				"OOO"
		});
		updateTileColors();
	}

	private void loadSpaceshipButtonClick() {
		current_field.insertIntoField(6, 3, new String[]{
				"O..O.",
				"....O",
				"O...O",
				".OOOO"
		});
		updateTileColors();
	}
}

package com.luminis.gameoflife;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.geometry.*;

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
				StackPane s = new StackPane(r[i][j]);
				lifeTile.getChildren().add(s);
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
		loadGliderButton.setText("Load Glider");
		loadGliderButton.setOnAction(e -> loadGliderButtonClick());
		loadGliderButton.setMaxWidth(Double.MAX_VALUE);

		buttons.getChildren().addAll(evolveButton, loadGliderButton);

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
}

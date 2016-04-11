package com.luminis.gameoflife;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class GuiField {
	private Field field;
	GridPane fieldGrid;
	SimpleIntegerProperty guiCellSize = new SimpleIntegerProperty(20);

	GuiField(int height, int width) {
		field = new Field(height, width);

		guiCellSize.addListener(e -> update());

		fieldGrid = new GridPane();
		fieldGrid.setHgap(1);
		fieldGrid.setVgap(1);

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Rectangle cell = new Rectangle(guiCellSize.getValue(), guiCellSize.getValue());
				cell.setFill(Color.BLACK);
				cell.setOnMousePressed(
						e -> {
							Rectangle source = ((Rectangle) e.getSource());
							int verticalCoordinate = GridPane.getRowIndex(source);
							int horizontalCoordinate = GridPane.getColumnIndex(source);
							boolean cellState = field.getCell(verticalCoordinate, horizontalCoordinate);
							field.setCell(verticalCoordinate, horizontalCoordinate, !cellState);
							source.setFill( !cellState ? Color.RED : Color.BLACK);

						});
				fieldGrid.add(cell, j, i);
			}
		}
	}

	private void update() {
		for(Node fieldChild : fieldGrid.getChildren()) {
			int verticalCoordinate = GridPane.getRowIndex(fieldChild);
			int horizontalCoordinate = GridPane.getColumnIndex(fieldChild);
			Rectangle cell = ((Rectangle) fieldChild);
			cell.setFill(field.getCell(verticalCoordinate, horizontalCoordinate) ? Color.RED : Color.BLACK);
			cell.setFill(field.getCell(verticalCoordinate, horizontalCoordinate) ? Color.RED : Color.BLACK);
			cell.setHeight(guiCellSize.getValue());
			cell.setWidth(guiCellSize.getValue());
		}
	}

	void evolve() {
		field.evolve();
		update();
	}

	void setField(int verticalOffset, int horizontalOffset, String[] lines) {
		field.setField(verticalOffset, horizontalOffset, lines);
		update();
	}

	void insertIntoField(int verticalOffset, int horizontalOffset, String[] lines) {
		field.insertIntoField(verticalOffset, horizontalOffset, lines);
		update();
	}
}

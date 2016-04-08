package com.luminis.gameoflife;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class GuiField extends Field {
	GridPane fieldGrid;

	GuiField(int height, int width) {
		super(height, width);

		fieldGrid = new GridPane();
		fieldGrid.setHgap(1);
		fieldGrid.setVgap(1);

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Rectangle cell = new Rectangle(20, 20);
				cell.setFill(Color.BLACK);
				cell.setOnMousePressed(
						e -> {
							Rectangle source = ((Rectangle) e.getSource());
							int verticalCoordinate = GridPane.getRowIndex(source);
							int horizontalCoordinate = GridPane.getColumnIndex(source);
							boolean cellState = super.getCell(verticalCoordinate, horizontalCoordinate);
							super.setCell(verticalCoordinate, horizontalCoordinate, !cellState);
							source.setFill( !cellState ? Color.RED : Color.BLACK);

						});
				fieldGrid.add(cell, j, i);
			}
		}
	}

	private void update() {
		for(Node cell : fieldGrid.getChildren()) {
			int verticalCoordinate = GridPane.getRowIndex(cell);
			int horizontalCoordinate = GridPane.getColumnIndex(cell);
			((Rectangle) cell).setFill(super.getCell(verticalCoordinate, horizontalCoordinate) ? Color.RED : Color.BLACK);
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
}

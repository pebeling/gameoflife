package com.luminis.gameoflife;

public class Field {
	private int width, height;
	private boolean[][] cells;

	Field() {
		width = 16;
		height = 10;
		cells = new boolean[height][width]; // when using boolean[], array is initialized with false; NB order as for matrices
	}

	void show() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				System.out.print(cells[i][j] ? "O" : ".");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}

	private int wrapCoordinate(int coordinate, int upperBound) { // implicit lower bound 0
		coordinate = coordinate % upperBound;
		return coordinate < 0 ? coordinate + upperBound : coordinate; // this step needed because of negative remainder in case of negative coordinate
	}

	boolean getCell( int cellX, int cellY ) {
		// wrap coordinates, i.e. playing field is a torus
		return cells[wrapCoordinate(cellX, height)][wrapCoordinate(cellY, width)];
	}

	void setCell( int cellX, int cellY, boolean value ) {
		// wrap coordinates, i.e. cells live on a torus.
		cells[wrapCoordinate(cellX, height)][wrapCoordinate(cellY, width)] = value;
	}

	// Fills a field using an array of strings such as { ".O.","..O","OOO" }, where O correspond to live cells. All other characters will be interpreted as dead cells
	// We silently clip strings longer than width and drop strings with index >= height. We pad the field with dead cells
	// cellX, cellY for offset
	void setField(int offsetX, int offsetY,  String[] lines ) {
		for(int i = 0; i < height; i++) {
			if (i < lines.length) for (int j = 0; j < width; j++) setCell(i + offsetX, j + offsetY, j < lines[i].length() && lines[i].charAt(j) == 'O');
			else for (int j = 0; j < width; j++) setCell(i + offsetX, j + offsetY, false);
		}
	}

	boolean equals(Field field) {
		boolean result = field.width == this.width && field.height == this.height;
		for(int i = 0; i < height && result ; i++)
			for (int j = 0; j < width && result; j++) result = this.cells[i][j] == field.cells[i][j];
		return result;
	}

	private int numberOfNeighbours( int cellX, int cellY ) {
		int total = 0;
		for(int i = -1; i <= 1 ; i++) {
			for(int j = -1; j <= 1; j++) {
				if ( ( i != 0 || j != 0 ) && getCell(cellX + i, cellY + j) ) total++; // count only neighbours, not the cell itself
			}
		}
		return total;
	}

	void evolve() {
		Field evolvedField = new Field();
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				int numberOfNeighbours = numberOfNeighbours(i, j);
				boolean newStatus;
				if ( cells[i][j] ) { // case living cell
					if ( numberOfNeighbours > 3 ) newStatus = false; // death because of overpopulation
					else if ( numberOfNeighbours < 2 ) newStatus = false; // death because of under-population
					else newStatus = true; // stays alive if there are 2 or 3 live neighbours
				} else { // case dead cell
					if ( numberOfNeighbours == 3 ) newStatus = true; // birth
					else newStatus = false; // stays dead
				}
				evolvedField.setCell(i, j, newStatus);
			}
		}
		this.cells = evolvedField.cells;
	}
}


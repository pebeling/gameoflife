package com.luminis.gameoflife;

public class Field {
	public static final boolean ALIVE = true;
	public static final boolean DEAD = false;
	private int width, height;
	private boolean[][] cells;

	Field() {
		this(10,16);
	}

	Field(int height, int width) {
		this.width = Math.max(0, width);
		this.height = Math.max(0, height);
		cells = new boolean[this.height][this.width]; // when using boolean[], the array is initialized with "false"; NB index order as for matrices
	}

	Field(String[] lines) {
		int maxStringWidth = 0;
		for(String s: lines) {
			maxStringWidth = Math.max(maxStringWidth, s.length());
		}
		width = maxStringWidth;
		height = lines.length;
		cells = new boolean[height][width];
		setField(0, 0, lines);
	}

	String[] stringify() {
		String[] lines = new String[height];
		java.util.Arrays.fill(lines, "");
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				lines[i] = lines[i] + (cells[i][j] ? "O" : ".");
			}
		}
		return lines;
	}

	@Override
	public String toString() {
		return String.join("\n", stringify());
	}

	private int wrapCoordinate(int coordinate, int upperBound) {
		// wrap coordinates between 0 and upperBound
		coordinate = coordinate % upperBound;
		return coordinate < 0 ? coordinate + upperBound : coordinate; // this step needed because of negative remainder in case of negative coordinate
	}

	boolean getCell( int cellVerticalCoordinate, int cellHorizontalCoordinate ) {
		return cells[wrapCoordinate(cellVerticalCoordinate, height)][wrapCoordinate(cellHorizontalCoordinate, width)];
	}

	void setCell( int cellVerticalCoordinate, int cellHorizontalCoordinate, boolean value ) {
		cells[wrapCoordinate(cellVerticalCoordinate, height)][wrapCoordinate(cellHorizontalCoordinate, width)] = value;
	}

	// Fills a field using an array of strings such as { ".O.","..O","OOO" }, where O correspond to live cells. All other characters will be interpreted as dead cells
	// We silently clip strings longer than width and drop strings with index >= height. We pad the field with dead cells
	void setField(int verticalOffset, int horizontalOffset, String[] lines ) {
		for(int i = 0; i < height; i++) {
			if (i < lines.length) {
				for (int j = 0; j < width; j++) {
					setCell(i + verticalOffset, j + horizontalOffset, j < lines[i].length() && lines[i].charAt(j) == 'O');
				}
			} else {
				for (int j = 0; j < width; j++) {
					setCell(i + verticalOffset, j + horizontalOffset, DEAD);
				}
			}
		}
	}

	void insertIntoField(int verticalOffset, int horizontalOffset, String[] lines ) {
		for(int i = 0; i < lines.length; i++) {
			for (int j = 0; j < lines[i].length(); j++) {
				setCell(i + verticalOffset, j + horizontalOffset, lines[i].charAt(j) == 'O' || cells[i + verticalOffset][j + horizontalOffset]);
			}
		}
	}

	boolean equals(Field field) {
		boolean result = field.width == this.width && field.height == this.height;
		for(int i = 0; i < height && result ; i++) {
			for (int j = 0; j < width && result; j++) {
				result = this.cells[i][j] == field.cells[i][j];
			}
		}
		return result;
	}

	private int numberOfNeighbours( int cellVerticalCoordinate, int cellHorizontalCoordinate ) {
		int total = 0;
		for(int i = -1; i <= 1 ; i++) {
			for(int j = -1; j <= 1; j++) {
				if ( ( i != 0 || j != 0 ) && getCell(cellVerticalCoordinate + i, cellHorizontalCoordinate + j) ) total++; // count only neighbours, not the cell itself
			}
		}
		return total;
	}

	void evolve() {
		Field evolvedField = new Field(height, width);
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				int numberOfNeighbours = numberOfNeighbours(i, j);
				evolvedField.setCell(i, j, (cells[i][j] && !(numberOfNeighbours > 3) && !(numberOfNeighbours < 2)) || (!cells[i][j] && (numberOfNeighbours == 3)));
			}
		}
		this.cells = evolvedField.cells;
	}
}


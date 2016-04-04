package com.luminis.gameoflife;

import java.security.cert.X509Certificate;
import java.util.Arrays;

/**
 * Created by paul on 04/04/16.
 */
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
		return coordinate < 0 ? coordinate + upperBound : coordinate; // this step because of negative remainder in case of negative coordinate
	}

	boolean get_cell( int cell_x, int cell_y ) {
		// wrap coordinates, i.e. playing field is a torus
		return cells[wrapCoordinate(cell_x, height)][wrapCoordinate(cell_y, width)];
	}

	void set_cell( int cell_x, int cell_y, boolean value ) {
		// wrap coordinates, i.e. playing field is a torus.
		cells[wrapCoordinate(cell_x, height)][wrapCoordinate(cell_y, width)] = value;
	}

	private int numberOfNeighbours( int cell_x, int cell_y ) {
		int total = 0;
		for(int i = -1; i <= 1 ; i++) {
			for(int j = -1; j <= 1; j++) {
				if ( ( i != 0 || j != 0 ) && get_cell(cell_x+i, cell_y+j) ) total++; // count only neighbours
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
				evolvedField.set_cell(i, j, newStatus);
			}
		}
		this.cells = evolvedField.cells;
	}
}


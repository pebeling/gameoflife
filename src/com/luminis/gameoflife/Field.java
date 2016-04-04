package com.luminis.gameoflife;

import java.security.cert.X509Certificate;

/**
 * Created by paul on 04/04/16.
 */
public class Field {
	private int width, height;
	private boolean[][] cells;
	Field() {
		width = 16;
		height = 10;
		cells = new boolean[height][width]; // when using boolean[], array is initialized with false

		// Glider pattern, manually put here for now
		cells[1][3]=true;
		cells[2][4]=true;
		cells[3][2]=true;
		cells[3][3]=true;
		cells[3][4]=true;
	}
	void show() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				System.out.print(cells[i][j] ? "O" : ".");
			}
			System.out.print("\n");
		}
	}
//	int neighbour_count( int cell_x, int cell_y ) {
//
//	}
//	void evolve() {
//
//	}
}


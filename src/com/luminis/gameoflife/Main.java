package com.luminis.gameoflife;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws java.io.IOException {
		Field current_field = new Field();

		String[] glider = {
				".O.",
				"..O",
				"OOO"
		};
		current_field.setField(1,1,glider);

		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader keyboard = new BufferedReader(input);
		String line = "";
		while ( line.equals("") ) {
			current_field.show();
			current_field.evolve();
			line = keyboard.readLine();
		}
	}
}

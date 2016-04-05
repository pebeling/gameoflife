package com.luminis.gameoflife;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StreamCorruptedException;

public class Main {
	public static void main(String[] args) throws java.io.IOException {
		Field current_field = new Field(10,10);

		current_field.setField(1, 1, new String[]{
				".O.",
				"..O",
				"OOO"
		});

		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader keyboard = new BufferedReader(input);
		String line = "";
		while ( line.equals("") ) {
			current_field.show();
			current_field.evolve();
			System.out.println("Press enter for next generation. Any other key + enter exits.");
			line = keyboard.readLine();
		}
	}
}

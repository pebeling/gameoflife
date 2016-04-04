package com.luminis.gameoflife;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws java.io.IOException {
		Field current_field = new Field();
		// Glider pattern
		current_field.set_cell(1,3,true);
		current_field.set_cell(2,4,true);
		current_field.set_cell(3,2,true);
		current_field.set_cell(3,3,true);
		current_field.set_cell(3,4,true);

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

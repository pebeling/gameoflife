package com.luminis.gameoflife;

import org.junit.Test;

import static org.junit.Assert.*;

public class FieldTest {
	@Test
	public void testCoordinateWrappingLow() {
		Field playingField = new Field();
		playingField.setCell(9,15,true); // places live cell at the extreme right lower corner of the field
		assertEquals(playingField.getCell(-1,-1), true);
	}

	@Test
	public void testCoordinateWrappingHigh() {
		Field playingField = new Field();
		playingField.setCell(9,15,true); // places live cell at the extreme right lower corner of the field
		assertEquals(playingField.getCell(19,31), true);
	}

	@Test
	public void gliderPeriodTest() {
		// After four generations the glider regained its original shape and has moved down and to the right by one step
		Field playingField1 = new Field();
		Field playingField2 = new Field();

		String[] glider = {
				".O.",
				"..O",
				"OOO"
		};
		playingField1.setField(0,0,glider);
		playingField2.setField(1,1,glider);
		for (int i = 0; i < 4; i++) playingField1.evolve();
		assertEquals(playingField1.equals(playingField2), true);
	}

	@Test
	public void gliderOneStepTest() {
		Field playingField1 = new Field();
		Field playingField2 = new Field();

		String[] glider = {
				".O.",
				"..O",
				"OOO"
		};
		String[] gliderNextGen = {
				"O.O",
				".OO",
				".O."
		};
		playingField1.setField(0,0,glider);
		playingField2.setField(1,0,gliderNextGen);
		playingField1.evolve();
		assertEquals(playingField1.equals(playingField2), true);
	}
}
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
		Field playingFieldFirstGen = new Field();
		Field playingFieldFourthGen = new Field();

		playingFieldFirstGen.setField(0, 0, new String[]{
				".O.",
				"..O",
				"OOO"
		});
		playingFieldFourthGen.setField(1, 1, new String[]{
				".O.",
				"..O",
				"OOO"
		});
		for (int i = 0; i < 4; i++) playingFieldFirstGen.evolve();
		assertEquals(playingFieldFirstGen.equals(playingFieldFourthGen), true);
	}

	@Test
	public void gliderOneStepTest() {
		Field playingFieldFirstGen = new Field();
		Field playingFieldNextGen = new Field();

		playingFieldFirstGen.setField(0, 0, new String[]{
				".O.",
				"..O",
				"OOO"
		});
		playingFieldFirstGen.setField(1, 0, new String[]{
				"O.O",
				".OO",
				".O."
		});
		playingFieldFirstGen.evolve();
		assertEquals(playingFieldFirstGen.equals(playingFieldNextGen), true);
	}
}
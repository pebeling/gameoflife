package com.luminis.gameoflife;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by paul on 04/04/16.
 */
public class FieldTest {
	@Test
	public void testCoordinateWrappingLow() {
		Field playingField = new Field();
		playingField.set_cell(9,15,true); // places live cell at the extreme right lower corner of the field
		assertEquals(playingField.get_cell(-1,-1), true);
	}

	@Test
	public void testCoordinateWrappingHigh() {
		Field playingField = new Field();
		playingField.set_cell(9,15,true); // places live cell at the extreme right lower corner of the field
		assertEquals(playingField.get_cell(19,31), true);
	}
}
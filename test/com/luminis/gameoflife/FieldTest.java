package com.luminis.gameoflife;

import org.junit.Test;

import static org.junit.Assert.*;

public class FieldTest {
	@Test
	public void testConstructorWithNoArguments() {
		assertArrayEquals(new Field().stringify(), new String[]{
				"................",
				"................",
				"................",
				"................",
				"................",
				"................",
				"................",
				"................",
				"................",
				"................"
		});
	}

	@Test
	public void testConstructorWithZeroDimensions() {
		assertArrayEquals(new Field(0,0).stringify(), new String[]{});
	}

	@Test
	public void testConstructorWithDimensions() {
		assertArrayEquals(new Field(5,7).stringify(), new String[]{
				".......",
				".......",
				".......",
				".......",
				"......."
		});
	}

	@Test
	public void testConstructorWithEmptyStringArray() {
		assertArrayEquals(new Field(new String[]{}).stringify(), new String[]{});
	}

	@Test
	public void testConstructorWithStringArray() {
		assertArrayEquals(new Field(new String[]{
				".O",
				"..O..",
				"OOO",
				""
		}).stringify(), new String[]{
				".O...",
				"..O..",
				"OOO..",
				"....."
		});
	}

	@Test
	public void testCoordinateWrappingLow() {
		Field playingField = new Field(10,16);
		playingField.setCell(9,15,true); // places live cell at the extreme right lower corner of the field
		assertTrue(playingField.getCell(-1,-1));
	}

	@Test
	public void testCoordinateWrappingHigh() {
		Field playingField = new Field(10,16);
		playingField.setCell(9,15,true); // places live cell at the extreme right lower corner of the field
		assertTrue(playingField.getCell(19,31));
	}

	@Test
	public void gliderFourGenerationsTest() {
		// After four generations the glider regained its original shape and has moved down and to the right by one step
		Field playingFieldFirstGen = new Field(10,16);
		Field playingFieldFourthGen = new Field(10,16);

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

		assertTrue(playingFieldFirstGen.equals(playingFieldFourthGen));
	}

	@Test
	public void gliderOneGenerationTest() {
		Field playingFieldFirstGen = new Field(10,16);
		Field playingFieldNextGen = new Field(10,16);

		playingFieldFirstGen.setField(0, 0, new String[]{
				".O.",
				"..O",
				"OOO"
		});
		playingFieldNextGen.setField(1, 0, new String[]{
				"O.O",
				".OO",
				".O."
		});

		playingFieldFirstGen.evolve();

		assertTrue(playingFieldFirstGen.equals(playingFieldNextGen));
	}

	@Test
	public void insertTest(){
		Field playingField1 = new Field(7,7);
		Field playingField2 = new Field(7,7);

		playingField1.setField(3,2, new String[]{"OOO"});
		playingField1.insertIntoField(1,3, new String[]{
				"O.",
				".O",
				".."
		});

		playingField2.setField(1, 2, new String[]{
				".O.",
				"..O",
				"OOO"
		});

		assertTrue(playingField1.equals(playingField2));
	}
}
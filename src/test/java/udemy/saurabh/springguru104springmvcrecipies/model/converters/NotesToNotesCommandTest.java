package udemy.saurabh.springguru104springmvcrecipies.model.converters;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.saurabh.springguru104springmvcrecipies.model.Notes;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.NotesCommand;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by jt on 6/21/17.
 */
class NotesToNotesCommandTest {

	private static final Long ID_VALUE = 1L;
	private static final String RECIPE_NOTES = "Notes";
	private NotesToNotesCommand converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new NotesToNotesCommand();
	}

	@Test
	void convert() throws Exception {
		//given
		Notes notes = new Notes();
		notes.setId(ID_VALUE);
		notes.setRecipeNotes(RECIPE_NOTES);

		//when
		NotesCommand notesCommand = converter.convert(notes);

		//then
		assert notesCommand != null;
		assertEquals(ID_VALUE, notesCommand.getId());
		assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
	}

	@Test
	void testNull() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Notes()));
	}
}
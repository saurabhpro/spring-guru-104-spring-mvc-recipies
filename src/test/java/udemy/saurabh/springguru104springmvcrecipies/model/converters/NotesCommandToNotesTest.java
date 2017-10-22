package udemy.saurabh.springguru104springmvcrecipies.model.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.saurabh.springguru104springmvcrecipies.model.Notes;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.NotesCommand;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

	private static final Long ID_VALUE = 1L;
	private static final String RECIPE_NOTES = "Notes";
	private NotesCommandToNotes converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new NotesCommandToNotes();

	}

	@Test
	void testNullParameter() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new NotesCommand()));
	}

	@Test
	void convert() throws Exception {
		//given
		NotesCommand notesCommand = new NotesCommand();
		notesCommand.setId(ID_VALUE);
		notesCommand.setRecipeNotes(RECIPE_NOTES);

		//when
		Notes notes = converter.convert(notesCommand);

		//then
		assertNotNull(notes);
		assertEquals(ID_VALUE, notes.getId());
		assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
	}

}
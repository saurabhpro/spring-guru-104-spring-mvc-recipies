package udemy.saurabh.springguru104springmvcrecipies.model.converters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.saurabh.springguru104springmvcrecipies.model.Category;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.CategoryCommand;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

	private static final Long ID_VALUE = 1L;
	private static final String DESCRIPTION = "description";
	private CategoryCommandToCategory conveter;

	@BeforeEach
	void setUp() throws Exception {
		conveter = new CategoryCommandToCategory();
	}

	@Test
	void testNullObject() throws Exception {
		assertNull(conveter.convert(null));
	}

	@Test
	void testEmptyObject() throws Exception {
		assertNotNull(conveter.convert(new CategoryCommand()));
	}

	@Test
	void convert() throws Exception {
		//given
		CategoryCommand categoryCommand = new CategoryCommand();
		categoryCommand.setId(ID_VALUE);
		categoryCommand.setDescription(DESCRIPTION);

		//when
		Category category = conveter.convert(categoryCommand);

		//then
		Assertions.assertNotNull(category);
		assertEquals(ID_VALUE, category.getId());
		assertEquals(DESCRIPTION, category.getDescription());
	}

}
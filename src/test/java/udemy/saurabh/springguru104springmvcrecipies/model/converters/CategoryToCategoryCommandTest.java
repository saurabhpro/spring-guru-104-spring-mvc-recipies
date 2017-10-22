package udemy.saurabh.springguru104springmvcrecipies.model.converters;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.saurabh.springguru104springmvcrecipies.model.Category;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.CategoryCommand;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by jt on 6/21/17.
 */
class CategoryToCategoryCommandTest {

	private static final Long ID_VALUE = 1L;
	private static final String DESCRIPTION = "descript";
	private CategoryToCategoryCommand convter;

	@BeforeEach
	void setUp() throws Exception {
		convter = new CategoryToCategoryCommand();
	}

	@Test
	void testNullObject() throws Exception {
		assertNull(convter.convert(null));
	}

	@Test
	void testEmptyObject() throws Exception {
		assertNotNull(convter.convert(new Category()));
	}

	@Test
	void convert() throws Exception {
		//given
		Category category = new Category();
		category.setId(ID_VALUE);
		category.setDescription(DESCRIPTION);

		//when
		CategoryCommand categoryCommand = convter.convert(category);

		//then
		Assertions.assertNotNull(categoryCommand);
		assertEquals(ID_VALUE, categoryCommand.getId());
		assertEquals(DESCRIPTION, categoryCommand.getDescription());

	}

}
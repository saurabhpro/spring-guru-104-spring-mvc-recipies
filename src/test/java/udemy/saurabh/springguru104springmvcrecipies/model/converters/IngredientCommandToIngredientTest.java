package udemy.saurabh.springguru104springmvcrecipies.model.converters;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.saurabh.springguru104springmvcrecipies.model.Ingredient;
import udemy.saurabh.springguru104springmvcrecipies.model.Recipe;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.IngredientCommand;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.UnitOfMeasureCommand;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

	public static final Recipe RECIPE = new Recipe();
	private static final BigDecimal AMOUNT = new BigDecimal("1");
	private static final String DESCRIPTION = "Cheeseburger";
	private static final Long ID_VALUE = 1L;
	private static final Long UOM_ID = 2L;

	private IngredientCommandToIngredient converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}

	@Test
	void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new IngredientCommand()));
	}

	@Test
	void convert() throws Exception {
		//given
		IngredientCommand command = new IngredientCommand();
		command.setId(ID_VALUE);
		command.setAmount(AMOUNT);
		command.setDescription(DESCRIPTION);
		UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
		unitOfMeasureCommand.setId(UOM_ID);
		command.setUnitOfMeasure(unitOfMeasureCommand);

		//when
		Ingredient ingredient = converter.convert(command);

		//then
		assertNotNull(ingredient);
		assertNotNull(ingredient.getUnitOfMeasure());
		assertEquals(ID_VALUE, ingredient.getId());
		assertEquals(AMOUNT, ingredient.getAmount());
		assertEquals(DESCRIPTION, ingredient.getDescription());
		assertEquals(UOM_ID, ingredient.getUnitOfMeasure().getId());
	}

	@Test
	void convertWithNullUOM() throws Exception {
		//given
		IngredientCommand command = new IngredientCommand();
		command.setId(ID_VALUE);
		command.setAmount(AMOUNT);
		command.setDescription(DESCRIPTION);
		UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();


		//when
		Ingredient ingredient = converter.convert(command);

		//then
		assertNotNull(ingredient);
		assertNull(ingredient.getUnitOfMeasure());
		assertEquals(ID_VALUE, ingredient.getId());
		assertEquals(AMOUNT, ingredient.getAmount());
		assertEquals(DESCRIPTION, ingredient.getDescription());

	}

}
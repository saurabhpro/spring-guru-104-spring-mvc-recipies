package udemy.saurabh.springguru104springmvcrecipies.model.converters;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.saurabh.springguru104springmvcrecipies.model.Ingredient;
import udemy.saurabh.springguru104springmvcrecipies.model.Recipe;
import udemy.saurabh.springguru104springmvcrecipies.model.UnitOfMeasure;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.IngredientCommand;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by jt on 6/21/17.
 */
class IngredientToIngredientCommandTest {

	private static final Recipe RECIPE = new Recipe();
	private static final BigDecimal AMOUNT = new BigDecimal("1");
	private static final String DESCRIPTION = "Cheeseburger";
	private static final Long UOM_ID = 2L;
	private static final Long ID_VALUE = 1L;


	private IngredientToIngredientCommand converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	}

	@Test
	void testNullConvert() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Ingredient()));
	}

	@Test
	void testConvertNullUOM() throws Exception {
		//given
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID_VALUE);
		ingredient.setRecipe(RECIPE);
		ingredient.setAmount(AMOUNT);
		ingredient.setDescription(DESCRIPTION);
		ingredient.setUnitOfMeasure(null);
		//when
		IngredientCommand ingredientCommand = converter.convert(ingredient);
		//then
		assert ingredientCommand != null;
		assertNull(ingredientCommand.getUnitOfMeasure());
		assertEquals(ID_VALUE, ingredientCommand.getId());
		// assertEquals(RECIPE, ingredientCommand.get);
		assertEquals(AMOUNT, ingredientCommand.getAmount());
		assertEquals(DESCRIPTION, ingredientCommand.getDescription());
	}

	@Test
	void testConvertWithUom() throws Exception {
		//given
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID_VALUE);
		ingredient.setRecipe(RECIPE);
		ingredient.setAmount(AMOUNT);
		ingredient.setDescription(DESCRIPTION);

		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(UOM_ID);

		ingredient.setUnitOfMeasure(uom);
		//when
		IngredientCommand ingredientCommand = converter.convert(ingredient);
		//then
		Assertions.assertNotNull(ingredientCommand);
		assertEquals(ID_VALUE, ingredientCommand.getId());
		assertNotNull(ingredientCommand.getUnitOfMeasure());
		assertEquals(UOM_ID, ingredientCommand.getUnitOfMeasure().getId());
		// assertEquals(RECIPE, ingredientCommand.get);
		assertEquals(AMOUNT, ingredientCommand.getAmount());
		assertEquals(DESCRIPTION, ingredientCommand.getDescription());
	}
}
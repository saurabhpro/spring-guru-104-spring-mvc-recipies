package udemy.saurabh.springguru104springmvcrecipies.model.converters;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.saurabh.springguru104springmvcrecipies.model.Difficulty;
import udemy.saurabh.springguru104springmvcrecipies.model.Recipe;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.CategoryCommand;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.IngredientCommand;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.NotesCommand;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.RecipeCommand;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {
	private static final Long RECIPE_ID = 1L;
	private static final Integer COOK_TIME = Integer.valueOf("5");
	private static final Integer PREP_TIME = Integer.valueOf("7");
	private static final String DESCRIPTION = "My Recipe";
	private static final String DIRECTIONS = "Directions";
	private static final Difficulty DIFFICULTY = Difficulty.EASY;
	private static final Integer SERVINGS = Integer.valueOf("3");
	private static final String SOURCE = "Source";
	private static final String URL = "Some URL";
	private static final Long CAT_ID_1 = 1L;
	private static final Long CAT_ID2 = 2L;
	private static final Long INGRED_ID_1 = 3L;
	private static final Long INGRED_ID_2 = 4L;
	private static final Long NOTES_ID = 9L;

	private RecipeCommandToRecipe converter;


	@BeforeEach
	void setUp() throws Exception {
		converter = new RecipeCommandToRecipe(new CategoryCommandToCategory(),
				new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
				new NotesCommandToNotes());
	}

	@Test
	void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new RecipeCommand()));
	}

	@Test
	void convert() throws Exception {
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(RECIPE_ID);
		recipeCommand.setCookTime(COOK_TIME);
		recipeCommand.setPrepTime(PREP_TIME);
		recipeCommand.setDescription(DESCRIPTION);
		recipeCommand.setDifficulty(DIFFICULTY);
		recipeCommand.setDirections(DIRECTIONS);
		recipeCommand.setServings(SERVINGS);
		recipeCommand.setSource(SOURCE);
		recipeCommand.setUrl(URL);

		NotesCommand notes = new NotesCommand();
		notes.setId(NOTES_ID);

		recipeCommand.setNotes(notes);

		CategoryCommand category = new CategoryCommand();
		category.setId(CAT_ID_1);

		CategoryCommand category2 = new CategoryCommand();
		category2.setId(CAT_ID2);

		recipeCommand.getCategories().add(category);
		recipeCommand.getCategories().add(category2);

		IngredientCommand ingredient = new IngredientCommand();
		ingredient.setId(INGRED_ID_1);

		IngredientCommand ingredient2 = new IngredientCommand();
		ingredient2.setId(INGRED_ID_2);

		recipeCommand.getIngredients().add(ingredient);
		recipeCommand.getIngredients().add(ingredient2);

		//when
		Recipe recipe = converter.convert(recipeCommand);

		assertNotNull(recipe);
		assertEquals(RECIPE_ID, recipe.getId());
		assertEquals(COOK_TIME, recipe.getCookTime());
		assertEquals(PREP_TIME, recipe.getPrepTime());
		assertEquals(DESCRIPTION, recipe.getDescription());
		assertEquals(DIFFICULTY, recipe.getDifficulty());
		assertEquals(DIRECTIONS, recipe.getDirections());
		assertEquals(SERVINGS, recipe.getServings());
		assertEquals(SOURCE, recipe.getSource());
		assertEquals(URL, recipe.getUrl());
		assertEquals(NOTES_ID, recipe.getNotes().getId());
		assertEquals(2, recipe.getCategories().size());
		assertEquals(2, recipe.getIngredients().size());
	}

}
package udemy.saurabh.springguru104springmvcrecipies.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import udemy.saurabh.springguru104springmvcrecipies.model.Recipe;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.RecipeCommand;
import udemy.saurabh.springguru104springmvcrecipies.model.converters.RecipeCommandToRecipe;
import udemy.saurabh.springguru104springmvcrecipies.model.converters.RecipeToRecipeCommand;
import udemy.saurabh.springguru104springmvcrecipies.model.exceptions.NotFoundException;
import udemy.saurabh.springguru104springmvcrecipies.repositories.IRecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {
	private IRecipeService recipeService;

	@Mock
	private IRecipeRepository recipeRepository;

	@Mock
	private RecipeToRecipeCommand recipeToRecipeCommand;

	@Mock
	private RecipeCommandToRecipe recipeCommandToRecipe;


	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}

	@Test
	void getRecSimple() {
		// when
		Set<Recipe> recipes = recipeService.getRecipes();

		// then
		Assertions.assertEquals(0, recipes.size());
	}

	@Test
	void getRecipesTest() {

		// given
		Recipe recipe = new Recipe();
		Set<Recipe> recipesData = new HashSet<>();
		recipesData.add(recipe);

		// when
		when(recipeService.getRecipes()).thenReturn(recipesData);       // got sample data back
		Set<Recipe> recipes = recipeService.getRecipes();

		// then
		assertEquals(recipes.size(), 1);
		verify(recipeRepository, times(1)).findAll();
		verify(recipeRepository, never()).findById(anyLong());
	}

	@Test
	void getRecipeByIdTest() throws Exception {

		// given
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		// when
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		Recipe recipeReturned = recipeService.findById(1L);

		// then
		assertNotNull(recipeReturned, "Null recipe returned");
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, never()).findAll();
	}

	@Test
	void getRecipeCommandByIdTest() throws Exception {
		// given
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);

		when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

		RecipeCommand commandById = recipeService.findCommandById(1L);

		assertNotNull(commandById, "Null recipe returned");
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, never()).findAll();
	}

	@Test
	void testDeleteById() throws Exception {

		//given
		Long idToDelete = 2L;

		//when
		recipeService.deleteById(idToDelete);

		//no 'when()', since method has void return type

		//then
		verify(recipeRepository, times(1)).deleteById(anyLong());
	}

	@Test
	void getRecipeByIdTestNotFound() throws Exception {

		Optional<Recipe> recipeOptional = Optional.empty();

		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

		assertThrows(NotFoundException.class, () -> recipeService.findById(1L));

		//should go boom
	}

}
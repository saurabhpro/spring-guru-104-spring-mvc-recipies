package udemy.saurabh.springguru104springmvcrecipies.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.IngredientCommand;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.RecipeCommand;
import udemy.saurabh.springguru104springmvcrecipies.service.IIngredientService;
import udemy.saurabh.springguru104springmvcrecipies.service.IRecipeService;
import udemy.saurabh.springguru104springmvcrecipies.service.IUnitOfMeasureService;

import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

	@Mock
	private IIngredientService ingredientService;

	@Mock
	private IUnitOfMeasureService unitOfMeasureService;

	@Mock
	private IRecipeService recipeService;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		IngredientController controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	void testListIngredients() throws Exception {
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

		//when
		mockMvc.perform(get("/recipe/1/ingredients"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/list"))
				.andExpect(model().attributeExists("recipe"));

		//then
		verify(recipeService, times(1)).findCommandById(anyLong());
	}

	@Test
	void testShowIngredient() throws Exception {
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();

		//when
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);

		//then
		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/show"))
				.andExpect(model().attributeExists("ingredient"));
	}

	@Test
	void testUpdateIngredientForm() throws Exception {
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();

		//when
		when(ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredientCommand);
		when(unitOfMeasureService.listAllUnitOfMeasures()).thenReturn(new HashSet<>());

		//then
		mockMvc.perform(get("/recipe/1/ingredient/2/update"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/ingredientform"))
				.andExpect(model().attributeExists("ingredient"))
				.andExpect(model().attributeExists("uomList"));
	}

	@Test
	void testSaveOrUpdate() throws Exception {
		//given
		IngredientCommand command = new IngredientCommand();
		command.setId(3L);
		command.setRecipeId(2L);

		//when
		when(ingredientService.saveIngredientCommand(any())).thenReturn(command);

		//then
		mockMvc.perform(post("/recipe/2/ingredient")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "some string")
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));

	}

	@Test
	void testNewIngredientForm() throws Exception {
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);

		//when
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		when(unitOfMeasureService.listAllUnitOfMeasures()).thenReturn(new HashSet<>());

		//then
		mockMvc.perform(get("/recipe/1/ingredient/new"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/ingredientform"))
				.andExpect(model().attributeExists("ingredient"))
				.andExpect(model().attributeExists("uomList"));

		verify(recipeService, times(1)).findCommandById(anyLong());

	}

	@Test
	void testDeleteIngredient() throws Exception {

		//then
		mockMvc.perform(get("/recipe/2/ingredient/3/delete")
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/2/ingredients"));

		verify(ingredientService, times(1)).deleteById(anyLong(), anyLong());

	}
}
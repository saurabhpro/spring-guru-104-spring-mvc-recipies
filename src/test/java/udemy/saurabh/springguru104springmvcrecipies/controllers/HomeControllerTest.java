package udemy.saurabh.springguru104springmvcrecipies.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import udemy.saurabh.springguru104springmvcrecipies.model.Recipe;
import udemy.saurabh.springguru104springmvcrecipies.service.IRecipeService;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


class HomeControllerTest {

	@Mock
	private IRecipeService recipeService;

	@Mock
	private Model model;

	private HomeController homeController;

	@Test
	void mockMvc() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();

		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"));
	}

	@Test
	void getHomePage() {

		// given
		Set<Recipe> recipeSet = new HashSet<>();
		recipeSet.add(new Recipe());

		Recipe recipe = new Recipe();
		recipe.setId(22L);
		recipeSet.add(recipe);

		// when
		when(recipeService.getRecipes()).thenReturn(recipeSet);
		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
		String pageName = homeController.getMo(model);

		// then
		Assertions.assertEquals("index", pageName);
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());

		Set<Recipe> setInController = argumentCaptor.getValue();
		Assertions.assertEquals(2, setInController.size());

	}

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		homeController = new HomeController(recipeService);
	}


}
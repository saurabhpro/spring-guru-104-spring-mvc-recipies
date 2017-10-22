package udemy.saurabh.springguru104springmvcrecipies.service;

import udemy.saurabh.springguru104springmvcrecipies.model.Recipe;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.RecipeCommand;

import java.util.Set;

public interface IRecipeService {

	Set<Recipe> getRecipes();

	Recipe findById(Long id);

	RecipeCommand saveRecipeCommand(RecipeCommand command);

	RecipeCommand findCommandById(Long l);

	void deleteById(Long idToDelete);
}

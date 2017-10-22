package udemy.saurabh.springguru104springmvcrecipies.service;

import udemy.saurabh.springguru104springmvcrecipies.model.commands.IngredientCommand;

public interface IIngredientService {
	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

	IngredientCommand saveIngredientCommand(IngredientCommand command);

	void deleteById(Long recipeId, Long ingredientId);
}

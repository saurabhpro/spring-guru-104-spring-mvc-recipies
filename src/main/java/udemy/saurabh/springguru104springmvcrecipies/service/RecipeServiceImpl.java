package udemy.saurabh.springguru104springmvcrecipies.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udemy.saurabh.springguru104springmvcrecipies.model.Recipe;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.RecipeCommand;
import udemy.saurabh.springguru104springmvcrecipies.model.converters.RecipeCommandToRecipe;
import udemy.saurabh.springguru104springmvcrecipies.model.converters.RecipeToRecipeCommand;
import udemy.saurabh.springguru104springmvcrecipies.model.exceptions.NotFoundException;
import udemy.saurabh.springguru104springmvcrecipies.repositories.IRecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements IRecipeService {

	private final IRecipeRepository recipeRepository;

	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	@Autowired
	public RecipeServiceImpl(IRecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Set<Recipe> getRecipes() {
		log.info("I'm using slf4j");

		Set<Recipe> recipeSet = new HashSet<>();

		recipeRepository.findAll().forEach(recipeSet::add);

		return recipeSet;
	}

	@Override
	public Recipe findById(Long id) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(id);

		return recipeOptional.orElseThrow(() -> new NotFoundException("Recipe Not Found for ID : " + id));
	}

	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

		assert detachedRecipe != null;
		Recipe savedRecipe = recipeRepository.save(detachedRecipe);

		log.debug("Saved RecipeId:" + savedRecipe.getId());

		return recipeToRecipeCommand.convert(savedRecipe);
	}

	@Override
	@Transactional
	public RecipeCommand findCommandById(Long l) {
		return recipeToRecipeCommand.convert(findById(l));
	}

	@Override
	public void deleteById(Long idToDelete) {
		recipeRepository.deleteById(idToDelete);
	}
}
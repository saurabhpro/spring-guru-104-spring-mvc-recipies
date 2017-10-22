package udemy.saurabh.springguru104springmvcrecipies.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udemy.saurabh.springguru104springmvcrecipies.model.Ingredient;
import udemy.saurabh.springguru104springmvcrecipies.model.Recipe;
import udemy.saurabh.springguru104springmvcrecipies.model.UnitOfMeasure;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.IngredientCommand;
import udemy.saurabh.springguru104springmvcrecipies.model.converters.IngredientCommandToIngredient;
import udemy.saurabh.springguru104springmvcrecipies.model.converters.IngredientToIngredientCommand;
import udemy.saurabh.springguru104springmvcrecipies.repositories.IRecipeRepository;
import udemy.saurabh.springguru104springmvcrecipies.repositories.IUnitOfMeasureRepository;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IIngredientService {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final IRecipeRepository recipeRepository;
	private final IUnitOfMeasureRepository unitOfMeasureRepository;

	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, IRecipeRepository recipeRepository, IUnitOfMeasureRepository unitOfMeasureRepository) {
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

		if (!recipeOptional.isPresent()) {
			// impl error handling using orElseThrow
			log.error("recipe id not found. Id: " + recipeId);
		}

		Recipe recipe = recipeOptional.orElseThrow(() -> new RuntimeException("Recipe id not found: " + recipeId));

		//noinspection Convert2MethodRef
		Optional<IngredientCommand> ingredientCommandOptional = recipe
				.getIngredients()
				.stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

		if (!ingredientCommandOptional.isPresent()) {
			// impl error handling using orElseThrow
			log.error("Ingredient id not found: " + ingredientId);
		}

		return ingredientCommandOptional.orElseThrow(() -> new RuntimeException("Ingredient id not found: " + ingredientId));
	}

	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

		if (!recipeOptional.isPresent()) {

			//todo toss error if not found!
			log.error("Recipe not found for id: " + command.getRecipeId());
			return new IngredientCommand();
		} else {
			Recipe recipe = recipeOptional.get();

			Optional<Ingredient> ingredientOptional = recipe
					.getIngredients()
					.stream()
					.filter(ingredient -> ingredient.getId().equals(command.getId()))
					.findFirst();

			if (ingredientOptional.isPresent()) {

				Ingredient ingredientFound = ingredientOptional.get();

				ingredientFound.setDescription(command.getDescription());
				ingredientFound.setAmount(command.getAmount());

				UnitOfMeasure unit = unitOfMeasureRepository
						.findById(command.getUnitOfMeasure().getId())
						.orElseThrow(() -> new RuntimeException("UOM NOT FOUND"));//todo address this

				ingredientFound.setUnitOfMeasure(unit);
			} else {
				//add new Ingredient
				recipe.addIngredient(ingredientCommandToIngredient.convert(command));
			}

			Recipe savedRecipe = recipeRepository.save(recipe);

			//to do check for fail
			Optional<Ingredient> savedIngredientOptional = savedRecipe
					.getIngredients()
					.stream()
					.filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
					.findFirst();

			//check by description
			if (!savedIngredientOptional.isPresent()) {
				//not totally safe... But best guess
				savedIngredientOptional = savedRecipe
						.getIngredients()
						.stream()
						.filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
						.filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
						.filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(command.getUnitOfMeasure().getId()))
						.findFirst();
			}

			//to do check for fail
			return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
		}

	}

	@Override
	public void deleteById(Long recipeId, Long ingredientId) {

		log.debug("Deleting ingredient: " + recipeId + ":" + ingredientId);

		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

		if (recipeOptional.isPresent()) {
			log.debug("found recipe");
			Recipe recipe = recipeOptional.get();

			Optional<Ingredient> ingredientOptional = recipe
					.getIngredients()
					.stream()
					.filter(ingredient -> ingredient.getId().equals(ingredientId))
					.findFirst();

			ingredientOptional.ifPresent(ingredient -> {
						log.debug("found Ingredient");

						ingredient.setRecipe(null);     // break recipe link
						recipe.getIngredients().remove(ingredient);   // remove from the set of ingredients (not delete)

						recipeRepository.save(recipe);
					}
			);
		} else {
			log.debug("Recipe Id Not found. Id:" + recipeId);
		}
	}
}
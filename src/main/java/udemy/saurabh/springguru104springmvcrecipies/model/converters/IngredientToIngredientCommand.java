package udemy.saurabh.springguru104springmvcrecipies.model.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import udemy.saurabh.springguru104springmvcrecipies.model.Ingredient;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.IngredientCommand;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

	private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

	public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
		this.uomConverter = uomConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public IngredientCommand convert(Ingredient source) {
		if (source == null) {
			return null;
		}

		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(source.getId());
		if (source.getRecipe() != null) {
			ingredientCommand.setRecipeId(source.getRecipe().getId());
		}
		ingredientCommand.setAmount(source.getAmount());
		ingredientCommand.setDescription(source.getDescription());
		ingredientCommand.setUnitOfMeasure(uomConverter.convert(source.getUnitOfMeasure()));
		return ingredientCommand;
	}
}

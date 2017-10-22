package udemy.saurabh.springguru104springmvcrecipies.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import udemy.saurabh.springguru104springmvcrecipies.model.*;
import udemy.saurabh.springguru104springmvcrecipies.repositories.ICategoryRepository;
import udemy.saurabh.springguru104springmvcrecipies.repositories.IRecipeRepository;
import udemy.saurabh.springguru104springmvcrecipies.repositories.IUnitOfMeasureRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@Profile("default")
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private final ICategoryRepository categoryRepository;
	private final IRecipeRepository recipeRepository;
	private final IUnitOfMeasureRepository unitOfMeasureRepository;

	public RecipeBootstrap(ICategoryRepository categoryRepository, IRecipeRepository recipeRepository, IUnitOfMeasureRepository unitOfMeasureRepository) {
		this.categoryRepository = categoryRepository;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Transactional
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		recipeRepository.saveAll(getRecipes());

		log.info("Loading Bootstrap Data");
	}

	private List<Recipe> getRecipes() {

		List<Recipe> recipes = new ArrayList<>(2);

		RecipeBasePreparer rawItemPreparer = new RecipeBasePreparer();

		//Yummy Guac
		Recipe guacRecipe = getGuacamoleRecipe(rawItemPreparer);
		recipes.add(guacRecipe);

		//Yummy Tacos
		Recipe tacosRecipe = getChickenTacoRecipe(rawItemPreparer);
		recipes.add(tacosRecipe);

		return recipes;
	}

	private Recipe getGuacamoleRecipe(RecipeBasePreparer rb) {
		Recipe guacRecipe = new Recipe();

		guacRecipe.setDescription("Perfect Guacamole");
		guacRecipe.setPrepTime(10);
		guacRecipe.setCookTime(0);
		guacRecipe.setDifficulty(Difficulty.EASY);
		guacRecipe.setDirections(RecipeDescriptions.GuacamolePreparationSteps.replaceAll("\\n", " <br/> "));

		Notes guacNotes = new Notes();
		guacNotes.setRecipeNotes(RecipeNotes.GuacamoleMakingNotes.replaceAll("\\n", " <br/> "));

		// needed for bidirectional - should be two way in one method call
		// guacNotes.setRecipe(guacRecipe);
		// guacRecipe.setNotes(guacNotes);
		guacRecipe.setNotes(guacNotes);
		guacRecipe.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");
		guacRecipe.setServings(4);
		guacRecipe.setSource("Simply Servings");

		guacRecipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), rb.eachUom));
		guacRecipe.addIngredient(new Ingredient("Kosher salt", new BigDecimal(".5"), rb.teaspoonUom));
		guacRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), rb.tableSpoonUom));
		guacRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), rb.tableSpoonUom));
		guacRecipe.addIngredient(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), rb.eachUom));
		guacRecipe.addIngredient(new Ingredient("Cilantro", new BigDecimal(2), rb.tableSpoonUom));
		guacRecipe.addIngredient(new Ingredient("freshly grated black pepper", new BigDecimal(2), rb.dashUom));
		guacRecipe.addIngredient(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), rb.eachUom));

		guacRecipe.getCategories().add(rb.americanCategory);
		guacRecipe.getCategories().add(rb.mexicanCategory);
		return guacRecipe;
	}

	private Recipe getChickenTacoRecipe(RecipeBasePreparer rb) {
		Recipe tacosRecipe = new Recipe();

		tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
		tacosRecipe.setCookTime(9);
		tacosRecipe.setPrepTime(20);
		tacosRecipe.setDifficulty(Difficulty.MODERATE);

		tacosRecipe.setDirections(RecipeDescriptions.TacosPreparationDirections.replaceAll("\\n", " <br/> "));

		Notes tacoNotes = new Notes();
		tacoNotes.setRecipeNotes(RecipeNotes.TacoMakingNotes.replaceAll("\\n", " <br/> "));

		//tacoNotes.setRecipe(tacosRecipe);
		tacosRecipe.setNotes(tacoNotes);
		tacosRecipe.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
		tacosRecipe.setServings(4);
		tacosRecipe.setSource("Simply Servings");


		tacosRecipe.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), rb.tableSpoonUom));
		tacosRecipe.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), rb.teaspoonUom));
		tacosRecipe.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), rb.teaspoonUom));
		tacosRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), rb.teaspoonUom));
		tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), rb.teaspoonUom));
		tacosRecipe.addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), rb.eachUom));
		tacosRecipe.addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), rb.tableSpoonUom));
		tacosRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), rb.tableSpoonUom));
		tacosRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), rb.tableSpoonUom));
		tacosRecipe.addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), rb.tableSpoonUom));
		tacosRecipe.addIngredient(new Ingredient("small corn tortillasr", new BigDecimal(8), rb.eachUom));
		tacosRecipe.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), rb.cupsUom));
		tacosRecipe.addIngredient(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), rb.eachUom));
		tacosRecipe.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), rb.eachUom));
		tacosRecipe.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), rb.pintUom));
		tacosRecipe.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), rb.eachUom));
		tacosRecipe.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), rb.eachUom));
		tacosRecipe.addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), rb.cupsUom));
		tacosRecipe.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(4), rb.eachUom));

		tacosRecipe.getCategories().add(rb.americanCategory);
		tacosRecipe.getCategories().add(rb.mexicanCategory);
		return tacosRecipe;
	}

	private class RecipeBasePreparer {

		//get optionals or throw error if not found
		final UnitOfMeasure eachUom;
		final UnitOfMeasure tableSpoonUom;
		final UnitOfMeasure teaspoonUom;
		final UnitOfMeasure dashUom;
		final UnitOfMeasure pintUom;
		final UnitOfMeasure cupsUom;

		//get Categories
		final Category americanCategory;
		final Category mexicanCategory;

		RecipeBasePreparer() {
			this.eachUom = getUomObjectByName("Each");
			this.tableSpoonUom = getUomObjectByName("Tablespoon");
			this.teaspoonUom = getUomObjectByName("Teaspoon");
			this.dashUom = getUomObjectByName("Dash");
			this.pintUom = getUomObjectByName("Pint");
			this.cupsUom = getUomObjectByName("Cup");

			this.americanCategory = getCategoryObjectByName("American");
			this.mexicanCategory = getCategoryObjectByName("Mexican");
		}

		/**
		 * Method to retrieve unit of measurement and throw error if not found
		 * <p>
		 * we can do the following or we can simply use orElsexxx() to do the same job
		 * if (!eachUomOptional.isPresent()) {
		 * throw new RuntimeException("Expected UOM Not Found");
		 * }
		 * UnitOfMeasure eachUom = eachUomOptional.get();
		 *
		 * @param opmType in string
		 * @return Uom Object or error thrown
		 */
		private UnitOfMeasure getUomObjectByName(String opmType) {
			//get UOMs
			Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findTopOneByDescription(opmType);

			return uomOptional.orElseThrow(() -> new RuntimeException("Expected UOM Not Found"));
		}

		private Category getCategoryObjectByName(String categoryType) {
			//get UOMs
			Optional<Category> catOptional = categoryRepository.findTopOneByDescription(categoryType);

			return catOptional.orElseThrow(() -> new RuntimeException("Expected UOM Not Found"));
		}
	}
}
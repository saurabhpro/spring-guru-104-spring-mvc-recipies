package udemy.saurabh.springguru104springmvcrecipies.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.IngredientCommand;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.RecipeCommand;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.UnitOfMeasureCommand;
import udemy.saurabh.springguru104springmvcrecipies.service.IIngredientService;
import udemy.saurabh.springguru104springmvcrecipies.service.IRecipeService;
import udemy.saurabh.springguru104springmvcrecipies.service.IUnitOfMeasureService;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class IngredientController {

	private final IRecipeService recipeService;
	private final IIngredientService ingredientService;
	private final IUnitOfMeasureService unitOfMeasureService;

	public IngredientController(IRecipeService recipeService, IIngredientService ingredientService, IUnitOfMeasureService unitOfMeasureService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}

	@GetMapping("/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		log.debug("Getting ingredient list for recipe id: " + recipeId);

		// use command object to avoid lazy load errors in Thymeleaf.
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));    // keeping Long.valueOf() usage

		return "recipe/ingredient/list";
	}

	@GetMapping("/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable Long recipeId,
	                                   @PathVariable Long id, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));
		return "recipe/ingredient/show";
	}

	@GetMapping("/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngredient(@PathVariable Long recipeId,
	                                     @PathVariable Long id,
	                                     Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));

		model.addAttribute("uomList", unitOfMeasureService.listAllUnitOfMeasures());
		return "recipe/ingredient/ingredientform";
	}

	@PostMapping("/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

		log.debug("saved receipe id:" + savedCommand.getRecipeId());
		log.debug("saved ingredient id:" + savedCommand.getId());

		return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
	}

	@GetMapping("/{recipeId}/ingredient/new")
	public String newRecipe(@PathVariable Long recipeId, Model model) {

		//make sure we have a good id value
		RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
		//todo raise exception if null

		//need to return back parent id for hidden form property
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(recipeId);
		model.addAttribute("ingredient", ingredientCommand);

		//init uom
		ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

		model.addAttribute("uomList", unitOfMeasureService.listAllUnitOfMeasures());

		return "recipe/ingredient/ingredientform";
	}

	@GetMapping("/{recipeId}/ingredient/{id}/delete")
	public String deleteRecipeIngredient(@PathVariable Long recipeId,
	                                     @PathVariable Long id,
	                                     Model model) {
		log.debug("deleting recipe : " + recipeId + " with ingredient id: " + id);

		ingredientService.deleteById(recipeId, id);

		return "redirect:/recipe/" + recipeId + "/ingredients";
	}
}
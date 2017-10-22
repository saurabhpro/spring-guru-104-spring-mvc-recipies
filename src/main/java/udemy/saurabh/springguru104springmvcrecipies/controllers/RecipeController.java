package udemy.saurabh.springguru104springmvcrecipies.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.RecipeCommand;
import udemy.saurabh.springguru104springmvcrecipies.model.exceptions.NotFoundException;
import udemy.saurabh.springguru104springmvcrecipies.service.IRecipeService;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {
	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

	private final IRecipeService recipeService;

	@Autowired
	public RecipeController(IRecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping("/{id}/show")
	public String showById(@PathVariable Long id, Model model) {

		log.info("@PathVariable Type is Recipe id or Long id instead of String id & it works");

		model.addAttribute("recipe", recipeService.findById(id));

		return "recipe/show";
	}

	@GetMapping("/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());

		return RECIPE_RECIPEFORM_URL;
	}

	@GetMapping("/{id}/update")
	public String updateRecipe(@PathVariable Long id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(id));

		return RECIPE_RECIPEFORM_URL;
	}

//	V1
//	@PostMapping("/")
//	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
//		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
//		return "redirect:/recipe/" + savedCommand.getId() + "/show";
//	}

	@PostMapping({"", "/"})
	public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command,
	                           BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {

			bindingResult.getAllErrors().forEach(objectError -> {
				log.debug(objectError.toString());
			});

			return RECIPE_RECIPEFORM_URL;
		}

		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}

	@GetMapping("/{id}/delete")
	public String deleteById(@PathVariable Long id) {

		log.debug("Deleting id: " + id);

		recipeService.deleteById(id);

		return "redirect:/";    // to home controller
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)   // since as we are handling the exception status code gets overridden as 200
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {

		log.error("Handling not found exception");
		log.error(exception.getMessage());

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("404error");
		modelAndView.addObject("exception", exception);

		return modelAndView;
	}
}

/*
@PathVariable Type valueName(from URI)

here the Type can be anything you need, given thats what you need
for our id - we could gave used

@PathVariable Recipe id or
@PathVariable Long id or
@PathVariable String id

by using @Long id - no boxing was required
 */
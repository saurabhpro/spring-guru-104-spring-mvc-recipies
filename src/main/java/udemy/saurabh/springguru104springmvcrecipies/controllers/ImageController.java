package udemy.saurabh.springguru104springmvcrecipies.controllers;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.RecipeCommand;
import udemy.saurabh.springguru104springmvcrecipies.service.IImageService;
import udemy.saurabh.springguru104springmvcrecipies.service.IRecipeService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("/recipe")
public class ImageController {

	private final IImageService imageService;
	private final IRecipeService recipeService;

	@Autowired
	public ImageController(IImageService imageService, IRecipeService recipeService) {
		this.imageService = imageService;
		this.recipeService = recipeService;
	}

	@GetMapping("/{id}/image")
	public String showUploadForm(@PathVariable Long id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(id));

		return "recipe/imageuploadform";
	}

	@PostMapping("/{id}/image")
	public String handleImagePost(@PathVariable Long id, @RequestParam("imagefile") MultipartFile file) {

		imageService.saveImageFile(id, file);

		return "redirect:/recipe/" + id + "/show";
	}

	@GetMapping("/{id}/recipeimage")
	public void renderImageFromDB(@PathVariable Long id, HttpServletResponse response) throws IOException {
		RecipeCommand recipeCommand = recipeService.findCommandById(id);

		if (recipeCommand.getImage() != null) {
			byte[] byteArray = new byte[recipeCommand.getImage().length];
			int i = 0;

			for (Byte wrappedByte : recipeCommand.getImage()) {
				byteArray[i++] = wrappedByte; //auto unboxing
			}

			response.setContentType("image/jpeg");
			InputStream is = new ByteArrayInputStream(byteArray);
			IOUtils.copy(is, response.getOutputStream());
		}
	}
}
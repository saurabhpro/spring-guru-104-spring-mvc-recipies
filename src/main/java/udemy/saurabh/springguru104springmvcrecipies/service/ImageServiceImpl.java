package udemy.saurabh.springguru104springmvcrecipies.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import udemy.saurabh.springguru104springmvcrecipies.model.Recipe;
import udemy.saurabh.springguru104springmvcrecipies.repositories.IRecipeRepository;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements IImageService {
	private final IRecipeRepository recipeRepository;

	@Autowired
	public ImageServiceImpl(IRecipeRepository recipeService) {
		this.recipeRepository = recipeService;
	}

	private static Byte[] getBytesArray(MultipartFile file) throws IOException {
		Byte[] byteObjects = new Byte[file.getBytes().length];

		int i = 0;

		for (byte b : file.getBytes()) {
			byteObjects[i++] = b;
		}

		return byteObjects;
	}

	@Override
	@Transactional
	public void saveImageFile(Long recipeId, MultipartFile file) {

		Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(RuntimeException::new);

		try {
			Byte[] byteObjects = getBytesArray(file);

			recipe.setImage(byteObjects);

		} catch (IOException e) {
			//todo handle better
			log.error("Error occurred", e);

			e.printStackTrace();
		}

		recipeRepository.save(recipe);
	}
}

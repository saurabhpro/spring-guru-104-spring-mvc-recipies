package udemy.saurabh.springguru104springmvcrecipies.service;

import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
	void saveImageFile(Long recipeId, MultipartFile file);
}

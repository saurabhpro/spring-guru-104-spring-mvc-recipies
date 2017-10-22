package udemy.saurabh.springguru104springmvcrecipies.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import udemy.saurabh.springguru104springmvcrecipies.model.Category;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest    //need in java 9  --add-modules=java.xml.bind
public class ICategoryRepositoryIT {

	@Autowired
	public ICategoryRepository categoryRepository;

	@Test
	void findTopOneByDescriptionAmerican() {
		Optional<Category> categoryOptional = categoryRepository.findTopOneByDescription("American");

		Assertions.assertEquals("American", categoryOptional.orElseThrow(() -> new RuntimeException("Err")).getDescription());
	}


	@Test
	@DirtiesContext
		// reloads the context - (not restart)
	void findTopOneByDescriptionMexican() {
		Optional<Category> categoryOptional = categoryRepository.findTopOneByDescription("Mexican");

		Assertions.assertEquals("Mexican", categoryOptional.orElseThrow(RuntimeException::new).getDescription());
	}
}
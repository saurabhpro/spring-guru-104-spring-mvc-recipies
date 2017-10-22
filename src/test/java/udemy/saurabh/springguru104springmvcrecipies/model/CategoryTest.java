package udemy.saurabh.springguru104springmvcrecipies.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryTest {

	private Category category;

	@BeforeEach
	void setUp() {
		category = new Category();
	}

	@Test
	void getId() {
		//given
		Long idValue = 4L;

		// when
		category.setId(idValue);

		// then
		Assertions.assertEquals(idValue, category.getId());
	}

}
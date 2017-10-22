package udemy.saurabh.springguru104springmvcrecipies.model.converters;


import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import udemy.saurabh.springguru104springmvcrecipies.model.Category;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.CategoryCommand;

/**
 * Created by jt on 6/21/17.
 * A converter converts a source object of type {@code S} to a target of type {@code T}.
 */
@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

	@Synchronized
	@Nullable
	@Override
	public Category convert(CategoryCommand source) {
		if (source == null) {
			return null;
		}

		final Category category = new Category();

		category.setId(source.getId());
		category.setDescription(source.getDescription());

		return category;
	}
}

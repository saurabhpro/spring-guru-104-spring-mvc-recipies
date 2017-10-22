package udemy.saurabh.springguru104springmvcrecipies.model.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import udemy.saurabh.springguru104springmvcrecipies.model.Difficulty;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jt on 6/21/17.
 */
@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
	private Long id;

	@NotBlank
	@Size(min = 4, max = 255)
	private String description;

	@NotNull
	@Min(0)
	@Max(120)
	private Integer prepTime;

	@NotNull
	@Min(1)
	@Max(240)
	private Integer cookTime;

	@NotNull
	@Min(1)
	@Max(24)
	private Integer servings;

	private String source;

	@URL
	private String url;

	@NotBlank
	private String directions;

	private Byte[] image;
	private Set<IngredientCommand> ingredients = new HashSet<>();
	private Difficulty difficulty;
	private NotesCommand notes;
	private Set<CategoryCommand> categories = new HashSet<>();
}

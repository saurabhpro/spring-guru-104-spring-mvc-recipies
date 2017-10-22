package udemy.saurabh.springguru104springmvcrecipies.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String source;
	private String url;

	@Lob
	private String directions;

	/*
	 * How the enum values will look in DB, default in "Ordinal"
	 */
	@Enumerated(value = EnumType.STRING)
	private Difficulty difficulty;

	/*
	 * Note that other than "categories"
	 * other name don't matter - so you can have "recipe_id" or something like "op"
	 * But be smart and choose good names, but it can create circular loop in Lombok
	 *
	 * The joinColumns attribute is responsible for the columns mapping of the owning side(Recipe).
	 * The "name" attribute contains the column name for the current table (the name of col on db),
	 * the "referencedColumnName" attribute (optional) contains the primary key column name
	 * (in our case the primary key column of the Recipe table is id) of the owning side.
	 *
	 * inverseJoinColumns it is the column of Category that will be used as a part of the JoinTable relationship
	 * between the current Recipe and Category.
	 *
	 * If you don't specify joinColumns and inverseJoinColumns on the @JoinTable annotation,
	 * the persistence provider assumes a "primary key to primary key" join relationship and
	 * still store the equivalent ID columns for two related entities in the table by default.
	 */
	@ManyToMany
	@JoinTable(name = "recipe_category",
			joinColumns = @JoinColumn(name = "op"), // referencedColumnName is not provided so primary key is assumed
			inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();

	/*
	 * The Cascade.All means any operation like Delete, Fetch etc will also be applied to other type in question
	 * as noted above in this case we have not provided joinColumns or inverseJoinColumns
	 * - so internally both tables primary keys are used
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
	private Set<Ingredient> ingredients = new HashSet<>();

	/*
	 * To store large amount of data in DB
	 */
	@Lob
	private Byte[] image;

	/*
	 * Deleting a Recipe will delete the Notes related to it
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private Notes notes;

	public void setNotes(Notes notes) {
		if (notes != null) {
			this.notes = notes;
			notes.setRecipe(this);
		}
	}

	/**
	 * add <code>ingredient</code> to the set of ingredients
	 *
	 * @param ingredient to be added
	 */
	public void addIngredient(Ingredient ingredient) {
		ingredient.setRecipe(this);
		this.ingredients.add(ingredient);
	}
}

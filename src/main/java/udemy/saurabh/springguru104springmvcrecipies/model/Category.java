package udemy.saurabh.springguru104springmvcrecipies.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	@ManyToMany(mappedBy = "categories")
	private Set<Recipe> recipe = new HashSet<>();
	// categories property of Recipe, mappedBy will not create additional column in this table
	// use mappedBy to access columns of the owning side
}

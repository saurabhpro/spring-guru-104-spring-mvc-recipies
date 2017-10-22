package udemy.saurabh.springguru104springmvcrecipies.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import udemy.saurabh.springguru104springmvcrecipies.model.Recipe;

@Repository
public interface IRecipeRepository extends CrudRepository<Recipe, Long> {
}

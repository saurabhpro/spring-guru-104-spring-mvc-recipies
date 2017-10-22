package udemy.saurabh.springguru104springmvcrecipies.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import udemy.saurabh.springguru104springmvcrecipies.model.Category;

import java.util.Optional;

@Repository
public interface ICategoryRepository extends CrudRepository<Category, Long> {

	// see the magic
	Optional<Category> findTopOneByDescription(String description);
}

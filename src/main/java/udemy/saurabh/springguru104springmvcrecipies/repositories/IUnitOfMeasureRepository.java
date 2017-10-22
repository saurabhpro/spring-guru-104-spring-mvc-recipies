package udemy.saurabh.springguru104springmvcrecipies.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import udemy.saurabh.springguru104springmvcrecipies.model.UnitOfMeasure;

import java.util.Optional;

@Repository
public interface IUnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

	Optional<UnitOfMeasure> findTopOneByDescription(String description);
}

package udemy.saurabh.springguru104springmvcrecipies.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import udemy.saurabh.springguru104springmvcrecipies.model.UnitOfMeasure;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.UnitOfMeasureCommand;
import udemy.saurabh.springguru104springmvcrecipies.model.converters.UnitOfMeasureToUnitOfMeasureCommand;
import udemy.saurabh.springguru104springmvcrecipies.repositories.IUnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

	private IUnitOfMeasureService service;

	@Mock
	private IUnitOfMeasureRepository unitOfMeasureRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
		service = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
	}

	@Test
	void listAllUoms() throws Exception {
		//given
		Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();

		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId(1L);
		unitOfMeasures.add(uom1);

		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId(2L);
		unitOfMeasures.add(uom2);

		when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

		//when
		Set<UnitOfMeasureCommand> commands = service.listAllUnitOfMeasures();

		//then
		assertEquals(2, commands.size());
		verify(unitOfMeasureRepository, times(1)).findAll();
	}

}
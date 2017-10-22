package udemy.saurabh.springguru104springmvcrecipies.model.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.saurabh.springguru104springmvcrecipies.model.UnitOfMeasure;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.UnitOfMeasureCommand;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

	private static final String DESCRIPTION = "description";
	private static final Long LONG_VALUE = 1L;

	private UnitOfMeasureCommandToUnitOfMeasure converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new UnitOfMeasureCommandToUnitOfMeasure();

	}

	@Test
	void testNullParamter() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new UnitOfMeasureCommand()));
	}

	@Test
	void convert() throws Exception {
		//given
		UnitOfMeasureCommand command = new UnitOfMeasureCommand();
		command.setId(LONG_VALUE);
		command.setDescription(DESCRIPTION);

		//when
		UnitOfMeasure uom = converter.convert(command);

		//then
		assertNotNull(uom);
		assertEquals(LONG_VALUE, uom.getId());
		assertEquals(DESCRIPTION, uom.getDescription());

	}

}
package udemy.saurabh.springguru104springmvcrecipies.model.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import udemy.saurabh.springguru104springmvcrecipies.model.UnitOfMeasure;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.UnitOfMeasureCommand;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by jt on 6/21/17.
 */
class UnitOfMeasureToUnitOfMeasureCommandTest {

	private static final String DESCRIPTION = "description";
	private static final Long LONG_VALUE = 1L;

	private UnitOfMeasureToUnitOfMeasureCommand converter;

	@BeforeEach
	void setUp() throws Exception {
		converter = new UnitOfMeasureToUnitOfMeasureCommand();
	}

	@Test
	void testNullObjectConvert() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	void testEmptyObj() throws Exception {
		assertNotNull(converter.convert(new UnitOfMeasure()));
	}

	@Test
	void convert() throws Exception {
		//given
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(LONG_VALUE);
		uom.setDescription(DESCRIPTION);
		//when
		UnitOfMeasureCommand uomc = converter.convert(uom);

		//then
		assert uomc != null;
		assertEquals(LONG_VALUE, uomc.getId());
		assertEquals(DESCRIPTION, uomc.getDescription());
	}

}
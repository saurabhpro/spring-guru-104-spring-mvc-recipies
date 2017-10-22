package udemy.saurabh.springguru104springmvcrecipies.model.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import udemy.saurabh.springguru104springmvcrecipies.model.UnitOfMeasure;
import udemy.saurabh.springguru104springmvcrecipies.model.commands.UnitOfMeasureCommand;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

	@Synchronized
	@Nullable
	@Override
	public UnitOfMeasureCommand convert(UnitOfMeasure source) {

		if (source == null) {
			return null;
		}

		final UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
		uomc.setId(source.getId());
		uomc.setDescription(source.getDescription());

		return uomc;
	}
}

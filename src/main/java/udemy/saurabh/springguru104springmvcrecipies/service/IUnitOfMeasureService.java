package udemy.saurabh.springguru104springmvcrecipies.service;

import udemy.saurabh.springguru104springmvcrecipies.model.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface IUnitOfMeasureService {
	Set<UnitOfMeasureCommand> listAllUnitOfMeasures();
}

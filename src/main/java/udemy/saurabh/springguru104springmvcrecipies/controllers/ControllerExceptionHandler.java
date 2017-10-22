package udemy.saurabh.springguru104springmvcrecipies.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	// since as we are handling the exception status code gets overridden as 200
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleBadRequests(Exception exception) {

		log.error("Handling bad request exception");
		log.error(exception.getMessage());

		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("400error");
		modelAndView.addObject("exception", exception);

		return modelAndView;
	}
}

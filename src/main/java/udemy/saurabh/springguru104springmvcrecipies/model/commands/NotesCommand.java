package udemy.saurabh.springguru104springmvcrecipies.model.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by jt on 6/21/17.
 */
@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {
	private Long id;
	private String recipeNotes;

}

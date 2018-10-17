package seedu.jxmusic.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Model;

/**
* Clears the library.
*/
public class ClearCommand extends Command {

   public static final String COMMAND_PHRASE = "clear";
   public static final String MESSAGE_SUCCESS = "Library has been cleared!";


   @Override
   public CommandResult execute(Model model) {
       requireNonNull(model);
       model.resetData(new Library());
       return new CommandResult(MESSAGE_SUCCESS);
   }
}

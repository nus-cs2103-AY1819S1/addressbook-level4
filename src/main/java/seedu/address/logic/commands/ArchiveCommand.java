package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ArchiveListEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists archived Persons in the address book to the user.
 */
public class ArchiveCommand extends Command {

  public static final String COMMAND_WORD = "archive";

  public static final String MESSAGE_SUCCESS = "Listed all archived Persons";


  @Override
  public CommandResult runBody(Model model, CommandHistory history) {
    requireNonNull(model);
    model.updateArchivedPersonList(PREDICATE_SHOW_ALL_PERSONS);
    EventsCenter.getInstance().post(new ArchiveListEvent());
    return new CommandResult(MESSAGE_SUCCESS);
  }
}

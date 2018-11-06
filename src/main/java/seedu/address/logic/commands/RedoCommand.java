package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VOLUNTEERS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.RecordChangeEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Context;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s address book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoAddressBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoAddressBook();

        model.updateFilteredVolunteerList(PREDICATE_SHOW_ALL_VOLUNTEERS);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        if (model.getContextId().equals(Context.RECORD_CONTEXT_ID)) {
            EventsCenter.getInstance().post(new RecordChangeEvent(model.getSelectedEvent()));
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}

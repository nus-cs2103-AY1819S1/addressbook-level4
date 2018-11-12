package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.HideStaffPanelEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Reverts the {@code model}'s address book to its previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!" + "\n"
            + "Any staff panel previously selected will now be unselected";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoSsenisub()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoSsenisub();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        EventsCenter.getInstance().post(new HideStaffPanelEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

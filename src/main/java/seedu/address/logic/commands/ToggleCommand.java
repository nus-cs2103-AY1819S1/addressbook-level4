package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.scene.control.Tab;
import seedu.address.commons.events.ui.EventPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.TabPanelSelectionChangedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.TabPanel;

/**
 * Toggles between the various GUI tabs.
 */
public class ToggleCommand extends Command {

    public static final String COMMAND_WORD = "toggle";
    public static final String COMMAND_WORD_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "(alias: " + COMMAND_WORD_ALIAS + ")"
            + ": Toggles to the adjacent GUI tab.\n"
            + "Parameters: None\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_TOGGLE_SUCCESS = "Toggled";

    public ToggleCommand () {
        new TabPanelSelectionChangedEvent();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        TabPanel panel = new TabPanel(model.getFilteredEventListByDate(), model.getFilteredPersonList(),
                model.getEventTagList());

        return new CommandResult(String.format(MESSAGE_TOGGLE_SUCCESS));
    }
}

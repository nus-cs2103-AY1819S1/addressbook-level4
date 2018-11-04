package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SWITCH;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ContextChangeEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Context;
import seedu.address.model.Model;

/**
 * Adds a volunteer to the address book.
 */
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches the context of the system "
            + "Parameters: "
            + PREFIX_SWITCH + "CONTEXT_ID\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SWITCH + Context.VOLUNTEER_CONTEXT_ID;

    public static final String MESSAGE_SUCCESS = "Context switched to %1$s";

    private final String contextId;

    public SwitchCommand(String contextToSwitch) {
        requireNonNull(contextToSwitch);
        contextId = contextToSwitch;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        model.setCurrentContext(contextId);
        model.updateFilteredRecordList(Model.PREDICATE_SHOW_ALL_RECORDS);
        model.resetStatePointer();

        EventsCenter.getInstance().post(new ContextChangeEvent(contextId));
        return new CommandResult(String.format(MESSAGE_SUCCESS, model.getContextName()));
    }
}

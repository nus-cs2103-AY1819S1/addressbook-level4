package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SWITCH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Context;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
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
        return new CommandResult(String.format(MESSAGE_SUCCESS, model.getContextName()));
    }
}

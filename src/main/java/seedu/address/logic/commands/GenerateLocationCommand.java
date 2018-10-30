package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
public class GenerateLocationCommand extends Command {

    public static final String COMMAND_WORD = "generateLocation";
    public static final String COMMAND_WORD_ALIAS = "gl";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "(alias: " + COMMAND_WORD_ALIAS + ")"
            + ": Generates a location for a specific meeting."
            + "Parameters: "
            + PREFIX_NAME + "MEETING_NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Project Meeting";

    public static final String MESSAGE_EVENT_DOES_NOT_EXIST = "This event does not exist!";

    public static final String MESSAGE_SUCCESS = "Meeting location generated!";

    private final Event meetingLocationEvent;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public GenerateLocationCommand(Event event) {
        requireNonNull(event);
        meetingLocationEvent = event;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasEvent(meetingLocationEvent)) {
            throw new CommandException(MESSAGE_EVENT_DOES_NOT_EXIST);
        }

        // TODO - to work on what to do after command
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, meetingLocationEvent));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GenerateLocationCommand // instanceof handles nulls
                && meetingLocationEvent.equals(((GenerateLocationCommand) other).meetingLocationEvent));
    }
}

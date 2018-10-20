package seedu.address.logic.commands;

//@@author EatOrBeEaten

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.storage.EmailLoadEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.email.Subject;

/**
 * Loads an email from the computer
 */
public class LoadEmailCommand extends Command {

    public static final String COMMAND_WORD = "load_email";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Loads the email identified by its subject.\n"
            + "Parameters SUBJECT\n"
            + "Example: " + COMMAND_WORD + " Meeting on Friday";

    public static final String MESSAGE_SUCCESS = "Loading email: %s";

    private final Subject subject;

    public LoadEmailCommand(Subject subject) {
        this.subject = subject;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new EmailLoadEvent(subject.value));
        return new CommandResult(String.format(MESSAGE_SUCCESS, subject.value));
    }

}

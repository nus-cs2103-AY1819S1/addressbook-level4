package seedu.address.logic.commands.google;

import seedu.address.logic.commands.Command;

//@@author chivent

/**
 * Shell for Google-type commands
 */
public abstract class GoogleCommand extends Command {

    public static final String COMMAND_WORD = "g";
    public static final String TYPE = "";
    public static final String FULL_CMD = COMMAND_WORD + " " + TYPE;

    protected String parameter;

    GoogleCommand(String parameter) {
        this.parameter = parameter;
    }
    GoogleCommand() {
        parameter = "";
    }
}

package seedu.address.logic.parser;

import seedu.address.logic.commands.EncryptCommand;

//@@author JasonChong96
/**
 * Parses input arguments and creates a new EncryptCommand object
 */
public class EncryptCommandParser implements Parser<EncryptCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EncryptCommand
     * and returns an EncryptCommand object for execution.
     */
    @Override
    public EncryptCommand parse(String userInput) {
        return new EncryptCommand(userInput.trim());
    }
}

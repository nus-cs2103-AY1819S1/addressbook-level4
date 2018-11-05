package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.exceptions.NoUserSelectedException;

public class DecryptCommand extends Command {

    public static final String COMMAND_WORD = "decrypt";
    public static final String COMMAND_ALIAS = "de";
    public static final String MESSAGE_SUCCESS = "Decrypted string:\n%1$s";
    public static final String MESSAGE_FAILURE = "The input String was not encrypted using your encryption key.\n";

    private final String toDecrypt;

    public DecryptCommand(String toDecrypt) {
        requireNonNull(toDecrypt);
        this.toDecrypt = toDecrypt;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws NoUserSelectedException {
        requireNonNull(model);
        String decryptedString;
        try {
            decryptedString = model.decryptString(toDecrypt);
            return new CommandResult(String.format(MESSAGE_SUCCESS, decryptedString));
        } catch (IllegalValueException e) {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }

}

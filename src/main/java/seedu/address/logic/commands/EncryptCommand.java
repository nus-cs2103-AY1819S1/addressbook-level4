package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.exceptions.NoUserSelectedException;

public class EncryptCommand extends Command {

    public static final String COMMAND_WORD = "encrypt";
    public static final String COMMAND_ALIAS = "en";
    public static final String MESSAGE_SUCCESS = "Encrypted string:\n%1$s";

    private final String toEncrypt;

    public EncryptCommand(String toEncrypt) {
        requireNonNull(toEncrypt);
        this.toEncrypt = toEncrypt;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws NoUserSelectedException {
        requireNonNull(model);
        String encryptedString = model.encryptString(toEncrypt);
        return new CommandResult(String.format(MESSAGE_SUCCESS, encryptedString));
    }

}

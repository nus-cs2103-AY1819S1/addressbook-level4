package seedu.address.logic.commands;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transformation.Transformation;
import seedu.address.storage.JsonConvertArgsStorage;

/**
 * @@author lancelotwillow
 * the class to create the convert command
 */
public class CreateConvertCommand extends Command {

    public static final String COMMAND_WORD = "create";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": add a new command, customised yourself.\n"
            + "Parameters: name operation|argument1|argument2\n"
            + "Example: " + COMMAND_WORD + " blurgray blur|0x8 colorspace|GRAY";
    private List<Transformation> cmds;
    private String name;

    public CreateConvertCommand(String name, List<Transformation> cmds) {
        if (cmds.isEmpty()) {
            throw new IllegalArgumentException("nothing inside the command arguments");
        } else {
            this.cmds = cmds;
            this.name = name;
        }
    }

    /**
     * to check whether the single argument tag is valid or not
     * @param transformation
     * @throws IllegalArgumentException
     */
    private void checkSingleValidation(Transformation transformation) throws IllegalArgumentException, IOException {
        //just a template, not only this
        String operation = transformation.toList().get(0);
        URL fileUrl = CreateConvertCommand.class.getResource("/imageMagic/commandTemplates/" + operation + ".json");
        if (fileUrl == null) {
            throw new IllegalArgumentException("Invalid argument, cannot find");
        }
        List<String> cmds = JsonConvertArgsStorage.retrieveCommandTemplate(fileUrl, operation);
        if (transformation.toList().size() != cmds.size() + 1) {
            throw new IllegalArgumentException("Invalid argument, cannot find");
        }
    }


    /**
     * to check the validation of the whole argument list
     */
    private void checkValidation() throws CommandException {
        Iterator<Transformation> iter = cmds.iterator();
        while (iter.hasNext()) {
            try {
                checkSingleValidation(iter.next());
            } catch (IllegalArgumentException | IOException e) {
                throw new CommandException(e.toString());
            }
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            checkValidation();
            JsonConvertArgsStorage.storeArgument(name, cmds);
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult("successfully create " + name);
    }
}


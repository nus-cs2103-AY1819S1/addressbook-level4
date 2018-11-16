package seedu.address.logic.commands;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.ImageMagickUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transformation.Transformation;
import seedu.address.storage.JsonConvertArgsStorage;

/**
 * @@author lancelotwillow
 * the class to create the convert command
 */
public class CreateApplyCommand extends Command {

    public static final String COMMAND_WORD = "create";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a new custom command.\n"
            + "Parameters: name operation|argument1|argument2 ...\n"
            + "Example: " + COMMAND_WORD + " blurgray blur|0x8 colorspace|GRAY";
    private List<Transformation> cmds;
    private String name;

    public CreateApplyCommand(String name, List<Transformation> cmds) {
        if (cmds.isEmpty()) {
            throw new IllegalArgumentException("Empty parameters! Please provide transformations to store.");
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
        URL fileUrl = CreateApplyCommand.class.getResource("/imageMagic/commandTemplates/" + operation + ".json");
        if (fileUrl == null) {
            throw new IllegalArgumentException();
        }
        List<String> patterns = JsonConvertArgsStorage.retrieveCommandTemplate(fileUrl, operation, "pattern");
        List<String> trans = transformation.toList();
        if (trans.size() != patterns.size() + 1) {
            throw new IllegalArgumentException(Messages.MESSAGE_INVALID_OPERATION_ARGUMENTS);
        }
        for (int i = 0; i < patterns.size(); i++) {
            if (!trans.get(i + 1).matches(patterns.get(i))) {
                throw new IllegalArgumentException(Messages.MESSAGE_INVALID_OPERATION_ARGUMENTS);
            }
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
                throw new CommandException(Messages.MESSAGE_INVALID_OPERATION_ARGUMENTS);
            }
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            checkValidation();
            JsonConvertArgsStorage.storeArgument(name, cmds, ImageMagickUtil.getCommandSaveFolder());
        } catch (IOException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_OPERATION_ARGUMENTS);
        }
        return new CommandResult("Successfully created " + name);
    }

    @Override
    public boolean equals(Object object) {
        CreateApplyCommand command = (CreateApplyCommand) object;
        return command == this || name.equals(command.name) && cmds.equals(command.cmds);
    }
}


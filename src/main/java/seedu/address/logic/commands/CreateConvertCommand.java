package seedu.address.logic.commands;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

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
            throw new IllegalArgumentException();
        }
        List<String> patterns = JsonConvertArgsStorage.retrieveCommandTemplate(fileUrl, operation, "pattern");
        List<String> trans = transformation.toList();
        if (trans.size() != patterns.size() + 1) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < patterns.size(); i++) {
            if (!trans.get(i + 1).matches(patterns.get(i))) {
                throw new IllegalArgumentException("the argument entered is invalid");
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
                throw new CommandException("the arguments entered is not valid\n,"
                        + "please check the argument of each transformation\n, see more details: " + MESSAGE_USAGE);
            }
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        try {
            checkValidation();
            JsonConvertArgsStorage.storeArgument(name, cmds, ImageMagickUtil.getCommandSaveFolder());
        } catch (IOException e) {
            throw new CommandException("the argument is invalid, see more details: " + MESSAGE_USAGE);
        }
        return new CommandResult("successfully create " + name);
    }
}


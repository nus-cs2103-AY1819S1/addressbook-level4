package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_NAME;

import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.model.Model;
import seedu.superta.model.tutorialgroup.TutorialGroup;

/**
 * Command that creates a tutorial group.
 */
public class CreateTutorialGroupCommand extends Command {
    public static final String COMMAND_WORD = "create-tutorial-group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a tutorial group.\n"
        + "Parameters: "
        + PREFIX_TUTORIAL_GROUP_NAME + "TUTORIAL-GROUP-NAME "
        + PREFIX_TUTORIAL_GROUP_ID + "TUTORIAL-GROUP-ID\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TUTORIAL_GROUP_NAME + "CS1101S Studio 04A "
        + PREFIX_TUTORIAL_GROUP_ID + "04a";

    public static final String MESSAGE_SUCCESS = "New tutorial group created: %1$s";

    private final TutorialGroup toAdd;

    public CreateTutorialGroupCommand(TutorialGroup tg) {
        requireNonNull(tg);
        toAdd = tg;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.addTutorialGroup(toAdd);
        model.commitSuperTaClient();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof CreateTutorialGroupCommand
            && toAdd.equals(((CreateTutorialGroupCommand) other).toAdd));
    }
}

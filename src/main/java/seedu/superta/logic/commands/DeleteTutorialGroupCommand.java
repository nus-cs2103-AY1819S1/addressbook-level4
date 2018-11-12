package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_ID;

import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.model.Model;
import seedu.superta.model.tutorialgroup.TutorialGroup;
import seedu.superta.model.tutorialgroup.exceptions.TutorialGroupNotFoundException;

/**
 * Command which deletes an existing tutorial group from the SuperTA client.
 */
public class DeleteTutorialGroupCommand extends Command {
    public static final String COMMAND_WORD = "delete-tutorial-group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a tutorial group.\n"
            + "Parameters: "
            + PREFIX_TUTORIAL_GROUP_ID + "TUTORIAL-GROUP-ID\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_GROUP_ID + "04a";

    public static final String MESSAGE_SUCCESS = "Tutorial group deleted: %s";
    public static final String MESSAGE_FAILURE = "No such tutorial group.";

    private final TutorialGroup toDelete;

    public DeleteTutorialGroupCommand(String idToDelete) {
        toDelete = new TutorialGroup(idToDelete, "toDelete");
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        requireNonNull(toDelete);

        try {
            model.deleteTutorialGroup(toDelete);
        } catch (TutorialGroupNotFoundException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        model.commitSuperTaClient();

        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete.getId()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTutorialGroupCommand // instanceof handles nulls
                && toDelete.equals(((DeleteTutorialGroupCommand) other).toDelete)); // state check
    }
}

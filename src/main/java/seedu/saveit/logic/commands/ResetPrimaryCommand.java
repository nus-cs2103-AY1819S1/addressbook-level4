package seedu.saveit.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.saveit.commons.core.Messages;
import seedu.saveit.commons.core.directory.Directory;
import seedu.saveit.logic.CommandHistory;
import seedu.saveit.logic.commands.exceptions.CommandException;
import seedu.saveit.model.Issue;
import seedu.saveit.model.Model;

/**
 * Star one existing solution for an issue.
 */
public class ResetPrimaryCommand extends Command {
    public static final String COMMAND_WORD = "resetprimary";
    public static final String COMMAND_ALIAS = "rp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remove highlight of one solution in the displayed solution list\n"
            + "Parameters: INDEX (must be the index shown in the list)\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Reset primary solution.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Directory currentDirectory = model.getCurrentDirectory();
        List<Issue> lastShownIssueList = model.getFilteredAndSortedIssueList();

        if (!currentDirectory.isIssueLevel() && !currentDirectory.isSolutionLevel()) {
            throw new CommandException(Messages.MESSAGE_WRONG_DIRECTORY);
        }

        Issue issueSelected = lastShownIssueList.get(currentDirectory.getIssue() - 1);

        Issue updatedIssue = issueSelected.resetPrimarySolution();

        model.updateIssue(issueSelected, updatedIssue);
        model.commitSaveIt();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATEMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Issue;
import seedu.address.model.Model;
import seedu.address.model.issue.Solution;

/**
 * Adds an issue to the saveIt.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an issue to the saveIt. "
            + "Parameters: "
            + PREFIX_STATEMENT + "ISSUE_STATEMENT "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_TAG + "algorithm "
            + PREFIX_TAG + "java";
    public static final String MESSAGE_ISSUE_SUCCESS = "New issue added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This issue already exists in the saveIt";

    private static final String dummyStatement = "dummyStatement";
    private static final String dummyDescription = "dummyDescription";
    private static final String MESSAGE_FAILED_ISSUE =
            "Issue has to be selected first before adding " + "solution";
    private static final String MESSAGE_SOLUTION_SUCCESS = "New solution added: %1$s";
    private boolean addSolution;
    private final Solution solutionToBeAdded;
    private final Issue toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Issue}
     */
    public AddCommand(Issue issue) {
        if (issue.getStatement().issue.equals(dummyStatement) && issue.getDescription().value
                .equals(dummyDescription)) {
            addSolution = true;
            assert (issue.getSolutions().size() == 1);
            solutionToBeAdded = issue.getSolutions().get(0);
            toAdd = null;
        } else {
            addSolution = false;
            requireNonNull(issue);
            toAdd = issue;
            solutionToBeAdded = null;
        }
    }

    /**
     * Add a solution to a existing issue in the issue list
     */
    private void addSolutionToIssue(Model model, int index) {
        List<Issue> lastShownList = model.getFilteredIssueList();
        Issue originalIssue = lastShownList.get(index - 1);
        List<Solution> newSolutionList = new ArrayList<>(originalIssue.getSolutions());
        newSolutionList.add(solutionToBeAdded);
        Issue newIssue = new Issue(originalIssue.getStatement(), originalIssue.getDescription(),
                newSolutionList, originalIssue.getTags());
        model.updateIssue(originalIssue, newIssue);
        model.updateFilteredIssueList(Model.PREDICATE_SHOW_ALL_ISSUES);
        model.commitSaveIt();
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        int issueIndex = model.getCurrentDirectory();
        if (addSolution) {
            try {
                if (issueIndex > 0) {
                    addSolutionToIssue(model, issueIndex);
                    return new CommandResult(String.format(MESSAGE_SOLUTION_SUCCESS, solutionToBeAdded));
                } else {
                    throw new CommandException(MESSAGE_FAILED_ISSUE);
                }
            } catch (NoSuchElementException e) {
                throw new CommandException(MESSAGE_FAILED_ISSUE);
            }
        }

        if (model.hasIssue(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addIssue(toAdd);
        model.commitSaveIt();
        addSolution = false;
        return new CommandResult(String.format(MESSAGE_ISSUE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}

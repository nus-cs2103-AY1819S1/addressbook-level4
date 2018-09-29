package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowBudgetViewEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.tag.TagContainsKeywordPredicate;


/**
 * View the budget of the whole hall or by their CCA.
 */
public class BudgetCommand extends Command {

    public static final String COMMAND_WORD = "budget";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View the budget of the whole hall or by each CCA "
        + "Parameters: "
        + "overall or " + PREFIX_TAG + "TAG\n"
        + "Example: " + COMMAND_WORD + " overall or " + COMMAND_WORD + " basketball\n";

    public static final String SHOWING_BUDGET_MESSAGE = "Display budget.";

    private final TagContainsKeywordPredicate predicate;

    public BudgetCommand(TagContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        EventsCenter.getInstance().post(new ShowBudgetViewEvent());
        return new CommandResult(SHOWING_BUDGET_MESSAGE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof BudgetCommand // instanceof handles nulls
            && predicate.equals(((BudgetCommand) other).predicate)); // state check
    }
}


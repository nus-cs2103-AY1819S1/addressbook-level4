package seedu.address.logic.commands;

import static seedu.address.logic.commands.UpdateCommand.MESSAGE_NON_EXISTENT_CCA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowBudgetViewEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.tag.TagContainsKeywordPredicate;

//@author ericyjw
/**
 * View the budget of the whole hall or by their CCA.
 *
 * @author ericyjw
 */
public class BudgetCommand extends Command {

    public static final String COMMAND_WORD = "budget";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View the budget of the whole hall or by each CCA\n"
        + "Parameters: no parameters\n"
        + "Example: " + COMMAND_WORD + "\n"
        + "or\n"
        + "Parameters: " + PREFIX_TAG + "CCA\n"
        + "Example: " + COMMAND_WORD + " basketball\n";

    public static final String SHOWING_BUDGET_MESSAGE = "Display budget.";

//    private final TagContainsKeywordPredicate predicate;

    private final CcaName ccaName;

//    public BudgetCommand(TagContainsKeywordPredicate predicate) {
//        this.predicate = predicate;
//    }

    public  BudgetCommand(CcaName ccaName) {
        this.ccaName = ccaName;
    }

    public BudgetCommand() {
        ccaName = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Cca> lastShownList = model.getFilteredCcaList();
        if (ccaName != null && !model.hasCca(ccaName)) {
            throw new CommandException(MESSAGE_NON_EXISTENT_CCA);
        }

        EventsCenter.getInstance().post(new ShowBudgetViewEvent(ccaName));
        return new CommandResult(SHOWING_BUDGET_MESSAGE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof BudgetCommand // instanceof handles nulls
            && ccaName.equals(((BudgetCommand) other).ccaName)); // state check
    }
}


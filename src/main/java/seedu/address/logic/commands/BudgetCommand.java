package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;


/**
 * View the budget of the whole hall or by their CCA.
 */
public class BudgetCommand extends Command {

	public static final String COMMAND_WORD = "budget";
	public static final String MESSAGE_USAGE = COMMAND_WORD + ": View the budget of the whole hall or by each CCA "
			+ "Parameters: "
			+ "overall or " + PREFIX_TAG + "TAG\n"
			+ "Example: " + COMMAND_WORD + " overall or " + COMMAND_WORD + " basketball\n";

	public static final String MESSAGE_SUCCESS_OVERALL = "The overall budget is: %1$s";
	public static final String MESSAGE_SUCCESS_CCA = PREFIX_TAG + " budget is: %1$s";

	private final NameContainsKeywordsPredicate predicate;

	public BudgetCommand(NameContainsKeywordsPredicate predicate) {
		this.predicate = predicate;
	}

	@Override
	public CommandResult execute(Model model, CommandHistory history) {
		requireNonNull(model);
		model.updateFilteredPersonList(predicate);
		return new CommandResult(
				String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof BudgetCommand // instanceof handles nulls
				&& predicate.equals(((BudgetCommand) other).predicate)); // state check
	}
}


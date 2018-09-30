package seedu.address.logic.commands;

import java.util.Arrays;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

public class EarningsCommand extends Command {

	public static final String COMMAND_WORD = "earnings";
	public static final String MESSAGE_SUCCESS = "Earning is successfully retrieved!";

	private String[] personToFind = new String[1];

	public EarningsCommand (String personName) {
		personToFind[0] = personName;
	}

	@Override
	public CommandResult execute(Model model, CommandHistory history) {

		// Execute the display of student's fees here
		requireNonNull(model);
		model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(personToFind)));

		Person targetPerson  = model.getFilteredPersonList().get(0);
		// Returns the command result
		return new CommandResult(targetPerson.getName() + "'s tuition fees is " + targetPerson.getFees().toString());
	}
}

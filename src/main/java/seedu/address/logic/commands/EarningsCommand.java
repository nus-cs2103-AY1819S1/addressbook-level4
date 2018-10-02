package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Finds and lists the tuition fee of a person in address book
 */
public class EarningsCommand extends Command {

    public static final String COMMAND_WORD = "earnings";

    public static final String MESSAGE_SUCCESS = "Earning is successfully retrieved!";

    private String[] personToFind = new String[1];

    /**
     * @param personName of the person to retrieve tuition fees from
     */
    public EarningsCommand (String personName) {
        personToFind[0] = personName;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        // Execute the display of student's fees here
        requireNonNull(model);
        model.updateFilteredPersonList(new
                NameContainsKeywordsPredicate(Arrays.asList(personToFind)));

        Person targetPerson = model.getFilteredPersonList().get(0);
        // Returns the command result
        return new CommandResult(targetPerson.getName() + "'s tuition fees is " + targetPerson.getFees().toString());
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.FeeFilterPredicate;
import seedu.address.model.person.Person;


/**
 * Finds and lists the list of person whose fee is larger than limit
 */

public class FilterByFeeCommand extends FilterCommand {

    public static final String COMMAND_WORD = "filterByFee";

    public static final String MESSAGE_SUCCESS = "Already filtered by fee!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": show the person list of whose fee is over given number "
            + "Parameters: "
            + "Limit Fee";

    private double limit;

    /**
     * filter by fee command
     *
     * @param limit
     */
    public FilterByFeeCommand(String limit) {


        this.limit = (double) Integer.parseInt(limit);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        // Execute the display of student's fees here
        requireNonNull(model);
        model.updateFilteredPersonList(new
                FeeFilterPredicate(limit));

        ObservableList<Person> targetList = model.getFilteredPersonList();
        // Returns the command result
        if (targetList.isEmpty()) {
            return new CommandResult("Cannot find person whose fee not less than " + limit + " !");
        }


        List<String> personNameList = new ArrayList<>();
        for (Person ppl : targetList) {

            personNameList.add(ppl.getName().fullName);
        }

        return new CommandResult("The person whose fee is not less than " + limit + " : " + personNameList.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterByFeeCommand // instanceof handles nulls
                && limit == ((FilterByFeeCommand) other).limit);
    }
}

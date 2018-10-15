package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.GradeFilterPredicate;
import seedu.address.model.person.Person;







/**
 * Finds and lists the list of person whose grade is between A to B.
 */

public class FilterByGradeCommand extends FilterCommand {

    public static final String COMMAND_WORD = "filterByGrade";

    public static final String MESSAGE_SUCCESS = "Already filtered by grade!";

    private double minLimit;

    private double maxLimit;

    /**
     * filter by grade command
     *
     * @param maxLimit
     * @param minLimit
     */
    public FilterByGradeCommand(Double minLimit, Double maxLimit) {
        this.minLimit = minLimit;
        this.maxLimit = maxLimit;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        // Execute the display of student's grades here
        requireNonNull(model);
        model.updateFilteredPersonList(new
                GradeFilterPredicate(minLimit, maxLimit));

        ObservableList<Person> targetList = model.getFilteredPersonList();
        // Returns the command result
        if (targetList.isEmpty()) {
            return new CommandResult("Cannot find person whose grade between " + minLimit + " and " + maxLimit +" !");
        }


        List<String> personNameList = new ArrayList<>();
        for (Person ppl : targetList) {

            personNameList.add(ppl.getName().fullName);
        }

        return new CommandResult("The person whose grade between " + minLimit + " and "
                + maxLimit + " : " + personNameList.toString());
    }
}

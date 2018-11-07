package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.EducationFilterPredicate;
import seedu.address.model.person.Person;


/**
 * Finds and lists the list of person whose grade is between A to B.
 */

public class FilterByEducationCommand extends FilterCommand {

    public static final String COMMAND_WORD = "filterByEducation";

    public static final String MESSAGE_SUCCESS = "Already filtered by Education!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": FilterByEducation. "
            + "Parameters: "
            + "Education Level";

    private String education;

    /**
     * filter by grade command
     *
     * @param education
     */
    public FilterByEducationCommand(String education) {
        this.education = education;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        // Execute the display of student's grades here
        requireNonNull(model);
        model.updateFilteredPersonList(new
                EducationFilterPredicate(education));

        ObservableList<Person> targetList = model.getFilteredPersonList();
        // Returns the command result
        if (targetList.isEmpty()) {
            return new CommandResult("Cannot find " + education + " education within the students list!");
        }


        List<String> personNameList = new ArrayList<>();
        for (Person ppl : targetList) {

            personNameList.add(ppl.getName().fullName);
        }

        return new CommandResult("The person whose education is " + education + " : " + personNameList.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterByEducationCommand // instanceof handles nulls
                && education.equals(((FilterByEducationCommand) other).education));
    }
}

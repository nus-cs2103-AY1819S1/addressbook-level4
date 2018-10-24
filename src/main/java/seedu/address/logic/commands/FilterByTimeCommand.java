package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.TimeFilterPredicate;
import seedu.address.model.person.*;


import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;


public class FilterByTimeCommand extends FilterCommand{
    public static final String COMMAND_WORD = "filterByTime";

    public static final String MESSAGE_SUCCESS = "Already filtered by Time!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": filter by tutorial time slot. "
            + "Parameters: "
            + "TIME ";

    private Time time;

    /**
     * filter by grade command
     *
     * @param args
     */

    public FilterByTimeCommand(String args) {
        this.time =new Time(args);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {

        // Execute the display of student's grades here
        requireNonNull(model);
        model.updateFilteredPersonList(new
                TimeFilterPredicate(time));

        ObservableList<Person> targetList = model.getFilteredPersonList();
        // Returns the command result
        if (targetList.isEmpty()) {
            return new CommandResult("Cannot find " + time.toString() +" education within the students list!");
        }


        List<String> personNameList = new ArrayList<>();
        for (Person ppl : targetList) {

            personNameList.add(ppl.getName().fullName);
        }

        return new CommandResult("The person whose education is " + time.toString() + " : " + personNameList.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterByTimeCommand // instanceof handles nulls
                && time.equals(((FilterByTimeCommand)other).time));
    }
}

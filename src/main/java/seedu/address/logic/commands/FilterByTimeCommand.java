package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Time;
import seedu.address.model.person.TimeFilterPredicate;

/**
 * Finds and lists the list of person whose time is between A to B.
 */

public class FilterByTimeCommand extends FilterCommand {
    public static final String COMMAND_WORD = "filterByTime";

    public static final String MESSAGE_SUCCESS = "Already filtered by Time!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": filter by tutorial time slot. "
            + PREFIX_TIME + "TIME ";

    private Time time;

    /**
     * filter by grade command
     *
     * @param args
     */

    public FilterByTimeCommand(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TIME);

        if (!argMultimap.getValue(PREFIX_TIME).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterByTimeCommand.MESSAGE_USAGE));
        }

        time = new Time(argMultimap.getValue(PREFIX_TIME).get());
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
            return new CommandResult("Cannot find " + time.toString() + " slot within the students list!");
        }


        List<String> personNameList = new ArrayList<>();
        for (Person ppl : targetList) {
            personNameList.add(ppl.getName().fullName);
        }

        return new CommandResult("The person whose timeSlot is "
                + time.toString() + " : " + personNameList.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterByTimeCommand && time.equals(((FilterByTimeCommand) other).time));
    }
}

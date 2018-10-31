package seedu.parking.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import java.util.function.Predicate;

import seedu.parking.commons.core.Messages;
import seedu.parking.logic.CommandHistory;
import seedu.parking.logic.commands.exceptions.CommandException;
import seedu.parking.model.Model;
import seedu.parking.model.carpark.CarparkContainsKeywordsPredicate;
import seedu.parking.model.carpark.CarparkFilteringPredicate;


/**
 * Filters car parks using to flags
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";
    public static final String COMMAND_ABBREVIATION = "fi";
    public static final String FORMAT = "filter f/ DAY START_TIME END_TIME n/ ct/ CARPARK_TYPE";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters the carpark based using flags.\n"
            + "Flags:\n"
            + "> Night Parking: n/ \n"
            + "> Free Parking: f/ [day] [start time] [end time]     Example: filter f/ SUN 7.30AM 8.30PM\n"
            + "> Car Park Type: ct/ [car park type]     Example: filter ct/ basement\n"
            + "   SURFACE\n" + "   MUITISTOREY\n" + "   BASEMENT\n" + "   COVERED\n" + "   MECHANISED";

    //public static final String MESSAGE_FILTER_CARPARK_SUCCESS = "Filtered Car Parks.";
    private Predicate predicate;
    private String[] flags;

    /**
     * Creates a FilterCommand with the relevant flags
     */
    public FilterCommand(String[] flags) {
        this.flags = flags;
        this.predicate = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);
        List<String> flagList = Arrays.asList(flags);

        // Todo: Location based filtering
        // Get last findCommand predicate
        CarparkContainsKeywordsPredicate locationPredicate = model.getLastPredicateUsedByFindCommand();
        List<String> locationKeywords = locationPredicate.getKeywords();

        this.predicate = new CarparkFilteringPredicate(locationKeywords, flagList);
        model.updateFilteredCarparkList(predicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_CARPARKS_LISTED_OVERVIEW, model.getFilteredCarparkList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}

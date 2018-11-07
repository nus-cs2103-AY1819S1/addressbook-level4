package seedu.parking.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import com.sun.istack.Nullable;

import seedu.parking.commons.core.EventsCenter;
import seedu.parking.commons.core.Messages;
import seedu.parking.commons.events.ui.FilterResultChangedEvent;
import seedu.parking.logic.CommandHistory;
import seedu.parking.logic.commands.exceptions.CommandException;
import seedu.parking.logic.parser.CarparkTypeParameter;
import seedu.parking.logic.parser.FreeParkingParameter;
import seedu.parking.logic.parser.ParkingSystemTypeParameter;
import seedu.parking.model.Model;
import seedu.parking.model.carpark.Carpark;
import seedu.parking.model.carpark.CarparkContainsKeywordsPredicate;
import seedu.parking.model.carpark.CarparkFilteringPredicate;

/**
 * Filters the list of car parks returned by the previous find command with the use of flags
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";
    public static final String FORMAT = "filter f/ DAY START_TIME END_TIME ct/ CARPARK_TYPE ps/ SYSTEM_TYPE a/ n/ st/";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters the list of car parks returned by the previous find command with the use of flags. "
            + "Multiple flags can be used and in any order.\n"
            + "Note: User must first find a list of car parks using the find command.\n"
            + "Valid flags:\n"
            + "> Available: a/ \n"
            + "> Night Parking: n/ \n"
            + "> Short-term Parking: st/ \n"
            + "> Free Parking: f/ [day] [start time] [end time]     Example: filter f/ SUN 7.30AM 8.30PM\n"
            + "> Car Park Type: ct/ [car park type]     Example: filter ct/ basement\n"
            + "> Parking System Type: ps/ [parking system type]     Example: filter ps/ coupon\n";

    private Predicate predicate;
    private final List<String> flagList;
    private final FreeParkingParameter freeParkingParameter;
    private final CarparkTypeParameter carparkTypeParameter;
    private final ParkingSystemTypeParameter parkingSystemTypeParameter;

    /**
     * Creates a FilterCommand with the relevant flags
     */
    public FilterCommand(List<String> flagList, @Nullable FreeParkingParameter freeParkingParameter,
        @Nullable CarparkTypeParameter carparkTypeParameter,
        @Nullable ParkingSystemTypeParameter parkingSystemTypeParameter) {
        this.predicate = null;
        this.flagList = flagList;
        this.freeParkingParameter = freeParkingParameter;
        this.carparkTypeParameter = carparkTypeParameter;
        this.parkingSystemTypeParameter = parkingSystemTypeParameter;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        CarparkContainsKeywordsPredicate locationPredicate = model.getLastPredicateUsedByFindCommand();
        if (locationPredicate == null) {
            throw new CommandException(Messages.MESSAGE_FINDCOMMAND_NEEDS_TO_BE_EXECUTED_FIRST);
        }

        List<String> locationKeywords = locationPredicate.getKeywords();
        predicate = new CarparkFilteringPredicate(locationKeywords, flagList, freeParkingParameter,
                carparkTypeParameter, parkingSystemTypeParameter);

        model.updateFilteredCarparkList(predicate);

        EventsCenter.getInstance().post(new FilterResultChangedEvent(
                model.getFilteredCarparkList().toArray(new Carpark[]{})));
        return new CommandResult(
                String.format(Messages.MESSAGE_CARPARKS_LISTED_OVERVIEW, model.getFilteredCarparkList().size()));
    }

    // Please fix the last line, it will give NULLPOINTEREXCEPTION because predicate is initialized as null value.
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof FilterCommand; // instanceof handles nulls
                //&& predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}

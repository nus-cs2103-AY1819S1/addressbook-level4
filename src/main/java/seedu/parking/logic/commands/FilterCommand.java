package seedu.parking.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.parking.commons.core.Messages;
import seedu.parking.logic.CommandHistory;
import seedu.parking.logic.commands.exceptions.CommandException;
import seedu.parking.logic.parser.CarparkTypeParameter;
import seedu.parking.logic.parser.FreeParkingParameter;
import seedu.parking.model.Model;
import seedu.parking.model.carpark.CarparkContainsKeywordsPredicate;
import seedu.parking.model.carpark.CarparkFilteringPredicate;

/**
 * Filters the list of car parks returned by the previous find command with the use of flags
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";
    public static final String COMMAND_ALIAS = "fi";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters the list of car parks returned by the previous find command with the use of flags. Multiple flags can be used and in any order.\n"
            + "Note: User must first find a list of car parks using the find command.\n"
            + "Valid flags:\n"
            + "> Night Parking: n/ \n"
            + "> Free Parking: f/ [day] [start time] [end time]     Example: filter f/ SUN 7.30AM 8.30PM\n"
            + "> Car Park Type: ct/ [car park type]     Example: filter ct/ basement\n";

    private Predicate predicate;
    private final List<String> flagList;
    private final FreeParkingParameter freeParkingParameter;
    private final CarparkTypeParameter carparkTypeParameter;

    /**
     * Creates a FilterCommand with the relevant flags
     */
    public FilterCommand(List<String> flagList, FreeParkingParameter freeParkingParameter, CarparkTypeParameter carparkTypeParameter) {
        this.predicate = null;
        this.flagList = flagList;
        this.freeParkingParameter = freeParkingParameter;
        this.carparkTypeParameter = carparkTypeParameter;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        CarparkContainsKeywordsPredicate locationPredicate = model.getLastPredicateUsedByFindCommand();
        if (locationPredicate == null) {
            throw new CommandException(Messages.MESSAGE_FINDCOMMAND_NEEDS_TO_BE_EXECUTED_FIRST);
        }

        List<String> locationKeywords = locationPredicate.getKeywords();
        predicate = new CarparkFilteringPredicate(locationKeywords, flagList, freeParkingParameter, carparkTypeParameter);

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

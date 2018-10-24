package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.carpark.CarparkHasFreeParkingPredicate;
import seedu.address.model.carpark.CarparkHasNightParkingPredicate;
import seedu.address.model.carpark.CarparkIsOfTypePredicate;


/**
 * Filters car parks using to flags
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";
    public static final String COMMAND_ALIAS = "fi";

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

        // Currently can only have one flag at a time.
        if (flagList.contains("n/")) {
            this.predicate = new CarparkHasNightParkingPredicate();
            model.updateFilteredCarparkList(predicate);
        }
        if (flagList.contains("f/")) {
            int index = flagList.indexOf("f/");

            // Can accept small letters too
            String day = flagList.get(index + 1).toUpperCase();
            System.out.println("day: " + day);

            String startTime = flagList.get(index + 2);
            System.out.println("startTime: " + startTime);

            String endTime = flagList.get(index + 3);
            System.out.println("endTime: " + endTime);

            this.predicate = new CarparkHasFreeParkingPredicate(day, startTime, endTime);
            model.updateFilteredCarparkList(predicate);
        }
        if (flagList.contains("ct/")) {
            int index2 = flagList.indexOf("ct/");

            String carparkType = flagList.get(index2 + 1).toUpperCase();
            System.out.println("carparkType: " + carparkType);

            this.predicate = new CarparkIsOfTypePredicate(carparkType);
            model.updateFilteredCarparkList(predicate);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_CARPARKS_LISTED_OVERVIEW, model.getFilteredCarparkList().size()));
        //throw new CommandException("filter command executed.");
        //return new CommandResult(String.format(MESSAGE_FILTER_CARPARK_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
        //|| (other instanceof DeleteCommand // instanceof handles nulls
        //&& targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}

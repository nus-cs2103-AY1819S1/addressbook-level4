package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.carpark.CarparkHasNightParkingPredicate;

/**
 * To be added
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters the carpark depending on the tags attached.\n"
            + "Parameters: Tags ... \n"
            + "Example: " + COMMAND_WORD + "f/TRUE";

    //public static final String MESSAGE_FILTER_CARPARK_SUCCESS = "Filtered Carparks.";
    private final CarparkHasNightParkingPredicate predicate;

    private String[] flags;

    public FilterCommand(String[] flags) {
        this.flags = flags;
        this.predicate = new CarparkHasNightParkingPredicate("YES");
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);
        List<String> flagList = Arrays.asList(flags);
        System.out.println(flagList.contains("n/"));
        if (flagList.contains("n/")) {
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

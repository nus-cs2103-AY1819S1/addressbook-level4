package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;

/**
 * Favourite an event for it to show up as a notification on startup
 * To do : documentation
 */

public class FavouriteCommand extends Command {

    public static final String COMMAND_WORD = "favourite";
    public static final String COMMAND_WORD_ALIAS = "fv";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Favourites the event identified by the date and index number used in the displayed event list.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_INDEX + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2018-09-18 "
            + PREFIX_INDEX + "1";

    public static final String MESSAGE_FAVOURITE_EVENT_SUCCESS = "Favorite Event: ";

    private final EventDate targetDate;
    private final Index targetIndex;

    public FavouriteCommand(EventDate targetDate, Index targetIndex) {
        this.targetDate = targetDate;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<List<Event>> lastShownList = model.getFilteredEventListByDate();

        // check if date exists in events in lastShownList
        if (lastShownList.isEmpty() || lastShownList.stream()
                .noneMatch(list -> list.get(0).getEventDate().equals(targetDate))) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_DATE);
        }

        List<List<Event>> targetDateList =
                lastShownList.stream()
                        .filter(list -> list.get(0).getEventDate().equals(targetDate))
                        .collect(Collectors.toList());

        // lastShownList should only have one list matching a given specific EventDate
        assert(targetDateList.size() == 1);

        List<Event> listToFavouriteFrom = targetDateList.get(0);

        if (targetIndex.getZeroBased() >= listToFavouriteFrom.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event favouriteEvent = listToFavouriteFrom.get(targetIndex.getZeroBased());

        model.updateFavourite(favouriteEvent);

        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_FAVOURITE_EVENT_SUCCESS
                + favouriteEvent.getEventName() + " on " + favouriteEvent.getEventDate()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavouriteCommand // instanceof handles nulls
                && targetDate.equals(((FavouriteCommand) other).targetDate)
                && targetIndex.equals(((FavouriteCommand) other).targetIndex)); // state check
    }

}

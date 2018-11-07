package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAddress;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Edits the address of an existing event in the address book.
 */
public class EditEventAddressCommand extends Command {

    public static final String COMMAND_WORD = "editEventAddress";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the address of the event identified "
            + "by the date and corresponding index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_INDEX + "INDEX (must be a positive integer) "
            + PREFIX_ADDRESS + "ADDRESS\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_DATE + "2018-09-18 "
            + PREFIX_INDEX + "1 "
            + PREFIX_ADDRESS + "NUS";

    public static final String MESSAGE_EDIT_EVENT_ADDRESS_SUCCESS = "Edited %1$s with new address: %2$s";
    public static final String MESSAGE_NOT_EDITED = "The specified address is the same as the current value in the "
            + "address book.";

    private final EventDate targetDate;
    private final Index targetIndex;
    private final EventAddress newAddress;

    /**
     * @param targetDate of the event in the filtered event list to edit
     * @param targetIndex of the event in the filtered event list to edit
     * @param newAddress to update the event's address with
     */
    public EditEventAddressCommand(EventDate targetDate, Index targetIndex, EventAddress newAddress) {
        requireNonNull(targetDate);
        requireNonNull(targetIndex);
        requireNonNull(newAddress);

        this.targetDate = targetDate;
        this.targetIndex = targetIndex;
        this.newAddress = newAddress;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<List<Event>> lastShownList = model.getFilteredEventListByDate();
        List<Event> listToRemoveFrom = getTargetDateList(lastShownList);
        Event eventToEdit = getEventToDelete(listToRemoveFrom);

        Event editedEvent = createEditedEvent(eventToEdit, newAddress);

        /* It is ok for editedEvent to be the same event as eventToEdit, but
        * editedEvent should not be an existing event in the address book,
        * unless it is the same event as eventToEdit
        */
        assert (!(!eventToEdit.isSameEvent(editedEvent) && model.hasEvent(editedEvent)));

        model.updateEvent(eventToEdit, editedEvent);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_ADDRESS_SUCCESS, eventToEdit.getEventName(),
                editedEvent.getEventAddress()));
    }

    /**
     * Returns the {@code Event} object from {@code listToRemoveFrom} to be deleted, based on
     * {@code targetIndex}, if it exists
     * @throws CommandException if such an event based on {@code targetIndex} does not exist in
     * {@code listToRemoveFrom}
     */
    public Event getEventToDelete(List<Event> listToRemoveFrom) throws CommandException {
        if (targetIndex.getZeroBased() >= listToRemoveFrom.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        return listToRemoveFrom.get(targetIndex.getZeroBased());
    }

    /**
     * Returns the event list for the required date based on {@code targetDate}, if such a list exists for
     * the given {@code targetDate}
     * @throws CommandException if no such list exists for {@code targetDate}
     */
    public List<Event> getTargetDateList(List<List<Event>> lastShownList) throws CommandException {
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

        return targetDateList.get(0);
    }

    /**
     * Creates and returns an {@code Event} with the details of {@code eventToEdit}
     * edited with {@code updatedAddress}.
     */
    private static Event createEditedEvent(Event eventToEdit, EventAddress updatedAddress) {
        assert eventToEdit != null;
        assert updatedAddress != null;

        EventName eventName = eventToEdit.getEventName();
        EventDescription eventDescription = eventToEdit.getEventDescription();
        EventDate eventDate = eventToEdit.getEventDate();
        EventTime eventStartTime = eventToEdit.getEventStartTime();
        EventTime eventEndTime = eventToEdit.getEventEndTime();
        Set<Person> eventContacts = eventToEdit.getEventContacts();
        Set<Tag> eventTags = eventToEdit.getEventTags();

        return new Event(eventName, eventDescription, eventDate, eventStartTime, eventEndTime, updatedAddress,
                eventContacts, eventTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEventAddressCommand)) {
            return false;
        }

        // state check
        EditEventAddressCommand e = (EditEventAddressCommand) other;
        return targetDate.equals(e.targetDate)
                && targetIndex.equals(e.targetIndex)
                && newAddress.equals(e.newAddress);
    }
}

package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.scheduler.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.commons.util.CollectionUtil;
import seedu.scheduler.commons.util.EventFormatUtil;
import seedu.scheduler.commons.web.ConnectToGoogleCalendar;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.event.DateTime;
import seedu.scheduler.model.event.Description;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.EventName;
import seedu.scheduler.model.event.ReminderDurationList;
import seedu.scheduler.model.event.RepeatType;
import seedu.scheduler.model.event.Venue;
import seedu.scheduler.model.tag.Tag;

/**
 * Edits the details of an existing event in the scheduler.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS_ONE = "edi";
    public static final String COMMAND_ALIAS_TWO = "ed";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_EVENT_NAME + "NAME] "
            + "[" + PREFIX_EVENT_NAME + "EMAIL] "
            + "[" + PREFIX_EVENT_NAME + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENT_NAME + "johndoe@example.com";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final ConnectToGoogleCalendar connectToGoogleCalendar =
            new ConnectToGoogleCalendar();
    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index of the event in the filtered event list to edit
     * @param editEventDescriptor details to edit the event with
     */
    public EditCommand(Index index, EditEventDescriptor editEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);

        model.updateEvent(eventToEdit, editedEvent);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        model.commitScheduler();
        int totalInstance = EventFormatUtil.calculateTotalInstanceNumber(lastShownList, eventToEdit);
        int instanceIndex = EventFormatUtil.calculateInstanceIndex(lastShownList, eventToEdit);
        connectToGoogleCalendar.updateGoogleEvent(eventToEdit, editedEvent, instanceIndex, totalInstance);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, editedEvent));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;
        UUID eventUid = editEventDescriptor.getUid().orElse(eventToEdit.getUid());
        UUID eventUuid = editEventDescriptor.getUuid().orElse(eventToEdit.getUuid());
        EventName updatedEventName = editEventDescriptor.getEventName().orElse(eventToEdit.getEventName());
        DateTime updatedStartDateTime = editEventDescriptor.getStartDateTime().orElse(eventToEdit.getStartDateTime());
        DateTime updatedEndDateTime = editEventDescriptor.getEndDateTime().orElse(eventToEdit.getEndDateTime());
        Description updatedDescription = editEventDescriptor.getDescription().orElse(eventToEdit.getDescription());
        Venue updatedVenue = editEventDescriptor.getVenue().orElse(eventToEdit.getVenue());
        RepeatType updatedRepeatType = editEventDescriptor.getRepeatType().orElse(eventToEdit.getRepeatType());
        DateTime updatedRepeatUntilDateTime = editEventDescriptor.getRepeatUntilDateTime()
                .orElse(eventToEdit.getRepeatUntilDateTime());
        Set<Tag> updatedTags = editEventDescriptor.getTags().orElse(eventToEdit.getTags());
        ReminderDurationList updatedReminderDurationList =
                editEventDescriptor.getReminderDurationList().orElse(eventToEdit.getReminderDurationList());
        return new Event(eventUid, eventUuid, updatedEventName, updatedStartDateTime, updatedEndDateTime,
                updatedDescription, updatedVenue, updatedRepeatType, updatedRepeatUntilDateTime, updatedTags,
                updatedReminderDurationList);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editEventDescriptor.equals(e.editEventDescriptor);
    }

    /**
     * Stores the details to edit the event with. Each non-empty field value will replace the
     * corresponding field value of the event.
     */
    public static class EditEventDescriptor {
        private UUID uid;
        private UUID uuid;
        private EventName eventName;
        private DateTime startDateTime;
        private DateTime endDateTime;
        private Description description;
        private Venue venue;
        private RepeatType repeatType;
        private DateTime repeatUntilDateTime;
        private Set<Tag> tags;
        private ReminderDurationList reminderDurationList;

        public EditEventDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setUid(toCopy.uid);
            setUuid(toCopy.uuid);
            setEventName(toCopy.eventName);
            setStartDateTime(toCopy.startDateTime);
            setEndDateTime(toCopy.endDateTime);
            setDescription(toCopy.description);
            setVenue(toCopy.venue);
            setRepeatType(toCopy.repeatType);
            setRepeatUntilDateTime(toCopy.repeatUntilDateTime);
            setTags(toCopy.tags);
            setReminderDurationList(toCopy.reminderDurationList);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(eventName, startDateTime, endDateTime, description,
                    venue, repeatType, repeatUntilDateTime, tags, reminderDurationList);
        }

        public void setUid(UUID uid) {
            this.uid = uid;
        }

        public Optional<UUID> getUid() {
            return Optional.ofNullable(uid);
        }

        public void setUuid(UUID uuid) {
            this.uuid = uuid;
        }

        public Optional<UUID> getUuid() {
            return Optional.ofNullable(uuid);
        }

        public void setEventName(EventName eventName) {
            this.eventName = eventName;
        }

        public Optional<EventName> getEventName() {
            return Optional.ofNullable(eventName);
        }

        public void setStartDateTime(DateTime startDateTime) {
            this.startDateTime = startDateTime;
        }

        public Optional<DateTime> getStartDateTime() {
            return Optional.ofNullable(startDateTime);
        }

        public void setEndDateTime(DateTime endDateTime) {
            this.endDateTime = endDateTime;
        }

        public Optional<DateTime> getEndDateTime() {
            return Optional.ofNullable(endDateTime);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setVenue(Venue venue) {
            this.venue = venue;
        }

        public Optional<Venue> getVenue() {
            return Optional.ofNullable(venue);
        }

        public void setRepeatType(RepeatType repeatType) {
            this.repeatType = repeatType;
        }

        public Optional<RepeatType> getRepeatType() {
            return Optional.ofNullable(repeatType);
        }

        public void setRepeatUntilDateTime(DateTime repeatUntilDateTime) {
            this.repeatUntilDateTime = repeatUntilDateTime;
        }

        public Optional<DateTime> getRepeatUntilDateTime() {
            return Optional.ofNullable(repeatUntilDateTime);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        public void setReminderDurationList(ReminderDurationList reminderDurationList) {
            this.reminderDurationList = reminderDurationList;
        }

        public Optional<ReminderDurationList> getReminderDurationList() {
            return Optional.ofNullable(reminderDurationList);
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            // state check
            EditEventDescriptor e = (EditEventDescriptor) other;

            return getEventName().equals(e.getEventName())
                    && getStartDateTime().equals(e.getStartDateTime())
                    && getEndDateTime().equals(e.getEndDateTime())
                    && getDescription().equals(e.getDescription())
                    && getVenue().equals(e.getVenue())
                    && getRepeatType().equals(e.getRepeatType())
                    && getRepeatUntilDateTime().equals(e.getRepeatUntilDateTime())
                    && getTags().equals(e.getTags())
                    && getReminderDurationList().equals(e.getReminderDurationList());
        }
    }
}

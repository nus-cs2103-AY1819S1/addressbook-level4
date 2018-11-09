package seedu.scheduler.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.logic.parser.CliSyntax.FLAG_UPCOMING;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.scheduler.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.commons.util.CollectionUtil;
import seedu.scheduler.commons.util.EventFormatUtil;
import seedu.scheduler.commons.web.ConnectToGoogleCalendar;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.RepeatEventGenerator;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.logic.parser.Flag;
import seedu.scheduler.logic.parser.exceptions.ParseException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.event.DateTime;
import seedu.scheduler.model.event.Description;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.EventName;
import seedu.scheduler.model.event.ReminderDurationList;
import seedu.scheduler.model.event.RepeatType;
import seedu.scheduler.model.event.Venue;
import seedu.scheduler.model.tag.Tag;
import seedu.scheduler.ui.UiManager;

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
    public static final String MESSAGE_INTERNET_ERROR = "Only local changes,"
            + "no effects on your Google Calender.";
    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private final ConnectToGoogleCalendar connectToGoogleCalendar =
            new ConnectToGoogleCalendar();
    private final Index index;
    private final EditEventDescriptor editEventDescriptor;
    private final Flag[] flags;

    /**
     * @param index               of the event in the filtered event list to edit
     * @param editEventDescriptor details to edit the event with
     */
    public EditCommand(Index index, EditEventDescriptor editEventDescriptor, Flag... flags) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
        this.flags = flags;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        boolean googleCalendarIsEnabled = connectToGoogleCalendar.isGoogleCalendarEnabled();
        List<Event> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.info(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        //Set up event to be edited and edited event according to user input
        logger.info("Creating event to be edited.");
        Event eventToEdit = lastShownList.get(index.getZeroBased());

        //Calculate parameters for updating events in Google Calender
        logger.info("Calculating parameters for Google calender edit commands.");
        int instanceIndex = EventFormatUtil.calculateInstanceIndex(lastShownList, eventToEdit);
        boolean operationOnGoogleCalIsSuccessful;

        try {
            //Update by cases
            //Case1: edit single event
            logger.info("The EditCommand will be executed by cases.");
            if (flags.length == 0) {
                logger.info("Single event will be edited.");
                Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor);
                operationOnGoogleCalIsSuccessful = connectToGoogleCalendar.updateSingleGoogleEvent(
                        googleCalendarIsEnabled, eventToEdit, editedEvent, instanceIndex);
                model.updateEvent(eventToEdit, editedEvent);
            } else {
                //edit upcoming or all events in a EventSet
                logger.info("The upcoming events in a EventSet to be edited.");
                List<Event> editedEvents;
                int effectRangeStartingIndex;
                if (flags[0].equals(FLAG_UPCOMING)) {
                    //Case2: edit upcoming events
                    editedEvents = createEditedEvents(eventToEdit, eventToEdit, editEventDescriptor);
                    effectRangeStartingIndex = instanceIndex;
                    operationOnGoogleCalIsSuccessful =
                            connectToGoogleCalendar.updateRangeGoogleEvent(googleCalendarIsEnabled,
                                    eventToEdit, editedEvents, instanceIndex, effectRangeStartingIndex);
                    model.updateUpcomingEvents(eventToEdit, editedEvents);
                } else {
                    //Case3: edit all events
                    logger.info("All the events in a EventSet to be edited.");
                    Predicate<Event> firstInstancePredicate = getFirstInstancePredicate(eventToEdit,
                            editEventDescriptor);
                    Event firstEventToEdit = model.getFirstInstanceOfEvent(firstInstancePredicate);
                    editedEvents = createEditedEvents(eventToEdit, firstEventToEdit, editEventDescriptor);
                    effectRangeStartingIndex = 0;
                    operationOnGoogleCalIsSuccessful = connectToGoogleCalendar.updateRangeGoogleEvent(
                            googleCalendarIsEnabled, eventToEdit, editedEvents,
                            instanceIndex, effectRangeStartingIndex);
                    model.updateRepeatingEvents(eventToEdit, editedEvents);
                }
            }
        } catch (ParseException e) {
            throw new CommandException(e.getMessage());
        }
        logger.info("Local update Done. Commit to Scheduler.");
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        model.commitScheduler();

        if (operationOnGoogleCalIsSuccessful | connectToGoogleCalendar.isGoogleCalendarDisabled()) {
            return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, eventToEdit.getEventName()));
        } else {
            return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, eventToEdit.getEventName())
                    + "\n" + MESSAGE_INTERNET_ERROR);
        }
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit} edited with {@code
     * editEventDescriptor}.
     */
    private static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor)
            throws ParseException {
        assert eventToEdit != null;
        UUID eventUid = editEventDescriptor.getEventUid().orElse(eventToEdit.getEventUid());
        UUID eventUuid = editEventDescriptor.getEventSetUid().orElse(eventToEdit.getEventSetUid());
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

        if (!Event.isValidEventDateTime(updatedStartDateTime, updatedEndDateTime)) {
            throw new ParseException(Event.MESSAGE_START_END_DATETIME_CONSTRAINTS);
        }

        if (!Event.isValidEventDateTime(updatedEndDateTime, updatedRepeatUntilDateTime)) {
            throw new ParseException(Event.MESSAGE_END_REPEAT_UNTIL_DATETIME_CONSTRAINTS);
        }

        return new Event(eventUid, eventUuid, updatedEventName, updatedStartDateTime, updatedEndDateTime,
                updatedDescription, updatedVenue, updatedRepeatType, updatedRepeatUntilDateTime, updatedTags,
                updatedReminderDurationList);
    }

    /**
     * Creates and returns a {@code List<Event>} with repeated events generated with details from {@code
     * editEventDescriptor}.
     */
    private static List<Event> createEditedEvents(Event eventToEdit, Event firstEventToEdit,
                                                  EditEventDescriptor editEventDescriptor) throws ParseException {
        assert eventToEdit != null;
        assert firstEventToEdit != null;

        UUID eventUid = editEventDescriptor.getEventUid().orElse(eventToEdit.getEventUid());
        UUID eventUuid = editEventDescriptor.getEventSetUid().orElse(eventToEdit.getEventSetUid());
        EventName updatedEventName = editEventDescriptor.getEventName().orElse(eventToEdit.getEventName());
        DateTime updatedStartDateTime = new DateTime(firstEventToEdit.getStartDateTime().value
                .plus(Duration.between(eventToEdit.getStartDateTime().value,
                        editEventDescriptor.getStartDateTime().orElse(eventToEdit.getStartDateTime()).value)));
        DateTime updatedEndDateTime = new DateTime(firstEventToEdit.getEndDateTime().value
                .plus(Duration.between(eventToEdit.getEndDateTime().value,
                        editEventDescriptor.getEndDateTime().orElse(eventToEdit.getEndDateTime()).value)));
        Description updatedDescription = editEventDescriptor.getDescription().orElse(eventToEdit.getDescription());
        Venue updatedVenue = editEventDescriptor.getVenue().orElse(eventToEdit.getVenue());
        RepeatType updatedRepeatType = editEventDescriptor.getRepeatType().orElse(eventToEdit.getRepeatType());
        DateTime updatedRepeatUntilDateTime = editEventDescriptor.getRepeatUntilDateTime()
                .orElse(eventToEdit.getRepeatUntilDateTime());
        Set<Tag> updatedTags = editEventDescriptor.getTags().orElse(eventToEdit.getTags());
        ReminderDurationList updatedReminderDurationList =
                editEventDescriptor.getReminderDurationList().orElse(eventToEdit.getReminderDurationList());
        Event updatedEvent = new Event(eventUid, eventUuid, updatedEventName, updatedStartDateTime, updatedEndDateTime,
                updatedDescription, updatedVenue, updatedRepeatType, updatedRepeatUntilDateTime, updatedTags,
                updatedReminderDurationList);

        if (!Event.isValidEventDateTime(updatedStartDateTime, updatedEndDateTime)) {
            throw new ParseException(Event.MESSAGE_START_END_DATETIME_CONSTRAINTS);
        }

        if (!Event.isValidEventDateTime(updatedEndDateTime, updatedRepeatUntilDateTime)) {
            throw new ParseException(Event.MESSAGE_END_REPEAT_UNTIL_DATETIME_CONSTRAINTS);
        }

        return RepeatEventGenerator.getInstance().generateAllRepeatedEvents(updatedEvent);
    }

    /**
     * Creates a predicate which is use to get the first instance of {@code eventToEdit}. The criteria for first
     * instance of {@code eventToEdit} is found in the edited repeat type from {@code editEventDescriptor}.
     */
    private static Predicate<Event> getFirstInstancePredicate(Event eventToEdit,
                                                              EditEventDescriptor editEventDescriptor) {
        assert eventToEdit != null;

        if (!editEventDescriptor.getRepeatType().isPresent()) {
            return event -> event.getEventSetUid().equals(eventToEdit.getEventSetUid());
        }

        RepeatType updatedRepeatType = editEventDescriptor.getRepeatType().get();
        DateTime updatedStartDateTime = editEventDescriptor.getStartDateTime().orElse(eventToEdit.getStartDateTime());

        switch (updatedRepeatType) {
        case WEEKLY:
            return event -> event.getEventSetUid().equals(eventToEdit.getEventSetUid())
                    && event.getStartDateTime().value.getDayOfWeek() == updatedStartDateTime.value.getDayOfWeek();
        case MONTHLY:
            return event -> event.getEventSetUid().equals(eventToEdit.getEventSetUid())
                    && event.getStartDateTime().value.getDayOfMonth() == updatedStartDateTime.value.getDayOfMonth();
        case YEARLY:
            return event -> event.getEventSetUid().equals(eventToEdit.getEventSetUid())
                    && event.getStartDateTime().value.getDayOfYear() == updatedStartDateTime.value.getDayOfYear();
        default:
            return event -> event.getEventSetUid().equals(eventToEdit.getEventSetUid());
        }
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
     * Stores the details to edit the event with. Each non-empty field value will replace the corresponding field value
     * of the event.
     */
    public static class EditEventDescriptor {
        private UUID eventUid;
        private UUID eventSetUid;
        private EventName eventName;
        private DateTime startDateTime;
        private DateTime endDateTime;
        private Description description;
        private Venue venue;
        private RepeatType repeatType;
        private DateTime repeatUntilDateTime;
        private Set<Tag> tags;
        private ReminderDurationList reminderDurationList;

        public EditEventDescriptor() {
        }

        /**
         * Copy constructor. A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setEventUid(toCopy.eventUid);
            setEventSetUid(toCopy.eventSetUid);
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

        public void setEventUid(UUID eventUid) {
            this.eventUid = eventUid;
        }

        public Optional<UUID> getEventUid() {
            return Optional.ofNullable(eventUid);
        }

        public void setEventSetUid(UUID eventSetUid) {
            this.eventSetUid = eventSetUid;
        }

        public Optional<UUID> getEventSetUid() {
            return Optional.ofNullable(eventSetUid);
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
         * Sets {@code tags} to this object's {@code tags}. A defensive copy of {@code tags} is used internally.
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
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException} if modification is
         * attempted. Returns {@code Optional#empty()} if {@code tags} is null.
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

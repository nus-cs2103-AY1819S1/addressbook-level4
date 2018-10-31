package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

//@@author AyushChatto
/**
 * Schedules a meeting with a person.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";
    public static final String COMMAND_ALIAS = "sch";

    public static final String MESSAGE_SCHEDULING_SUCCESS = "Meeting added";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedules a value with the person at the given "
            + "index number. "
            + "Existing values will be overwritten by new values. \n"
            + "Parameters: INDEX (must be a positive integer)"
            + "[" + PREFIX_MEETING + "MEETING TIME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MEETING + "31/12/18 1630";
    public static final String MESSAGE_NO_PREFIX = "No prefix " + PREFIX_MEETING + " detected.";
    private final Index index;
    private final Meeting meeting;

    public ScheduleCommand(Index index, Meeting meeting) {
        requireNonNull(index);
        requireNonNull(meeting);
        this.index = index;
        this.meeting = meeting;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (model.hasMeeting(meeting)) {
            throw new CommandException(Messages.MESSAGE_CLASHING_MEETINGS);
        }

        Person personToSchedule = lastShownList.get(index.getZeroBased());

        if (!personToSchedule.getMeeting().value.equals(Meeting.NO_MEETING)) {
            model.deleteMeeting(personToSchedule.getMeeting());
        }

        if (!meeting.value.equals(Meeting.NO_MEETING)) {
            model.addMeeting(meeting);
        }

        Person scheduledPerson = createScheduledPerson(personToSchedule, meeting);

        model.updatePerson(personToSchedule, scheduledPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SCHEDULING_SUCCESS));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToSchedule}
     * updated with the value {@code value}.
     */
    private static Person createScheduledPerson(Person personToSchedule, Meeting meeting) {
        assert personToSchedule != null;

        Name name = personToSchedule.getName();
        Optional<Phone> phone = personToSchedule.getPhone();
        Optional<Email> email = personToSchedule.getEmail();
        Optional<Address> address = personToSchedule.getAddress();
        Set<Tag> tags = personToSchedule.getTags();
        Person scheduledPerson = new Person(name, phone, email, address, tags, meeting);
        scheduledPerson.setPicture(personToSchedule.getPicture());
        return scheduledPerson;
    }
}

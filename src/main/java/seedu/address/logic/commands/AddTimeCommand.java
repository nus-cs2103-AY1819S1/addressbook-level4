package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Education;
import seedu.address.model.person.Email;
import seedu.address.model.person.Grades;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Time;
import seedu.address.model.tag.Tag;

/**
 * Adds a time slot of a Person in the address book.
 */
public class AddTimeCommand extends Command {
    public static final String COMMAND_WORD = "addTime";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tutorial time slot of a person. "
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_TIME + "TIME "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TIME + "mon 1300 1500 ";

    public static final String MESSAGE_SUCCESS = "Time slot successfully added";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This person does not exists in the address book";
    public static final String MESSAGE_TIME_IS_NOT_AVAILABLE = "The time has already been taken";
    public static final String MESSAGE_INVALID_START_END_TIME = "Start time must be earlier than end time";
    public static final String MESSAGE_TIME_CLASH = "There is a clash with other tuition timing in the address book";

    private final Time toAdd;

    private final Index index;

    /**
     * Creates a AddTimeCommand to add the specified {@code Time}
     */
    public AddTimeCommand(Index index, Time time) {
        requireNonNull(index);
        requireNonNull(time);
        toAdd = time;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ArrayList<Time> allTimeSlot = new ArrayList();
        ArrayList<Time> sameDay = new ArrayList();

        for (Person ppl : model.getFilteredPersonList()) {
            allTimeSlot.addAll(ppl.getTime());
        }
        for (Time time : allTimeSlot) {
            if (time.getDay() == toAdd.getDay()) {
                sameDay.add(time);
            }
        }
        Person targetPerson = lastShownList.get(index.getZeroBased());

        if (allTimeSlot.contains(toAdd)) {
            throw new CommandException(MESSAGE_TIME_IS_NOT_AVAILABLE);
        }
        if (!model.hasPerson(targetPerson)) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }
        if (toAdd.getStartTime() >= toAdd.getEndTime()) {
            throw new CommandException(MESSAGE_INVALID_START_END_TIME);
        }
        if (toAdd.getStartTime() >= 2400) {
            throw new CommandException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        if (toAdd.getEndTime() >= 2400) {
            throw new CommandException(Time.MESSAGE_TIME_CONSTRAINTS);
        }

        for (Time time : sameDay) {
            if (toAdd.getStartTime() >= time.getStartTime() && toAdd.getStartTime() < time.getEndTime()) {
                throw new CommandException(MESSAGE_TIME_CLASH);
            }
            if (toAdd.getEndTime() > time.getStartTime() && toAdd.getEndTime() <= time.getEndTime()) {
                throw new CommandException(MESSAGE_TIME_CLASH);
            }
            if (toAdd.getStartTime() <= time.getStartTime() && toAdd.getEndTime() >= time.getEndTime()) {
                throw new CommandException(MESSAGE_TIME_CLASH);
            }
        }

        Person editedPerson = createEditedPerson(targetPerson);
        editedPerson.addTime(toAdd);

        model.updatePerson(targetPerson, editedPerson);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    /**
     * copy a person with a new timing ArrayList
     *
     * @param personToEdit origin person
     * @return
     */
    private static Person createEditedPerson(Person personToEdit) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();

        Address updatedAddress = personToEdit.getAddress();
        Education updatedEducation = personToEdit.getEducation();

        HashMap<String, Grades> updatedGrades = personToEdit.getGrades();
        ArrayList<Time> updatedTime = new ArrayList<>(personToEdit.getTime());
        Set<Tag> updatedTags = personToEdit.getTags();

        return new Person(updatedName, updatedPhone, updatedEmail,
                updatedAddress, updatedEducation, updatedGrades, updatedTime, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTimeCommand // instanceof handles nulls
                && toAdd.equals(((AddTimeCommand) other).toAdd))
                && index.equals(((AddTimeCommand) other).index);
    }
}

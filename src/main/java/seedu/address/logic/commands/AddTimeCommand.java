package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Time;

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

    public static final String MESSAGE_SUCCESS_ADDED = "Time slot successfully added";
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
        model.addTime(targetPerson, toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS_ADDED));
    }
}


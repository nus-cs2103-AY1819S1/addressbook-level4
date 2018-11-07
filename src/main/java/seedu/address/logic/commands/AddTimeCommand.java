package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Time;

/**
 * Adds a time slot of a Person in the address book.
 */
public class AddTimeCommand extends Command {
    public static final String COMMAND_WORD = "addTime";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tutorial time slot of a person. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TIME + "TIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TIME + "mon 1300 1500 ";

    public static final String MESSAGE_SUCCESS_ADDED = "Time slot successfully added";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This person does not exists in the student list";
    public static final String MESSAGE_TIME_IS_NOT_AVAILABLE = "The time has already been taken";
    public static final String MESSAGE_INVALID_START_END_TIME = "Start time must be earlier than end time";
    public static final String MESSAGE_TIME_CLASH = "There is a clash with other tuition timing in the student list";

    private String[] personToFind;

    private final Time toAdd;
    private final String toFind;

    /**
     * Creates a AddTimeCommand to add the specified {@code Time}
     */
    public AddTimeCommand(String personName, Time time) {
        requireNonNull(time);
        toAdd = time;
        toFind = personName;
        personToFind = personName.split("\\s+");
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

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
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(personToFind)));

        Person targetPerson = model.getFilteredPersonList().get(0);

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
        model.addTime(toFind, toAdd);
        //model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS); // NEW LINE
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS_ADDED));
    }
}


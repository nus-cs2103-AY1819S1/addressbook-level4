package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddTimeCommand.MESSAGE_PERSON_NOT_FOUND;
import static seedu.address.logic.commands.AddTimeCommand.MESSAGE_TIME_IS_NOT_AVAILABLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Time;

/**
 * Edits the time of an existing person in the address book.
 */
public class EditTimeCommand extends Command {

    public static final String COMMAND_WORD = "editTime";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit a tutorial time slot of a person. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TIME + "NEWTIME "
            + PREFIX_TIME + "OLDTIME ";

    public static final String MANAGE_SUCCESS = "edit successfully";

    private String name;
    private Time oldTime;
    private Time newTime;

    /**
     * @param oldTime of the person in the filtered person list to edit
     * @param newTime to cover
     */
    public EditTimeCommand(String name, Time oldTime, Time newTime) {
        requireNonNull(name);
        requireNonNull(oldTime);
        requireNonNull(newTime);

        this.name = name;
        this.oldTime = oldTime;
        this.newTime = newTime;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        ArrayList<Time> allTimeSlot = new ArrayList();

        for (Person ppl : model.getFilteredPersonList()) {
            allTimeSlot.addAll(ppl.getTime());
        }

        ArrayList<String> pplList = new ArrayList<>();
        pplList.add(name);

        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(pplList));

        Person targetPerson = model.getFilteredPersonList().get(0);

        if (allTimeSlot.contains(newTime)) {
            throw new CommandException(MESSAGE_TIME_IS_NOT_AVAILABLE);
        }
        if (!model.hasPerson(targetPerson)) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }
        model.deleteTime(targetPerson, oldTime);
        model.addTime(targetPerson, newTime);
        return new CommandResult(String.format(MANAGE_SUCCESS));

    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Time;

/**
 * Exchange Time.
 */
public class ChangeTimeCommand extends Command {
    public static final String COMMAND_WORD = "ChangeTime";
    public static final String MESSAGE_SUCCESS = "Already changed the time between the given students with given time!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": ChangeTime. "
            + "StudentA: "
            + "The Ordinal number of the wanted time//base 0"
            + "StudentB: "
            + "The Ordinal number of the wanted time//base 0";

    private String nameA;
    private String nameB;
    private int numA;
    private int numB;

    /**
     * Change timeslot command
     *
     * @param args
     */
    public ChangeTimeCommand (String args) {
        String[] stringCommand = args.trim().split(" ");
        this.nameA = stringCommand[0];
        this.nameB = stringCommand[1];
        this.numA = Integer.parseInt(stringCommand[2]);
        this.numB = Integer.parseInt(stringCommand[3]);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {


        ArrayList<String> pplList = new ArrayList<>();
        pplList.add(nameA);
        pplList.add(nameB);

        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(pplList));

        Person targetPersonA = model.getFilteredPersonList().get(0);
        Person targetPersonB = model.getFilteredPersonList().get(1);

        // Execute the display of student's grades here
        requireNonNull(model);

        if (numA >= targetPersonA.getTime().size() || numA <= 0 ) {
            return new CommandResult("Cannot find the wanted timeSlot, please enter valid timeSlot");
        }

        if (numB >= targetPersonB.getTime().size() || numB <= 0 ) {
            return new CommandResult("Cannot find the wanted timeSlot, please enter valid timeSlot");
        }

        Time timeA = (Time) targetPersonA.getTime().get(numA);
        Time timeB = (Time) targetPersonB.getTime().get(numA);

        targetPersonA.getTime().remove(timeA);
        targetPersonB.getTime().remove(timeB);
        targetPersonA.addTime(timeB);
        targetPersonB.addTime(timeA);


        return new CommandResult("the time slot changed successfully");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeTimeCommand
                && nameA.equals(((ChangeTimeCommand) other).nameA)
                && nameB.equals(((ChangeTimeCommand) other).nameB)
                && numA == ((ChangeTimeCommand) other).numA
                && numB == ((ChangeTimeCommand) other).numB);
    }
}



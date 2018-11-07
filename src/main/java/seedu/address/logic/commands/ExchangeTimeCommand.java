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
public class ExchangeTimeCommand extends Command {
    public static final String COMMAND_WORD = "ExchangeTime";
    public static final String MESSAGE_SUCCESS = "Already exchanged the time"
            + " between the given students with given time!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": ExchangeTime. "
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
    public ExchangeTimeCommand(String args) {
        String[] stringCommand = args.trim().split(" ");
        if (stringCommand.length != 4) {
            this.nameA = "invalid";
        } else {
            this.nameA = stringCommand[0];
            this.numA = Integer.parseInt(stringCommand[1]);
            this.nameB = stringCommand[2];
            this.numB = Integer.parseInt(stringCommand[3]);
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if ("invalid".equals(nameA)) {
            return new CommandResult("Cannot find the student or the input is not complete,"
                    + " please enter valid name");
        }
        ArrayList<String> pplList = new ArrayList<>();
        pplList.add(nameA);
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(pplList));
        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult("Cannot find the student or the input is not complete,"
                    + " please enter valid name");
        }
        Person targetPersonA = model.getFilteredPersonList().get(0);
        ArrayList<String> pplList2 = new ArrayList<>();
        pplList2.add(nameB);
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(pplList2));
        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult("Cannot find the student or the input is not complete,"
                    + " please enter valid name");
        }
        Person targetPersonB = model.getFilteredPersonList().get(0);

        if (!targetPersonA.getEducation().equals(targetPersonB.getEducation())) {
            return new CommandResult("The students are not "
                    + "in the same grade of the same education level");
        }
        // Execute the display of student's grades here
        requireNonNull(model);

        if (numA >= targetPersonA.getTime().size() || numA <= 0) {
            return new CommandResult("Cannot find the wanted timeSlot, please enter valid timeSlot");
        }

        if (numB >= targetPersonB.getTime().size() || numB <= 0) {
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
                || (other instanceof ExchangeTimeCommand
                && nameA.equals(((ExchangeTimeCommand) other).nameA)
                && nameB.equals(((ExchangeTimeCommand) other).nameB)
                && numA == ((ExchangeTimeCommand) other).numA
                && numB == ((ExchangeTimeCommand) other).numB);
    }
}



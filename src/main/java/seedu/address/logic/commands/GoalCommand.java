package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.capgoal.CapGoal;

//@@author jeremiah-ang
/**
 * Sets CAP Goal
 */
public class GoalCommand extends Command {

    public static final String COMMAND_WORD = "goal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set your CAP goal. "
            + "Parameters: "
            + "CAP_GOAL "
            + "\nExample: " + COMMAND_WORD + " "
            + "4.5";

    public static final String MESSAGE_SUCCESS = "Your CAP Goal: %1$s";

    private final double goal;

    /**
     * Creates an GoalCommand to set the CAP Goal
     */
    public GoalCommand(double goal) {
        this.goal = goal;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        model.updateCapGoal(goal);
        CapGoal capGoal = model.getCapGoal();
        model.commitTranscript();
        return new CommandResult(String.format(MESSAGE_SUCCESS, capGoal.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GoalCommand // instanceof handles nulls
                && goal == ((GoalCommand) other).goal); // state check
    }
}

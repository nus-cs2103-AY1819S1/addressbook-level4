package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_HPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TWEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CWEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.healthplan.HealthPlan;

public class AddHealthplanCommand extends Command {

    public static final String COMMAND_WORD = "addHP";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a healthplan. "
            + "Parameters: "
            + PREFIX_HPNAME + "NAME "
            + PREFIX_TWEIGHT + "TARGET WEIGHT(KG) "
            + PREFIX_CWEIGHT + "CURRENT WEIGHT(KG) "
            + PREFIX_CHEIGHT + "CURRENT HEIGHT(CM) "
            + PREFIX_AGE + "AGE "
            + PREFIX_DURATION + "DURATION(DAYS) "
            + PREFIX_SCHEME + "SCHEME (LOSS/GAIN/MAINTAIN) "

            + "\n"

            + "Example: " + COMMAND_WORD + " "
            + PREFIX_HPNAME + "Get fit "
            + PREFIX_TWEIGHT + "80 "
            + PREFIX_CWEIGHT + "70 "
            + PREFIX_CHEIGHT + "179 "
            + PREFIX_AGE + "26 "
            + PREFIX_DURATION + "15 "
            + PREFIX_SCHEME + "LOSS ";

    public static final String MESSAGE_SUCCESS = "New healthplan added: %1$s";
    public static final String MESSAGE_DUPLICATE_PLAN = "This healthplan already exists";

    private final HealthPlan toAdd;

    public AddHealthplanCommand(HealthPlan plan) {
        requireNonNull(plan);
        toAdd = plan;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasPlan(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PLAN);
        }

        model.addPlan(toAdd);
        model.commitAppContent();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddHealthplanCommand // instanceof handles nulls
                && toAdd.equals(((AddHealthplanCommand) other).toAdd));
    }



}

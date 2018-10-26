package seedu.souschef.logic.commands;

import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_PLAN;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.logic.CommandHistory;
import seedu.souschef.logic.parser.ArgumentMultimap;
import seedu.souschef.logic.parser.ArgumentTokenizer;
import seedu.souschef.logic.parser.Prefix;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.planner.Meal;

/**
 * command to add indexed day into index healthplan
 */
public class AddMealHealthPlanCommand <T extends UniqueType> extends Command {



    public static final String COMMAND_WORD = "addDay";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the day identified by the index number used in the "
            + "displayed meal plan list to the specified health plan\n"
            + "Parameters: p/PLANINDEX d/DAYINDEX(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " p/1 d/1";

    public static final String MESSAGE_SUCCESS = "added day %2$s to plan %1$s";

    private static final Logger logger = LogsCenter.getLogger(AddMealHealthPlanCommand.class);

    private HealthPlan planToAddTo;
    private Day dayToAdd;
    private Model<HealthPlan> model;

    private ArgumentMultimap argMultimap;



    public AddMealHealthPlanCommand(Model healthPlanModel, Model mealPlanModel, String args) throws ParseException {
        this.model = healthPlanModel;

        argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PLAN, PREFIX_DURATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_PLAN, PREFIX_DURATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }


        planToAddTo = healthPlanModel.getAppContent().getObservableHealthPlanList()
                .get(Integer.parseInt(argMultimap.getValue(PREFIX_PLAN).get().trim()) - 1);

        logger.info(planToAddTo.getHealthPlanName().planName);


        dayToAdd = mealPlanModel.getAppContent().getObservableMealPlanner()
                .get(Integer.parseInt(argMultimap.getValue(PREFIX_DURATION).get().trim()) - 1);

        logger.info(dayToAdd.getMeal(Meal.Slot.BREAKFAST).toString());
    }

    @Override
    public CommandResult execute(CommandHistory history) {


        planToAddTo.getMealPlans().add(dayToAdd);

        model.update(planToAddTo, planToAddTo);

        model.commitAppContent();
        return new CommandResult(String.format(MESSAGE_SUCCESS, argMultimap.getValue(PREFIX_PLAN).get().trim(),
                argMultimap.getValue(PREFIX_DURATION).get().trim()));
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }



}

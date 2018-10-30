package seedu.souschef.logic.parser.commandparser;

import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_PLAN;

import java.util.stream.Stream;

import seedu.souschef.logic.commands.AddMealHealthPlanCommand;
import seedu.souschef.logic.parser.ArgumentMultimap;
import seedu.souschef.logic.parser.ArgumentTokenizer;
import seedu.souschef.logic.parser.Prefix;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.planner.Day;

/**
 *  parser class for adding days into health plan
 */
public class AddMealHealthPlanCommandParser {

    private ArgumentMultimap argMultimap;
    private HealthPlan planToAddTo;
    private Day dayToAdd;
    private Model<HealthPlan> model;
    private int dayIndex;
    private int planIndex;

    /**
     * parser call for healthplans in healthPlan Parser
     */
    public AddMealHealthPlanCommand parseHealthPlan(Model healthPlanModel, Model mealPlanModel, String args)
            throws ParseException {

        this.model = healthPlanModel;

        argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PLAN, PREFIX_DURATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_PLAN, PREFIX_DURATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddMealHealthPlanCommand.MESSAGE_USAGE));
        }

        planToAddTo = healthPlanModel.getAppContent().getObservableHealthPlanList()
                .get(Integer.parseInt(argMultimap.getValue(PREFIX_PLAN).get().trim()) - 1);

        this.planIndex = Integer.parseInt(argMultimap.getValue(PREFIX_PLAN).get().trim());

        this.dayIndex = Integer.parseInt(argMultimap.getValue(PREFIX_DURATION).get().trim());

        dayToAdd = mealPlanModel.getAppContent().getObservableMealPlanner()
                .get(Integer.parseInt(argMultimap.getValue(PREFIX_DURATION).get().trim()) - 1);

        return new AddMealHealthPlanCommand(healthPlanModel, planToAddTo, dayToAdd, dayIndex, planIndex);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

package seedu.souschef.logic.parser.commandparser;

import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_PLAN;

import java.util.stream.Stream;

import seedu.souschef.logic.commands.DeleteMealHealthPlanCommand;
import seedu.souschef.logic.parser.ArgumentMultimap;
import seedu.souschef.logic.parser.ArgumentTokenizer;
import seedu.souschef.logic.parser.Prefix;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.healthplan.HealthPlan;


/**
 *  command parser for deleting days from healthplans
 */
public class DeleteMealHealthPlanCommandParser {

    private ArgumentMultimap argMultimap;
    private HealthPlan plan;
    private Model<HealthPlan> model;
    private int dayIndex;
    private int planIndex;


    /**
     *  parser call for healthplans in healthPlan Parser
     */
    public DeleteMealHealthPlanCommand parseHealthPlan(Model healthPlanModel, String args)
            throws ParseException {

        this.model = healthPlanModel;

        argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PLAN, PREFIX_DURATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_PLAN, PREFIX_DURATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteMealHealthPlanCommand.MESSAGE_USAGE));
        }

        this.planIndex = Integer.parseInt(argMultimap.getValue(PREFIX_PLAN).get().trim());

        plan = healthPlanModel.getAppContent().getObservableHealthPlanList().get(planIndex - 1);

        this.dayIndex = Integer.parseInt(argMultimap.getValue(PREFIX_DURATION).get().trim());

        return new DeleteMealHealthPlanCommand(model, plan, planIndex, dayIndex);


    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

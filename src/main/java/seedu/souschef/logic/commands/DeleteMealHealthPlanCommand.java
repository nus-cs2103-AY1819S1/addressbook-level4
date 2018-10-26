package seedu.souschef.logic.commands;

import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_PLAN;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.souschef.commons.core.EventsCenter;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.ui.BrowserUiChangedEvent;
import seedu.souschef.logic.CommandHistory;
import seedu.souschef.logic.parser.ArgumentMultimap;
import seedu.souschef.logic.parser.ArgumentTokenizer;
import seedu.souschef.logic.parser.Prefix;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;
import seedu.souschef.model.healthplan.HealthPlan;

/**
 * command extension to delete meal plans from health plan
 */
public class DeleteMealHealthPlanCommand <T extends UniqueType> extends Command {

    public static final String COMMAND_WORD = "deleteDay";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the day identified by the index number used in the "
            + "displayed meal plan list to the specified health plan\n"
            + "Parameters: p/PLANINDEX d/DAYINDEX(must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " p/1 d/1";

    public static final String MESSAGE_SUCCESS = "removed day %2$s from plan %1$s";

    private static final Logger logger = LogsCenter.getLogger(AddMealHealthPlanCommand.class);

    private HealthPlan plan;

    private Model<HealthPlan> model;

    private ArgumentMultimap argMultimap;

    private int indexToRemove;

    private int index;

    public DeleteMealHealthPlanCommand(Model healthPlanModel, String args) throws ParseException {
        this.model = healthPlanModel;


        argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PLAN, PREFIX_DURATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_PLAN, PREFIX_DURATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        this.index = Integer.parseInt(argMultimap.getValue(PREFIX_PLAN).get().trim());

        plan = healthPlanModel.getAppContent().getObservableHealthPlanList()
                .get(Integer.parseInt(argMultimap.getValue(PREFIX_PLAN).get().trim()) - 1);

        this.indexToRemove = Integer.parseInt(argMultimap.getValue(PREFIX_DURATION).get().trim()) - 1;

    }

    @Override
    public CommandResult execute(CommandHistory history) {

        plan.getMealPlans().remove(indexToRemove);
        model.update(plan, plan);
        model.commitAppContent();

        EventsCenter.getInstance().post(new BrowserUiChangedEvent("healthplanDetails", this.index));

        return new CommandResult(String.format(MESSAGE_SUCCESS, argMultimap.getValue(PREFIX_PLAN).get().trim(),
                argMultimap.getValue(PREFIX_DURATION).get().trim()));
    }



    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}

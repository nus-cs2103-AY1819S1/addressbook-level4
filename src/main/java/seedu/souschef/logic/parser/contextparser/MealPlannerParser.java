package seedu.souschef.logic.parser.contextparser;

import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.souschef.logic.commands.ClearMealPlannerCommand;
import seedu.souschef.logic.commands.Command;
import seedu.souschef.logic.commands.DisplayMealPlannerCommand;
import seedu.souschef.logic.commands.HelpCommand;
import seedu.souschef.logic.commands.PlanMealCommand;
import seedu.souschef.logic.parser.commandparser.PlanMealCommandParser;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.Model;
import seedu.souschef.ui.Ui;


/**
 * Parses user input.
 */
public class MealPlannerParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses userInput into command for execution.
     *
     * @param mealPlannerModel
     * @param recipeModel
     * @param userInput
     * @return
     * @throws ParseException
     */
    public Command parseCommand(Model mealPlannerModel, Model recipeModel,
            String userInput, Ui ui) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments").trim();

        switch(commandWord) {
        case PlanMealCommand.COMMAND_WORD:
            return new PlanMealCommandParser().parsePlan(mealPlannerModel, recipeModel, arguments);
        case DisplayMealPlannerCommand.COMMAND_WORD:
            return new DisplayMealPlannerCommand(ui);
        case ClearMealPlannerCommand.COMMAND_WORD:
            return new ClearMealPlannerCommand(mealPlannerModel);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

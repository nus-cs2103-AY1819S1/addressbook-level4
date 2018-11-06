package seedu.souschef.commons.core;

import static seedu.souschef.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_CHEIGHT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_COOKTIME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_CWEIGHT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_HPNAME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_INSTRUCTION;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_SCHEME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_STEP;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_TWEIGHT;

import seedu.souschef.logic.commands.AddCommand;
import seedu.souschef.logic.commands.BuildRecipeInstructionCommand;
import seedu.souschef.logic.commands.DeleteCommand;
import seedu.souschef.logic.commands.EditCommand;
import seedu.souschef.logic.commands.FindCommand;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX = "The recipe index provided is invalid";
    public static final String MESSAGE_INVALID_EMPTY_PLAN_INDEX = "Please enter an index!";
    public static final String MESSAGE_INVALID_PLAN_INDEX = "The plan index provided must be a non-decimal number";
    public static final String MESSAGE_INVALID_DAY_INDEX = "The day index provided must be a non-decimal number";
    public static final String MESSAGE_PLAN_INDEX_OUT_OF_RANGE ="The plan index provided is invalid";
    public static final String MESSAGE_DAY_INDEX_OUT_OF_RANGE ="The day index provided is invalid";
    public static final String MESSAGE_INVALID_INSTRUCTION_INDEX = "The instruction index provided is invalid";
    public static final String MESSAGE_LISTED_OVERVIEW = "%1$d %2$ss listed!";
    public static final String MESSAGE_DUPLICATE = "This %1$s already exists.";
    public static final String MESSAGE_NO_ELEMENT = "There is no matching result";

    // Add command messages
    public static final String MESSAGE_ADD_RECIPE_USAGE = AddCommand.COMMAND_WORD
            + ": Adds a recipe to the sous chef. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DIFFICULTY + "DIFFICULTY "
            + PREFIX_COOKTIME + "TIME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + AddCommand.COMMAND_WORD + " "
            + PREFIX_NAME + "Chicken Rice "
            + PREFIX_DIFFICULTY + "5 "
            + PREFIX_COOKTIME + "PT1H20M "
            + PREFIX_TAG + "asian "
            + PREFIX_TAG + "chinese";

    public static final String MESSAGE_CONT_RECIPE_USAGE = BuildRecipeInstructionCommand.COMMAND_WORD
            + ": Adds a recipe's instruction to the sous chef. "
            + "Parameters: "
            + PREFIX_INSTRUCTION + "INSTRUCTION "
            + "[" + PREFIX_INGREDIENT + "INGREDIENT AMOUNT UNIT]... "
            + "[" + PREFIX_COOKTIME + "TIME]\n"
            + "Example: " + BuildRecipeInstructionCommand.COMMAND_WORD + " "
            + PREFIX_INSTRUCTION + "Pour "
            + PREFIX_INGREDIENT + "chicken stock 300 ml "
            + "into pot and heat it up."
            + PREFIX_COOKTIME + "PT10M";

    public static final String MESSAGE_ADD_INGREDIENT_USAGE = AddCommand.COMMAND_WORD + ": Adds a ingredient. "
            + "Parameters: "
            + "NAME AMOUNT SERVING_UNIT DATE(MM-dd-yyyy)\n"
            + "Example: " + AddCommand.COMMAND_WORD + " "
            + "onion 100 gram 11-02-2018";

    public static final String MESSAGE_ADD_HEALTHPLAN_USAGE = AddCommand.COMMAND_WORD + ": Adds a healthplan. "
            + "Parameters: "
            + PREFIX_HPNAME + "HealthPlan Name "
            + PREFIX_AGE + "Age "
            + PREFIX_CHEIGHT + "Current Height(CM) "
            + PREFIX_CWEIGHT + "Current Weight(KG) "
            + PREFIX_TWEIGHT + "Target Weight(KG) "
            + PREFIX_DURATION + "Duration(Days) \n"
            + "Example: " + AddCommand.COMMAND_WORD + " "
            + PREFIX_HPNAME + "SLIM DOWN "
            + PREFIX_AGE + "25 "
            + PREFIX_CHEIGHT + "170 "
            + PREFIX_CWEIGHT + "70.0 "
            + PREFIX_TWEIGHT + "60.0 "
            + PREFIX_DURATION + "10 ";

    public static final String MESSAGE_ADD_FAVOURITE_USAGE = AddCommand.COMMAND_WORD
            + ": Adds a recipe to your favourite. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: favourite 1";

    // Delete command messages
    public static final String MESSAGE_DELETE_RECIPE_USAGE = DeleteCommand.COMMAND_WORD
            + ": Deletes the recipe identified by the index number used in the displayed recipe list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + DeleteCommand.COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_INGREDIENT_USAGE = DeleteCommand.COMMAND_WORD
            + ": Deletes the recipe identified by the name"
            + "Parameters: INDEX\n"
            + "Example: " + DeleteCommand.COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_HEALTHPLAN_USAGE = DeleteCommand.COMMAND_WORD
            + ": Deletes the health plan identified by the index number used in the displayed health plan list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + DeleteCommand.COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_FAVOURITE_USAGE = DeleteCommand.COMMAND_WORD
            + ": Deletes a recipe from your favourite. "
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + DeleteCommand.COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MEALPLANNER_USAGE = DeleteCommand.COMMAND_WORD
        + ": Deletes a day from your Meal Planner. "
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + DeleteCommand.COMMAND_WORD + " 1";

    // Edit command messages
    public static final String MESSAGE_EDIT_RECIPE_USAGE = EditCommand.COMMAND_WORD
            + ": Edits the details of the recipe identified "
            + "by the index number used in the displayed recipe list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DIFFICULTY + "DIFFICULTY] "
            + "[" + PREFIX_COOKTIME + "TIME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + EditCommand.COMMAND_WORD + " 1 "
            + PREFIX_DIFFICULTY + "3 "
            + PREFIX_COOKTIME + "PT1H20M\n"
            + "Alternative parameters: INDEX (must be a positive index) "
            + PREFIX_STEP + "STEP "
            + PREFIX_INSTRUCTION + "INSTRUCTION "
            + "[" + PREFIX_COOKTIME + "TIME]";

    public static final String MESSAGE_EDIT_INGREDIENT_USAGE = EditCommand.COMMAND_WORD
            + ": Edits the details of the ingredient identified "
            + "Parameters: INDEX FIELD_NAME NEW_INFO [MORE FIELD_NAME NEW_INFO]...\n"
            + "Example: " + EditCommand.COMMAND_WORD + " 1 name onion amount 200";

    public static final String MESSAGE_EDIT_HEALTHPLAN_USAGE = EditCommand.COMMAND_WORD
            + ": Edits the details of the health plan identified "
            + "by the index number used in the displayed health plan list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_HPNAME + "NAME] "
            + "[" + PREFIX_AGE + "AGE] "
            + "[" + PREFIX_CWEIGHT + "CURRENT WEIGHT(KG)] "
            + "[" + PREFIX_CHEIGHT + "CURRENT HEIGHT(CM)] "
            + "[" + PREFIX_TWEIGHT + "TARGET WEIGHT(KG)] "
            + "[" + PREFIX_DURATION + "DURATION(DAYS)] \n"
            + "Example: " + EditCommand.COMMAND_WORD + " 1 "
            + PREFIX_HPNAME + "Lose weight "
            + PREFIX_AGE + "23";

    // Find command messages
    public static final String MESSAGE_FIND_RECIPE_USAGE = FindCommand.COMMAND_WORD
            + ": Finds all recipes whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + FindCommand.COMMAND_WORD + " chicken asian western";

    public static final String MESSAGE_FIND_INGREDIENT_USAGE = FindCommand.COMMAND_WORD
            + ": Finds all ingredients whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + FindCommand.COMMAND_WORD + " onion";

    public static final String MESSAGE_FIND_FAVOURITES_USAGE = FindCommand.COMMAND_WORD;
}

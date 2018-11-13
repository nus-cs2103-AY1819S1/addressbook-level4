package seedu.souschef.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.souschef.commons.core.ComponentManager;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.logic.commands.Command;
import seedu.souschef.logic.commands.CommandResult;
import seedu.souschef.logic.commands.exceptions.CommandException;
import seedu.souschef.logic.parser.AppContentParser;
import seedu.souschef.logic.parser.exceptions.ParseException;
import seedu.souschef.model.ModelSet;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.recipe.CrossRecipe;
import seedu.souschef.model.recipe.Recipe;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final ModelSet modelSet;
    private final History history;
    private final AppContentParser appContentParser;

    public LogicManager(ModelSet modelSet) {
        this.modelSet = modelSet;
        history = new History();
        appContentParser = new AppContentParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = appContentParser.parseCommand(modelSet, commandText, history);
            return command.execute(history);
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Recipe> getFilteredRecipeList() {
        return modelSet.getRecipeModel().getFilteredList();
    }

    @Override
    public ObservableList<Ingredient> getFilteredIngredientList() {
        return modelSet.getIngredientModel().getFilteredList();
    }

    @Override
    public ObservableList<CrossRecipe> getFilteredCrossRecipeList() {
        return modelSet.getCrossRecipeModel().getFilteredList();
    }

    @Override
    public ObservableList<HealthPlan> getFilteredHealthPlanList() {
        return modelSet.getHealthPlanModel().getFilteredList();
    }

    @Override
    public ObservableList<Day> getMealPlanList() {
        return modelSet.getMealPlannerModel().getFilteredList();
    }

    @Override
    public ObservableList<Recipe> getFilteredFavouritesList() {
        return modelSet.getFavouriteModel().getFilteredList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}

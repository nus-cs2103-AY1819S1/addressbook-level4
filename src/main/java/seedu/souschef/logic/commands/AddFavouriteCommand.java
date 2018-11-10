package seedu.souschef.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.model.Model.PREDICATE_SHOW_ALL;

import java.util.logging.Logger;

import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.logic.History;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;
import seedu.souschef.model.favourite.Favourites;
import seedu.souschef.model.recipe.Recipe;

/**
 * Adds a recipe to favourite.
 */

public class AddFavouriteCommand <T extends UniqueType> extends Command {

    public static final String COMMAND_WORD = "favourite";
    public static final String MESSAGE_SUCCESS = "Added to favourite";
    private static final Logger logger = LogsCenter.getLogger(AddFavouriteCommand.class);

    private final Model model;

    private final String argument;

    public AddFavouriteCommand(Model model, String argument) {
        this.model = model;
        this.argument = argument;
    }

    @Override
    public CommandResult execute(History history) {
        requireNonNull(model);
        Recipe recipe = model.getAppContent().getObservableRecipeList().get(Integer.parseInt(argument.trim()) - 1);
        logger.info(recipe.getName().fullName);
        Favourites favourite = new Favourites(recipe.getName(),
                recipe.getDifficulty(), recipe.getCookTime(), recipe.getTags());

        model.add(favourite);
        model.updateFilteredList(PREDICATE_SHOW_ALL);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                history.getKeyword()));
    }
}

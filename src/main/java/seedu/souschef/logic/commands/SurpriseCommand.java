package seedu.souschef.logic.commands;

<<<<<<< HEAD
import seedu.souschef.logic.History;
=======
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Random;

import seedu.souschef.commons.core.EventsCenter;
import seedu.souschef.commons.core.Messages;
import seedu.souschef.commons.core.index.Index;
import seedu.souschef.commons.events.ui.JumpToListRequestEvent;
import seedu.souschef.logic.History;
import seedu.souschef.logic.commands.exceptions.CommandException;
import seedu.souschef.model.Model;
import seedu.souschef.model.recipe.Recipe;
>>>>>>> a0774f84ee9a4d04513be5834cde4b26f1276a17

/**
 * Randomly displays a recipe for the user.
 */

public class SurpriseCommand extends Command {

    public static final String COMMAND_WORD = "surprise";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": SousChef will surprise you with a random recipe.\n"
            + "Example: " + COMMAND_WORD;

<<<<<<< HEAD
    public static final String SHOWING_SURPRISE_MESSAGE = "SURPRISE!";
=======
    public static final String MESSAGE_SURPRISE_SUCCESS = "SURPRISE!";

    private final Model model;
    private Index randomIndex;
    private Random rand = new Random();

    public SurpriseCommand(Model model) {
        this.model = model;
    }

    @Override
    public CommandResult execute (History history) throws CommandException {
        requireNonNull(model);

        List<Recipe> filteredRecipeList = model.getFilteredList();

        setRandomIndex();

        if (filteredRecipeList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_EMPTY_RECIPE_LIST);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent((randomIndex)));
        return new CommandResult(String.format(MESSAGE_SURPRISE_SUCCESS, randomIndex.getOneBased()));
    }

    public void setRandomIndex() {
        List<Recipe> filteredRecipeList = model.getFilteredList();
        int random = rand.nextInt(filteredRecipeList.size() + 1);
        this.randomIndex = Index.fromZeroBased(random);

    }
>>>>>>> a0774f84ee9a4d04513be5834cde4b26f1276a17

    @Override
    public CommandResult execute(History history) {
        return new CommandResult(SHOWING_SURPRISE_MESSAGE);
    }
}

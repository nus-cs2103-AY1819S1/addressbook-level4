package seedu.souschef.logic.commands;

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

/**
 * Randomly displays a recipe for the user.
 */

public class SurpriseCommand extends Command {

    public static final String COMMAND_WORD = "surprise";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": SousChef will surprise you with a random recipe.\n"
            + "Example: " + COMMAND_WORD;

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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SurpriseCommand // instanceof handles nulls
                && randomIndex.equals(((SurpriseCommand) other).randomIndex)); // state check
    }
}

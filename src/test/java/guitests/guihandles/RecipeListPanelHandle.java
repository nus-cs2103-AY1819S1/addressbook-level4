package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.recipe.Recipe;

/**
 * Provides a handle for {@code RecipeListPanel} containing the list of {@code RecipeCard}.
 */
public class RecipeListPanelHandle extends NodeHandle<ListView<Recipe>> {
    public static final String RECIPE_LIST_VIEW_ID = "#personListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Recipe> lastRememberedSelectedRecipeCard;

    public RecipeListPanelHandle(ListView<Recipe> recipeListPanelNode) {
        super(recipeListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code RecipeCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public RecipeCardHandle getHandleToSelectedCard() {
        List<Recipe> selectedRecipeList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedRecipeList.size() != 1) {
            throw new AssertionError("Recipe list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(RecipeCardHandle::new)
                .filter(handle -> handle.equals(selectedRecipeList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Recipe> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code recipe}.
     */
    public void navigateToCard(Recipe recipe) {
        if (!getRootNode().getItems().contains(recipe)) {
            throw new IllegalArgumentException("Recipe does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(recipe);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code RecipeCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the recipe card handle of a recipe associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public RecipeCardHandle getRecipeCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(RecipeCardHandle::new)
                .filter(handle -> handle.equals(getRecipe(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Recipe getRecipe(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code RecipeCard} in the list.
     */
    public void rememberSelectedRecipeCard() {
        List<Recipe> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedRecipeCard = Optional.empty();
        } else {
            lastRememberedSelectedRecipeCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code RecipeCard} is different from the value remembered by the most recent
     * {@code rememberSelectedRecipeCard()} call.
     */
    public boolean isSelectedRecipeCardChanged() {
        List<Recipe> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedRecipeCard.isPresent();
        } else {
            return !lastRememberedSelectedRecipeCard.isPresent()
                    || !lastRememberedSelectedRecipeCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}

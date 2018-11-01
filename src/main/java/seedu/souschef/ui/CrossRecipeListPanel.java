package seedu.souschef.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.ui.CrossRecipePanelSelectionChangedEvent;
import seedu.souschef.commons.events.ui.JumpToListRequestEvent;
import seedu.souschef.model.recipe.CrossRecipe;

/**
 * Panel containing the list of cross recipes.
 */
public class CrossRecipeListPanel extends GenericListPanel<CrossRecipe> {
    private static final String FXML = "RecipeListPanel.fxml";
    @javafx.fxml.FXML
    protected ListView<CrossRecipe> recipeListView;
    private final Logger logger = LogsCenter.getLogger(CrossRecipeListPanel.class);


    public CrossRecipeListPanel(ObservableList<CrossRecipe> recipeList) {
        super(FXML);
        setConnections(recipeList);
        registerAsAnEventHandler(this);
    }

    @Override
    protected void setConnections(ObservableList<CrossRecipe> recipeList) {
        recipeListView.setItems(recipeList);
        recipeListView.setCellFactory(listView -> new CrossRecipeListPanel.RecipeListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    @Override
    protected void setEventHandlerForSelectionChangeEvent() {
        recipeListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in recipe list panel changed to : '" + newValue + "'");
                        raise(new CrossRecipePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code Card} at the {@code index} and selects it.
     * To be used in handleJumpToListRequestEvent().
     */
    @Override
    protected void scrollTo(int index) {
        Platform.runLater(() -> {
            recipeListView.scrollTo(index);
            recipeListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Override
    @Subscribe
    protected void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Recipe} using a {@code RecipeCard}.
     */
    class RecipeListViewCell extends ListViewCell<CrossRecipe> {
        @Override
        protected void updateItem(CrossRecipe recipe, boolean empty) {
            super.updateItem(recipe, empty);

            if (empty || recipe == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RecipeCard(recipe, getIndex() + 1).getRoot());
            }
        }
    }
}

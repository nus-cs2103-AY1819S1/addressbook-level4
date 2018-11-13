package seedu.souschef.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.ui.FavouritesPanelSelectionChangedEvent;
import seedu.souschef.commons.events.ui.JumpToListRequestEvent;
import seedu.souschef.model.recipe.Recipe;

/**
 * Panel containing the list of recipes.
 */

public class FavouritesPanel extends GenericListPanel<Recipe> {
    private static final String FXML = "FavouritesPanel.fxml";
    @FXML
    protected ListView<Recipe> favouritesListView;
    private final Logger logger = LogsCenter.getLogger(RecipeListPanel.class);


    public FavouritesPanel(ObservableList<Recipe> recipeList) {
        super(FXML);
        setConnections(recipeList);
        registerAsAnEventHandler(this);
    }

    @Override
    protected void setConnections(ObservableList<Recipe> recipeList) {
        favouritesListView.setItems(recipeList);
        favouritesListView.setCellFactory(listView -> new FavouritesListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    @Override
    protected void setEventHandlerForSelectionChangeEvent() {
        favouritesListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in recipe list panel changed to : '" + newValue + "'");
                        raise(new FavouritesPanelSelectionChangedEvent(newValue));
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
            favouritesListView.scrollTo(index);
            favouritesListView.getSelectionModel().clearAndSelect(index);
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
    class FavouritesListViewCell extends ListViewCell<Recipe> {

        @Override
        protected void updateItem(Recipe favourites, boolean empty) {
            super.updateItem(favourites, empty);

            if (empty || favourites == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FavouritesCard(favourites, getIndex() + 1).getRoot());
            }
        }
    }
}

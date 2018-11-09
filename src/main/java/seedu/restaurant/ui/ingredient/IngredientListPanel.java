package seedu.restaurant.ui.ingredient;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.restaurant.commons.core.LogsCenter;
import seedu.restaurant.commons.events.ui.ingredient.IngredientPanelSelectionChangedEvent;
import seedu.restaurant.commons.events.ui.ingredient.JumpToIngredientListRequestEvent;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.ui.UiPart;

/**
 * Panel containing the list of ingredients.
 */
public class IngredientListPanel extends UiPart<Region> {

    private static final String FXML = "IngredientListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(IngredientListPanel.class);

    @FXML
    private ListView<Ingredient> ingredientListView;

    public IngredientListPanel(ObservableList<Ingredient> ingredientList) {
        super(FXML);
        setConnections(ingredientList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Ingredient> ingredientList) {
        ingredientListView.setItems(ingredientList);
        ingredientListView.setCellFactory(listView -> new IngredientListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        ingredientListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in ingredient list panel changed to : '" + newValue + "'");
                        raise(new IngredientPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code IngredientCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            ingredientListView.scrollTo(index);
            ingredientListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToIngredientListRequestEvent(JumpToIngredientListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Ingredient} using an {@code IngredientCard}.
     */
    private class IngredientListViewCell extends ListCell<Ingredient> {

        @Override
        protected void updateItem(Ingredient ingredient, boolean isEmpty) {
            super.updateItem(ingredient, isEmpty);

            if (isEmpty || ingredient == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new IngredientCard(ingredient, getIndex() + 1).getRoot());
            }
        }
    }
}

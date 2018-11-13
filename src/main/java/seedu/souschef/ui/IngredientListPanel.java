package seedu.souschef.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.ui.IngredientPanelSelectionChangedEvent;
import seedu.souschef.commons.events.ui.JumpToListRequestEvent;
import seedu.souschef.model.ingredient.Ingredient;

/**
 * Panel containing the list of ingredients.
 */
public class IngredientListPanel extends GenericListPanel<Ingredient> {
    private static final String FXML = "IngredientListPanel.fxml";
    @javafx.fxml.FXML
    protected ListView<Ingredient> ingredientListView;
    private final Logger logger = LogsCenter.getLogger(IngredientListPanel.class);


    public IngredientListPanel(ObservableList<Ingredient> ingredientList) {
        super(FXML);
        setConnections(ingredientList);
        registerAsAnEventHandler(this);
    }

    @Override
    protected void setConnections(ObservableList<Ingredient> ingredientList) {
        ingredientListView.setItems(ingredientList);
        ingredientListView.setCellFactory(listView -> new IngredientListPanel.IngredientListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    @Override
    protected void setEventHandlerForSelectionChangeEvent() {
        ingredientListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in ingredient list panel changed to : '" + newValue + "'");
                        raise(new IngredientPanelSelectionChangedEvent(newValue));
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
            ingredientListView.scrollTo(index);
            ingredientListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Override
    @Subscribe
    protected void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Ingredient} using a {@code IngredientCard}.
     */
    class IngredientListViewCell extends ListViewCell<Ingredient> {
        @Override
        protected void updateItem(Ingredient ingredient, boolean empty) {
            super.updateItem(ingredient, empty);

            if (empty || ingredient == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new IngredientCard(ingredient, getIndex() + 1).getRoot());
            }
        }
    }
}

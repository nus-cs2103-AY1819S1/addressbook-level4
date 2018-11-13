package seedu.souschef.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.ui.JumpToListRequestEvent;
import seedu.souschef.commons.events.ui.MealPlanPanelSelectionChangedEvent;
import seedu.souschef.model.planner.Day;

/**
 * Panel containing the list of recipes.
 */
public class MealPlanListPanel extends GenericListPanel<Day> {
    private static final String FXML = "MealPlanListPanel.fxml";
    @FXML
    protected ListView<Day> mealPlanListView;
    private final Logger logger = LogsCenter.getLogger(MealPlanListPanel.class);


    public MealPlanListPanel(ObservableList<Day> mealPlannerList) {
        super(FXML);
        setConnections(mealPlannerList);
        registerAsAnEventHandler(this);
    }

    @Override
    protected void setConnections(ObservableList<Day> mealPlannerList) {
        mealPlanListView.setItems(mealPlannerList);
        mealPlanListView.setCellFactory(listView -> new MealPlanListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    @Override
    protected void setEventHandlerForSelectionChangeEvent() {
        mealPlanListView.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    logger.fine("Selection in recipe list panel changed to : '" + newValue + "'");
                    raise(new MealPlanPanelSelectionChangedEvent(newValue));
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
            mealPlanListView.scrollTo(index);
            mealPlanListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Override
    @Subscribe
    protected void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Day} using a {@code MealPlanCard}.
     */
    class MealPlanListViewCell extends ListViewCell<Day> {
        @Override
        protected void updateItem(Day day, boolean empty) {
            super.updateItem(day, empty);

            if (empty || day == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MealPlanCard(day, getIndex() + 1).getRoot());
            }
        }
    }

}

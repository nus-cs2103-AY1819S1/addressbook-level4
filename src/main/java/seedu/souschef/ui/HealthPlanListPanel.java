package seedu.souschef.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.ui.HealthPlanPanelSelectionChangedEvent;
import seedu.souschef.commons.events.ui.JumpToListRequestEvent;
import seedu.souschef.model.healthplan.HealthPlan;

/**
 * Panel for the healthPlanlist
 */
public class HealthPlanListPanel extends GenericListPanel<HealthPlan> {

    private static final String FXML = "HealthPlanListPanel.fxml";
    @FXML
    protected ListView<HealthPlan> healthPlanListView;
    private final Logger logger = LogsCenter.getLogger(HealthPlanListPanel.class);


    public HealthPlanListPanel (ObservableList<HealthPlan> healthPlanList) {
        super(FXML);
        setConnections(healthPlanList);
        registerAsAnEventHandler(this);
    }

    @Override
    protected void setConnections(ObservableList<HealthPlan> healthPlanList) {
        healthPlanListView.setItems(healthPlanList);
        healthPlanListView.setCellFactory(listView -> new HealthPlanListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    @Override
    protected void setEventHandlerForSelectionChangeEvent() {
        healthPlanListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in recipe list panel changed to : '" + newValue + "'");
                        raise(new HealthPlanPanelSelectionChangedEvent(newValue));
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
            healthPlanListView.scrollTo(index);
            healthPlanListView.getSelectionModel().clearAndSelect(index);
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
    class HealthPlanListViewCell extends ListViewCell<HealthPlan> {
        @Override
        protected void updateItem(HealthPlan healthPlan, boolean empty) {
            super.updateItem(healthPlan, empty);

            if (empty || healthPlan == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new HealthPlanCard(healthPlan, getIndex() + 1).getRoot());
            }
        }
    }









}

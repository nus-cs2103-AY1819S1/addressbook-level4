package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PlanPanelSelectionChangedEvent;
import seedu.address.model.healthplan.HealthPlan;






public class HealthPlanPanel extends UiPart<Region> {

    private static final String FXML = "healthplanPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(HealthPlanPanel.class);


    @FXML
    private ListView<HealthPlan> healthplanView;

    public HealthPlanPanel(ObservableList<HealthPlan> planList) {
        super(FXML);
        setConnections(planList);
        registerAsAnEventHandler(this);
    }

    //bind all the entries found in the xml file
    private void setConnections(ObservableList<HealthPlan> planList) {
        healthplanView.setItems(planList);
        healthplanView.setCellFactory(listView -> new HPListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    //bind event handler to handle the case when user selects a row
    private void setEventHandlerForSelectionChangeEvent() {
        healthplanView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PlanPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    private void scrollTo(int index) {
        Platform.runLater(() -> {
            healthplanView.scrollTo(index);
            healthplanView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }


    class HPListViewCell extends ListCell<HealthPlan> {
        @Override
        protected void updateItem(HealthPlan plan, boolean empty) {
            super.updateItem(plan, empty);

            if (empty || plan == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new HealthPlanCard(plan, getIndex() + 1).getRoot());
            }
        }
    }




}

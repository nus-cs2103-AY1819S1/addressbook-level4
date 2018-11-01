package seedu.souschef.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.events.ui.JumpToListRequestEvent;
import seedu.souschef.commons.events.ui.MealPlanPanelSelectionChangedEvent;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.planner.Day;



/**
 *  controller class for health plan details
 */
public class HealthPlanDetailsPanel extends GenericListPanel<HealthPlan> {

    private static final String FXML = "HealthPlanDetailsPanel.fxml";

    @FXML
    protected ListView<Day> daysListView;

    @FXML
    protected Label planName;

    private final Logger logger = LogsCenter.getLogger(HealthPlanDetailsPanel.class);


    public HealthPlanDetailsPanel (ObservableList<HealthPlan> plan, int index) {
        super(FXML);
        index = index - 1;
        ArrayList<HealthPlan> temp = new ArrayList<>();
        temp.add(plan.get(index));

        ObservableList<HealthPlan> targetPlan = FXCollections.observableArrayList(temp);
        setConnections(targetPlan);
        planName.setText(plan.get(index).getHealthPlanName().planName);


        registerAsAnEventHandler(this);
    }


    @Override
    protected void setConnections(ObservableList<HealthPlan> plan) {

        ObservableList<Day> days = FXCollections.observableArrayList(plan.get(0).getMealPlans());


        daysListView.setItems(days);
        daysListView.setCellFactory(listView -> new DayListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    @Override
    protected void setEventHandlerForSelectionChangeEvent() {
        daysListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in day list panel changed to : '" + newValue + "'");
                        raise(new MealPlanPanelSelectionChangedEvent(newValue));
                    }
                });
    }


    @Override
    protected void scrollTo(int index) {
        Platform.runLater(() -> {
            daysListView.scrollTo(index);
            daysListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Override
    @Subscribe
    protected void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }




    /**
     *  cell implementation to handle the day listfor health plans details
     */
    class DayListViewCell extends ListViewCell<Day> {
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



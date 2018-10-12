package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.event.Event;

public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private ListView<List<Event>> eventListView;

    public EventListPanel(ObservableList<List<Event>> eventList) {
        super(FXML);
        setConnections(eventList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<List<Event>> eventList) {
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code List<Event>} using a {@code EventListCard}.
     */
    class EventListViewCell extends ListCell<List<Event>> {
        @Override
        protected void updateItem(List<Event> eventListByDay, boolean empty) {
            super.updateItem(eventListByDay, empty);

            if (empty || eventListByDay.isEmpty()) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventListCard(eventListByDay).getRoot());
            }
        }
    }
}

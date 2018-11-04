package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookEventTagChangedEvent;
import seedu.address.commons.events.ui.EventPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Panel containing the list of events.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    private final ObservableList<Tag> eventTagList;

    @FXML
    private FlowPane allEventTags;

    @FXML
    private ListView<List<Event>> eventListView;

    public EventListPanel(ObservableList<List<Event>> eventList, ObservableList<Person> personList,
                          ObservableList<Tag> eventTagList) {
        super(FXML);
        this.eventTagList = eventTagList;
        setConnections(eventList, personList, eventTagList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<List<Event>> eventList, ObservableList<Person> personList,
                                ObservableList<Tag> eventTagList) {

        eventTagList.forEach(eventTag -> allEventTags.getChildren().add(new Label(eventTag.tagName)));

        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell(personList));
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        eventListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in event list panel changed to : '" + newValue + "'");
                        raise(new EventPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            eventListView.scrollTo(index);
            eventListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handleAddressBookEventTagChangedEvent(AddressBookEventTagChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        // manually reset eventTags (FlowPane) display
        allEventTags.getChildren().removeAll(allEventTags.getChildren());
        eventTagList.forEach(eventTag -> allEventTags.getChildren().add(new Label(eventTag.tagName)));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code List<Event>} using a {@code EventListCard}.
     */
    class EventListViewCell extends ListCell<List<Event>> {

        private List<Person> personList;

        public EventListViewCell(List<Person> personList) {
            super();
            this.personList = personList;
        }

        @Override
        protected void updateItem(List<Event> eventListByDate, boolean empty) {
            super.updateItem(eventListByDate, empty);

            if (empty || eventListByDate.isEmpty()) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventListCard(eventListByDate, personList).getRoot());
            }
        }
    }
}

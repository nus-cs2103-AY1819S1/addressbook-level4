package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookEventTagChangedEvent;
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
    private FlowPane eventTags;

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

        eventTagList.forEach(eventTag -> eventTags.getChildren().add(new Label(eventTag.getLowerCaseTagName())));

        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell(personList));
    }

    @Subscribe
    private void handleAddressBookEventTagChangedEvent(AddressBookEventTagChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        // manually reset eventTags (FlowPane) display
        eventTags.getChildren().removeAll(eventTags.getChildren());
        eventTagList.forEach(eventTag -> eventTags.getChildren().add(new Label(eventTag.getLowerCaseTagName())));
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

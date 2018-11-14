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
import seedu.address.commons.events.ui.JumpToPersonListRequestEvent;
import seedu.address.commons.events.ui.PersonBrowserChangeEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.RefreshPersonBrowserEvent;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {

    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        setConnections(personList);

        registerAsAnEventHandler(this);
    }

    /**
     * Clears selection of the view.
     */
    public void clearSelection() {
        Platform.runLater(() -> {
            personListView.getSelectionModel().clearSelection();
        });
    }

    /**
     * Change list of view.
     * @param personList updated list.
     */
    public void updatePanel(ObservableList<Person> personList) {
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    private void setConnections(ObservableList<Person> personList) {
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        personListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    private boolean isListSelected() {
        return personListView.getSelectionModel().getSelectedIndex() > -1;
    }

    @Subscribe
    private void handleRefreshBrowserEvent(RefreshPersonBrowserEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (isListSelected()) {
            int index = personListView.getSelectionModel().getSelectedIndex();
            personListView.getSelectionModel().clearSelection();
            personListView.getSelectionModel().clearAndSelect(index);
            Person currPerson = personListView.getSelectionModel().getSelectedItem();
            raise(new PersonBrowserChangeEvent(currPerson));
        }
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            personListView.scrollTo(index);
            personListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToPersonListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}

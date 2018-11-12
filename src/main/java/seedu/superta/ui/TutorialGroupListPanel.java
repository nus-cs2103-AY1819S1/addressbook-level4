package seedu.superta.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import seedu.superta.commons.core.LogsCenter;
import seedu.superta.commons.events.ui.TutorialGroupSelectedEvent;
import seedu.superta.model.ReadOnlySuperTaClient;
import seedu.superta.model.tutorialgroup.TutorialGroup;

// @@author Caephler
/**
 * Panel containing the list of tutorial groups.
 */
public class TutorialGroupListPanel extends ViewPanelContent {
    private static final String FXML = "TutorialGroupListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TutorialGroupListPanel.class);

    @javafx.fxml.FXML
    private ListView<TutorialGroup> tutorialGroupListView;

    private ObservableList<TutorialGroup> tutorialGroups;

    public TutorialGroupListPanel(ObservableList<TutorialGroup> tutorialGroups) {
        super(FXML);
        this.tutorialGroups = tutorialGroups;
        setConnections();
        registerAsAnEventHandler(this);
    }

    private void setConnections() {
        tutorialGroupListView.setItems(tutorialGroups);
        tutorialGroupListView.setCellFactory(listView -> new TutorialGroupListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        tutorialGroupListView.setOnMouseClicked(event -> {
            raise(new TutorialGroupSelectedEvent(tutorialGroupListView.getSelectionModel().getSelectedItem()));
        });
    }

    @Override
    public void update(ReadOnlySuperTaClient model) {
        tutorialGroups = model.getTutorialGroupList();
        setConnections();
        setEventHandlerForSelectionChangeEvent();
    }

    /**
     * ListCell for Tutorial Group.
     */
    class TutorialGroupListViewCell extends ListCell<TutorialGroup> {
        @Override
        protected void updateItem(TutorialGroup tutorialGroup, boolean empty) {
            super.updateItem(tutorialGroup, empty);

            if (empty || tutorialGroup == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TutorialGroupCard(tutorialGroup).getRoot());
            }
        }
    }
}

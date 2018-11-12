package seedu.address.ui.test.openendedtest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.test.Attempt;
import seedu.address.model.test.openendedtest.OpenEndedTest;
import seedu.address.ui.UiPart;

/**
 * A section of the matching result page which shows the list of attempts made the the user in the Open Ended Test.
 */
public class OpenEndedTestAttemptList extends UiPart<Region> {
    private static final String FXML = "test/openendedtest/OpenEndedAttemptList.fxml";

    @FXML
    private ListView<Attempt> openEndedAttemptView;

    public OpenEndedTestAttemptList(OpenEndedTest openEndedTest) {
        super(FXML);
        setConnections(FXCollections.observableArrayList(openEndedTest.getAttempts()));
    }

    private void setConnections(ObservableList<Attempt> attemptList) {
        openEndedAttemptView.setItems(attemptList);
        openEndedAttemptView.setCellFactory(listView -> new AttemptListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Attempt}
     */
    class AttemptListViewCell extends ListCell<Attempt> {
        @Override
        protected void updateItem(Attempt attempt, boolean empty) {
            super.updateItem(attempt, empty);

            if (empty || attempt == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new OpenEndedTestAttemptView(attempt).getRoot());
            }
        }
    }
}

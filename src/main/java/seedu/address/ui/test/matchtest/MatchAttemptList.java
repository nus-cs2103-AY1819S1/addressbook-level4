package seedu.address.ui.test.matchtest;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.test.matchtest.MatchAttempt;
import seedu.address.model.test.matchtest.MatchTest;
import seedu.address.ui.UiPart;

/**
 * A section of the matching result page which shows the list of attempts made the the user in the matching test.
 */
public class MatchAttemptList extends UiPart<Region> {
    private static final String FXML = "/test/matchtest/MatchAttemptList.fxml";
    private final Logger logger = LogsCenter.getLogger(MatchAttemptList.class);

    @FXML
    private ListView<MatchAttempt> matchAttemptView;

    public MatchAttemptList(MatchTest matchTest) {
        super(FXML);
        setConnections(FXCollections.observableArrayList(matchTest.getAttempts()));
    }

    private void setConnections(ObservableList<MatchAttempt> attemptList) {
        matchAttemptView.setItems(attemptList);
        matchAttemptView.setCellFactory(listView -> new AttemptListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Attempt} using a {@code MatchAttemptView}.
     */
    class AttemptListViewCell extends ListCell<MatchAttempt> {
        @Override
        protected void updateItem(MatchAttempt attempt, boolean empty) {
            super.updateItem(attempt, empty);

            if (empty || attempt == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MatchAttemptView(attempt, getIndex() + 1).getRoot());
            }
        }
    }
}

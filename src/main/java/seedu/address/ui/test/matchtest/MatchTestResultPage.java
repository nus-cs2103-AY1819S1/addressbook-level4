package seedu.address.ui.test.matchtest;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.test.matchtest.MatchTest;
import seedu.address.ui.test.TriviaTestResultPage;

/**
 * The page that shows up after the user has completed a matching test.
 */
public class MatchTestResultPage extends TriviaTestResultPage {
    private static final String FXML = "/test/matchtest/MatchTestResultPage.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private final MatchTest matchTest;
    private final MatchAttemptList matchAttemptList;

    @FXML
    private Label testDateText;

    @FXML
    private Label durationText;

    @FXML
    private Label topicText;

    @FXML
    private Label numOfCardsText;

    @FXML
    private Label numOfAttemptsText;

    @FXML
    private StackPane attemptListPlaceholder;

    public MatchTestResultPage(MatchTest matchTest) {
        super(FXML);

        this.matchTest = matchTest;
        testDateText.setText(StringUtil.formatDate(matchTest.getTestDate()));
        durationText.setText(String.valueOf(matchTest.getDuration()) + "s");
        topicText.setText(matchTest.getTopic().topicName);
        numOfCardsText.setText(String.valueOf(matchTest.getCardsTested().size()));
        numOfAttemptsText.setText(String.valueOf(matchTest.getAttempts().size()) + " Attempts");

        matchAttemptList = new MatchAttemptList(matchTest);
        attemptListPlaceholder.getChildren().add(matchAttemptList.getRoot());
    }
}

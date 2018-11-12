package seedu.address.ui.test.openendedtest;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.test.openendedtest.OpenEndedTest;
import seedu.address.ui.test.TriviaTestResultPage;

/**
 * The page that shows up after the user has completed Open Ended Test.
 */
public class OpenEndedTestResultPage extends TriviaTestResultPage {
    private static final String FXML = "test/openendedtest/OpenEndedTestResultPage.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private final OpenEndedTest openEndedTest;
    private final OpenEndedTestAttemptList openEndedTestAttemptList;

    @javafx.fxml.FXML
    private Label testDateText;

    @FXML
    private Label durationText;

    @FXML
    private Label topicText;

    @FXML
    private Label numOfCardsText;

    @FXML
    private StackPane attemptListPlaceholder;

    public OpenEndedTestResultPage(OpenEndedTest openEndedTest) {
        super(FXML);

        this.openEndedTest = openEndedTest;
        testDateText.setText(DateUtil.format(openEndedTest.getTestDate()));
        durationText.setText(String.valueOf(openEndedTest.getDuration().getValue()) + "s");
        topicText.setText(openEndedTest.getTopic().topicName);
        numOfCardsText.setText(String.valueOf(openEndedTest.getCorrectAttempts()) + " / "
                + String.valueOf(openEndedTest.getCardsTested().size()));

        openEndedTestAttemptList = new OpenEndedTestAttemptList(openEndedTest);
        attemptListPlaceholder.getChildren().add(openEndedTestAttemptList.getRoot());
    }
}

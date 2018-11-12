package guitests.guihandles.triviatest.matchtest;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * A handler for the UI class {@code MatchTestResultPage}
 */
public class MatchTestResultPageHandle extends NodeHandle<Node> {
    public static final String TEST_DATE_ID = "#testDateText";
    public static final String TOPIC_ID = "#topicText";
    public static final String NUM_OF_CARDS_ID = "#numOfCardsText";
    public static final String NUM_OF_ATTEMPTS_ID = "#numOfAttemptsText";
    public static final String ATTEMPT_LIST_PLACEHOLDER_ID = "#matchAttemptView";

    private Label testDateText;
    private Label topicText;
    private Label numOfCards;
    private Label numOfAttempts;

    private MatchAttemptListHandle matchAttemptListHandle;

    public MatchTestResultPageHandle(Node matchTestResultPageNode) {
        super(matchTestResultPageNode);

        testDateText = getChildNode(TEST_DATE_ID);
        topicText = getChildNode(TOPIC_ID);
        numOfCards = getChildNode(NUM_OF_CARDS_ID);
        numOfAttempts = getChildNode(NUM_OF_ATTEMPTS_ID);
        matchAttemptListHandle = new MatchAttemptListHandle(getChildNode(ATTEMPT_LIST_PLACEHOLDER_ID));
    }

    public String getTestDateText() {
        return testDateText.getText();
    }

    public String getTopicText() {
        return topicText.getText();
    }

    public String getNumOfCards() {
        return numOfCards.getText();
    }

    public String getNumOfAttempts() {
        return numOfAttempts.getText();
    }

    public MatchAttemptListHandle getMatchAttemptListHandle() {
        return matchAttemptListHandle;
    }
}

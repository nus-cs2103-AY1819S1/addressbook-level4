package seedu.address.ui;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_PHYSICS;
import static seedu.address.testutil.MatchTestUtil.completeMatchTest;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.triviatest.matchtest.MatchTestResultPageHandle;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TriviaBundle;
import seedu.address.model.test.matchtest.MatchTest;
import seedu.address.model.topic.Topic;
import seedu.address.testutil.TypicalCards;
import seedu.address.ui.test.matchtest.MatchTestResultPage;

public class MatchTestResultPageTest extends GuiUnitTest {

    private Model model;
    private TriviaBundle triviaBundle;
    private MatchTest matchTest;
    private MatchTestResultPage matchTestResultPage;

    @Before
    public void setUp() {
        model = new ModelManager();
        triviaBundle = new TriviaBundle(TypicalCards.getTypicalTriviaBundle());
        matchTest = new MatchTest(new Topic(VALID_TOPIC_PHYSICS), triviaBundle);
        completeMatchTest(model, matchTest);
        matchTestResultPage = new MatchTestResultPage(matchTest);
    }

    @Test
    public void display() {
        uiPartRule.setUiPart(matchTestResultPage);
        guiRobot.pauseForHuman();
        assertCorrectDisplay();
    }

    /**
     * Assert that MatchTestResultPage is displayed correctly.
     */
    private void assertCorrectDisplay() {
        MatchTestResultPageHandle resultPageHandle = new
                MatchTestResultPageHandle(matchTestResultPage.getRoot());
        assertEquals(resultPageHandle.getNumOfAttempts(),
                String.valueOf(matchTest.getAttempts().size()) + " Attempt(s)");
        assertEquals(resultPageHandle.getNumOfCards(), String.valueOf(matchTest.getCardsTested().size()));
        assertEquals(resultPageHandle.getTestDateText(), DateUtil.format(matchTest.getTestDate()));
        assertEquals(resultPageHandle.getTopicText(), matchTest.getTopic().topicName);
        assertCorrectAttemptListDisplay(resultPageHandle);
    }

    /**
     * Assert that the attemptlist is displayed correctly.
     */
    public void assertCorrectAttemptListDisplay(MatchTestResultPageHandle matchTestResultPageHandle) {
        for (int i = 0; i < matchTest.getAttempts().size(); i++) {
            assertTrue(matchTestResultPageHandle.getMatchAttemptListHandle()
                    .getAttemptViewHandle(i).equals(matchTest.getAttempts().get(i)));
        }
    }
}

package seedu.address.ui;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_PHYSICS;
import static seedu.address.testutil.EventsUtil.postNow;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.triviatest.matchtest.AnswerListPanelHandle;
import guitests.guihandles.triviatest.matchtest.MatchTestPageHandle;
import guitests.guihandles.triviatest.matchtest.QuestionListPanelHandle;

import javafx.scene.paint.Color;

import seedu.address.commons.events.ui.FlashMatchOutcomeEvent;
import seedu.address.model.TriviaBundle;
import seedu.address.model.card.IndexedAnswer;
import seedu.address.model.card.IndexedQuestion;
import seedu.address.model.test.matchtest.MatchTest;
import seedu.address.model.topic.Topic;
import seedu.address.testutil.TypicalCards;
import seedu.address.ui.test.matchtest.MatchTestPage;

public class MatchTestPageTest extends GuiUnitTest {

    private static final FlashMatchOutcomeEvent CORRECT_MATCH_OUTCOME_EVENT = new FlashMatchOutcomeEvent(0, 0, true);
    private static final FlashMatchOutcomeEvent WRONG_MATCH_OUTCOME_EVENT = new FlashMatchOutcomeEvent(0, 0, false);

    private TriviaBundle triviaBundle;
    private MatchTest matchTest;
    private MatchTestPage matchTestPage;

    @Before
    public void setUp() {
        triviaBundle = new TriviaBundle(TypicalCards.getTypicalTriviaBundle());
        matchTest = new MatchTest(new Topic(VALID_TOPIC_PHYSICS), triviaBundle);
        matchTestPage = new MatchTestPage(matchTest);
    }

    @Test
    public void display() {
        uiPartRule.setUiPart(matchTestPage);
        assertMatchTestPageDisplayCorrect(matchTestPage, matchTest);
    }

    @Test
    public void handleFlashCorrectMatchOutcomeEvent() {
        uiPartRule.setUiPart(matchTestPage);
        postNow(CORRECT_MATCH_OUTCOME_EVENT);
        guiRobot.pauseForHuman();
        assertCorrectFlashColor(new Color(0, 1, 0, 1));
    }

    @Test
    public void handleFlashWrongMatchOutcomeEvent() {
        uiPartRule.setUiPart(matchTestPage);
        postNow(WRONG_MATCH_OUTCOME_EVENT);
        guiRobot.pauseForHuman();
        assertCorrectFlashColor(new Color(1, 0, 0, 1));
    }

    /**
     * Asserts that the flash is occur in the right cell with the right color.
     */
    public void assertCorrectFlashColor(Color color) {
        MatchTestPageHandle matchTestPageHandle = new MatchTestPageHandle(matchTestPage.getRoot());
        AnswerListPanelHandle answerListPanelHandle = matchTestPageHandle.getAnswerListPanelHandle();
        QuestionListPanelHandle questionListPanelHandle = matchTestPageHandle.getQuestionListPanelHandle();

        for (int i = 0; i < matchTest.getQuestions().size(); i++) {
            if (i == 0) {
                assertTrue(isSimilarColor(answerListPanelHandle.getBackgroundColor(i), color));
            } else {
                assertFalse(isSimilarColor(answerListPanelHandle.getBackgroundColor(i), color));
            }
        }

        for (int i = 0; i < matchTest.getAnswers().size(); i++) {
            if (i == 0) {
                assertTrue(isSimilarColor(questionListPanelHandle.getBackgroundColor(i), color));
            } else {
                assertFalse(isSimilarColor(questionListPanelHandle.getBackgroundColor(i), color));
            }
        }
    }

    /**
     * Asserts that {@code cardInfoPanel} displays the details of {@code expectedCardInfoPanel} correctly.
     */
    private void assertMatchTestPageDisplayCorrect(MatchTestPage matchTestPage, MatchTest matchTest) {
        guiRobot.pauseForHuman();

        MatchTestPageHandle matchTestPageHandle = new MatchTestPageHandle(matchTestPage.getRoot());

        assertEquals(matchTestPageHandle.getMatchTestTopic(), matchTest.getTopic().topicName);
        assertEquals(matchTestPageHandle.getMatchTestDuration(),
                matchTest.getDuration().getValue().toString() + " s");

        assertQuestionListPanelCorrect(matchTestPageHandle.getQuestionListPanelHandle(), matchTest.getQuestions());
        assertAnswerListPanelCorrect(matchTestPageHandle.getAnswerListPanelHandle(), matchTest.getAnswers());
    }

    /**
     * Asserts that the question list panel is displayed correctly.
     */
    private void assertQuestionListPanelCorrect(QuestionListPanelHandle questionListHandle,
                                                List<IndexedQuestion> questionList) {
        for (int i = 0; i < questionList.size(); i++) {
            assertTrue(questionListHandle.getQuestionViewHandle(i).equals(questionList.get(i)));
        }
    }

    /**
     * Asserts that the answer list panel is displayed correctly.
     */
    private void assertAnswerListPanelCorrect(AnswerListPanelHandle answerListHandle, List<IndexedAnswer> answerList) {
        for (int i = 0; i < answerList.size(); i++) {
            assertTrue(answerListHandle.getAnswerViewHandle(i).equals(answerList.get(i)));
        }
    }

    /**
     * Comparing according to color, ignoring the opacity that differs when the color flashes.
     */
    boolean isSimilarColor(Color c1, Color c2) {
        return c1.getBlue() == c1.getBlue() && c1.getGreen() == c2.getGreen() && c1.getRed() == c2.getRed();
    }
}

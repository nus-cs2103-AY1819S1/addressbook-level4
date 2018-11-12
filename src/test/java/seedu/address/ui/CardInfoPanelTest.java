package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalAttempts.FLAT_EARTH_CORRECT_ATTEMPT;
import static seedu.address.testutil.TypicalAttempts.FLAT_EARTH_WRONG_ATTEMPT;
import static seedu.address.testutil.TypicalCards.Q_FLAT_EARTH;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardInfoPanelDisplaysCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import guitests.guihandles.CardInfoPanelHandle;
import seedu.address.model.card.Card;
import seedu.address.model.test.Attempt;
import seedu.address.testutil.CardBuilder;
import seedu.address.ui.home.CardInfoPanel;


public class CardInfoPanelTest extends GuiUnitTest {

    @Test
    public void display() {
        // 1 Wrong attempt and 1 correct attempt, Expected answer is still the same as the expected answer from the most
        // recent mistake.
        CardInfoPanel cardInfoPanel = new CardInfoPanel();
        List<Attempt> attemptsOnCard = new ArrayList<>(Arrays.asList(FLAT_EARTH_CORRECT_ATTEMPT,
                FLAT_EARTH_WRONG_ATTEMPT));
        cardInfoPanel.loadCardPage(Q_FLAT_EARTH, attemptsOnCard);
        uiPartRule.setUiPart(cardInfoPanel);
        assertCardInfoPanelDisplay(cardInfoPanel, Q_FLAT_EARTH, attemptsOnCard);

        // 1 Wrong attempt and 1 correct attempt, Expected answer is diff from the expected answer from the most
        // recent mistake.
        Card editedCard = new CardBuilder(Q_FLAT_EARTH).withAnswer("Enhanced answer").build();
        CardInfoPanel secondCardInfoPanel = new CardInfoPanel();
        secondCardInfoPanel.loadCardPage(editedCard, attemptsOnCard);
        uiPartRule.setUiPart(secondCardInfoPanel);
        assertCardInfoPanelDisplay(secondCardInfoPanel, editedCard, attemptsOnCard);

        // No Attempt.
        CardInfoPanel thirdCardInfoPanel = new CardInfoPanel();
        List<Attempt> emptyAttempt = new ArrayList<>();
        thirdCardInfoPanel.loadCardPage(editedCard, emptyAttempt);
        uiPartRule.setUiPart(thirdCardInfoPanel);
        assertCardInfoPanelDisplay(thirdCardInfoPanel, editedCard, emptyAttempt);
    }

    /**
     * Asserts that {@code cardInfoPanel} displays the details of {@code expectedCardInfoPanel} correctly.
     */
    private void assertCardInfoPanelDisplay(CardInfoPanel cardInfoPanel, Card expectedCardDisplay,
                                            List<Attempt> attemptsOnCard) {
        guiRobot.pauseForHuman();

        CardInfoPanelHandle cardInfoPanelHandle = new CardInfoPanelHandle(cardInfoPanel.getRoot());

        // verify question is displayed correctly
        assertEquals(expectedCardDisplay.getQuestion().toString(), cardInfoPanelHandle.getCardInfoQuestion());

        // verify person details are displayed correctly
        assertCardInfoPanelDisplaysCard(expectedCardDisplay, attemptsOnCard, cardInfoPanelHandle);
    }
}

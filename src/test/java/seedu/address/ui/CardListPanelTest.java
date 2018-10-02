package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalCards.getTypicalCards;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARD;

import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardViewDisplay;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.CardListPanelHandle;
import guitests.guihandles.CardViewHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;

import seedu.address.model.card.Card;
import seedu.address.storage.XmlSerializableTriviaBundle;




public class CardListPanelTest extends GuiUnitTest {
    private static final ObservableList<Card> TYPICAL_CARDS =
            FXCollections.observableList(getTypicalCards());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_CARD);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private CardListPanelHandle cardListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_CARDS);

        for (int i = 0; i < TYPICAL_CARDS.size(); i++) {
            cardListPanelHandle.navigateToCard(TYPICAL_CARDS.get(i));
            Card expectedPerson = TYPICAL_CARDS.get(i);
            CardViewHandle actualCard = cardListPanelHandle.getCardViewHandle(i);

            assertCardViewDisplay(expectedPerson, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_CARDS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        CardViewHandle expectedPerson = cardListPanelHandle.getCardViewHandle(INDEX_SECOND_CARD.getZeroBased());
        CardViewHandle selectedPerson = cardListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedPerson, selectedPerson);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code PersonListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Card> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of cards views exceeded time limit");
    }

    /**
     * Returns a list of persons containing {@code personCount} persons that is used to populate the
     * {@code PersonListPanel}.
     */
    private ObservableList<Card> createBackingList(int cardCount) throws Exception {
        Path xmlFile = createXmlFileWithCard(cardCount);
        XmlSerializableTriviaBundle xmlTrivialBundle =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableTriviaBundle.class);
        return FXCollections.observableArrayList(xmlTrivialBundle.toModelType().getCardList());
    }

    /**
     * Returns a .xml file containing {@code personCount} persons. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithCard(int cardCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<triviabundle>\n");
        for (int i = 0; i < cardCount; i++) {
            builder.append("<cards>\n");
            builder.append("<question>").append(i).append("a</question>\n");
            builder.append("<answer>21</answer>\n");
            builder.append("<tagged>Physics</tagged>");
            builder.append("</cards>\n");
        }
        builder.append("</triviabundle>\n");

        Path manyCardsFile = Paths.get(TEST_DATA_FOLDER + "manyCards.xml");
        FileUtil.createFile(manyCardsFile);
        FileUtil.writeToFile(manyCardsFile, builder.toString());
        manyCardsFile.toFile().deleteOnExit();
        return manyCardsFile;
    }

    /**
     * Initializes {@code personListPanelHandle} with a {@code PersonListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PersonListPanel}.
     */
    private void initUi(ObservableList<Card> backingList) {
        CardListPanel cardListPanel = new CardListPanel(backingList);
        uiPartRule.setUiPart(cardListPanel);

        cardListPanelHandle = new CardListPanelHandle(getChildNode(cardListPanel.getRoot(),
                CardListPanelHandle.CARD_LIST_VIEW_ID));
    }
}

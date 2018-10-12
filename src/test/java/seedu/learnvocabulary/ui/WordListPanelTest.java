package seedu.learnvocabulary.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.learnvocabulary.testutil.EventsUtil.postNow;
import static seedu.learnvocabulary.testutil.TypicalIndexes.INDEX_SECOND_WORD;
import static seedu.learnvocabulary.testutil.TypicalWords.getTypicalWords;
import static seedu.learnvocabulary.ui.testutil.GuiTestAssert.assertCardDisplaysWord;
import static seedu.learnvocabulary.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.WordCardHandle;
import guitests.guihandles.WordListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.learnvocabulary.commons.events.ui.JumpToListRequestEvent;
import seedu.learnvocabulary.commons.util.FileUtil;
import seedu.learnvocabulary.commons.util.XmlUtil;
import seedu.learnvocabulary.model.word.Word;
import seedu.learnvocabulary.storage.XmlSerializableLearnVocabulary;

public class WordListPanelTest extends GuiUnitTest {
    private static final ObservableList<Word> TYPICAL_WORDS =
            FXCollections.observableList(getTypicalWords());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_WORD);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private WordListPanelHandle wordListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_WORDS);

        for (int i = 0; i < TYPICAL_WORDS.size(); i++) {
            wordListPanelHandle.navigateToCard(TYPICAL_WORDS.get(i));
            Word expectedWord = TYPICAL_WORDS.get(i);
            WordCardHandle actualCard = wordListPanelHandle.getWordCardHandle(i);

            assertCardDisplaysWord(expectedWord, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_WORDS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        WordCardHandle expectedWord = wordListPanelHandle.getWordCardHandle(INDEX_SECOND_WORD.getZeroBased());
        WordCardHandle selectedWord = wordListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedWord, selectedWord);
    }

    /**
     * Verifies that creating and deleting large number of words in {@code WordListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Word> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of word cards exceeded time limit");
    }

    /**
     * Returns a list of words containing {@code wordCount} words that is used to populate the
     * {@code WordListPanel}.
     */
    private ObservableList<Word> createBackingList(int wordCount) throws Exception {
        Path xmlFile = createXmlFileWithWords(wordCount);
        XmlSerializableLearnVocabulary xmlLearnVocabulary =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableLearnVocabulary.class);
        return FXCollections.observableArrayList(xmlLearnVocabulary.toModelType().getWordList());
    }

    /**
     * Returns a .xml file containing {@code wordCount} words. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithWords(int wordCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<learnvocabulary>\n");
        for (int i = 0; i < wordCount; i++) {
            builder.append("<words>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<meaning>Test</meaning>\n");
            builder.append("<phone>000</phone>\n");
            builder.append("<address>a</address>\n");
            builder.append("</words>\n");
        }
        builder.append("</learnvocabulary>\n");

        Path manyWordsFile = TEST_DATA_FOLDER.resolve("manyWords.xml");
        FileUtil.createFile(manyWordsFile);
        FileUtil.writeToFile(manyWordsFile, builder.toString());
        manyWordsFile.toFile().deleteOnExit();
        return manyWordsFile;
    }

    /**
     * Initializes {@code wordListPanelHandle} with a {@code WordListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code WordListPanel}.
     */
    private void initUi(ObservableList<Word> backingList) {
        WordListPanel wordListPanel = new WordListPanel(backingList);
        uiPartRule.setUiPart(wordListPanel);

        wordListPanelHandle = new WordListPanelHandle(getChildNode(wordListPanel.getRoot(),
                WordListPanelHandle.WORD_LIST_VIEW_ID));
    }
}

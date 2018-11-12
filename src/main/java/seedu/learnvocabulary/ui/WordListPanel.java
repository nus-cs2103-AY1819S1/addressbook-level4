package seedu.learnvocabulary.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.learnvocabulary.commons.core.LogsCenter;
import seedu.learnvocabulary.commons.events.ui.JumpToListRequestEvent;
import seedu.learnvocabulary.commons.events.ui.WordPanelSelectionChangedEvent;
import seedu.learnvocabulary.model.word.Word;

/**
 * Panel containing the list of words.
 */
public class WordListPanel extends UiPart<Region> {
    private static final String FXML = "WordListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(WordListPanel.class);

    @FXML
    private ListView<Word> wordListView;

    public WordListPanel(ObservableList<Word> wordList) {
        super(FXML);
        setConnections(wordList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Word> wordList) {
        wordListView.setItems(wordList);
        wordListView.setCellFactory(listView -> new WordListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        wordListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in word list panel changed to : '" + newValue + "'");
                        raise(new WordPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code WordCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            wordListView.scrollTo(index);
            wordListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Word} using a {@code WordCard}.
     */
    class WordListViewCell extends ListCell<Word> {
        @Override
        protected void updateItem(Word word, boolean empty) {
            super.updateItem(word, empty);

            if (empty || word == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new WordCard(word, getIndex() + 1).getRoot());
            }
        }
    }

}

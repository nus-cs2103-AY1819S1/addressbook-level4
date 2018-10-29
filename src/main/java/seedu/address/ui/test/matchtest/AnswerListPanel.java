package seedu.address.ui.test.matchtest;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.FlashMatchOutcomeEvent;
import seedu.address.model.card.IndexedAnswer;
import seedu.address.ui.UiPart;

/**
 * The panel which contains all the answers in a particular test.
 */
public class AnswerListPanel extends UiPart<Region> {
    private static final String FXML = "test/matchtest/AnswerListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AnswerListPanel.class);

    @FXML
    private ListView<IndexedAnswer> matchTestAnswerListView;

    public AnswerListPanel(ObservableList<IndexedAnswer> answerList) {
        super(FXML);

        setConnections(answerList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<IndexedAnswer> answerList) {
        matchTestAnswerListView.setItems(answerList);
        matchTestAnswerListView.setCellFactory(listView -> new AnswerListViewCell());
    }

    @Subscribe
    private void handleFlashMatchOutcomeEvent(FlashMatchOutcomeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        matchTestAnswerListView.setCellFactory(listView ->
                new AnswerListViewCell(event.indexOfAnswer, event.isCorrect));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> matchTestAnswerListView.setCellFactory(listView ->
                        new AnswerListViewCell()));
            };
        }, UiPart.FLASH_TIME);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Card} using a {@code AnswerView}.
     */
    class AnswerListViewCell extends ListCell<IndexedAnswer> {
        private Integer actualIndexOfAnswer;
        private boolean isCorrect;

        public AnswerListViewCell() {
            actualIndexOfAnswer = null;
        }

        /**
         * Used for defining which cell to flash, with the boolean whether it is correct.
         * @param actualIndexOfAnswer The targetIndex that is needed to be flashed.
         * @param isCorrect Whether the matching card is correct.
         */
        public AnswerListViewCell(int actualIndexOfAnswer, boolean isCorrect) {
            this.actualIndexOfAnswer = actualIndexOfAnswer;
            this.isCorrect = isCorrect;
        }

        @Override
        protected void updateItem(IndexedAnswer answer, boolean empty) {
            super.updateItem(answer, empty);

            if (empty || answer == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (actualIndexOfAnswer == null || actualIndexOfAnswer != getIndex()) {
                    setGraphic(new AnswerView(answer, answer.getId()).getRoot());
                } else {
                    setGraphic(new AnswerView(answer, answer.getId(), isCorrect).getRoot());
                }
            }
        }
    }
}

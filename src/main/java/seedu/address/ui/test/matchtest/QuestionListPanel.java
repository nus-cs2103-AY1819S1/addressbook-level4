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
import seedu.address.model.card.Question;
import seedu.address.ui.UiPart;

/**
 * The panel which contains all the questions in a particular test.
 */
public class QuestionListPanel extends UiPart<Region> {
    private static final String FXML = "/test/matchtest/QuestionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(QuestionListPanel.class);

    @FXML
    private ListView<Question> matchTestQuestionListView;

    public QuestionListPanel(ObservableList<Question> questionList) {
        super(FXML);
        setConnections(questionList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Question> questionList) {
        this.matchTestQuestionListView.setItems(questionList);
        matchTestQuestionListView.setCellFactory(listView -> new QuestionListViewCell());
    }

    @Subscribe
    private void handleFlashMatchOutcomeEvent(FlashMatchOutcomeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        matchTestQuestionListView.setCellFactory(listView -> new QuestionListViewCell(event.indexOfQuestion,
                event.isCorrect));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() ->
                        matchTestQuestionListView.setCellFactory(listView -> new QuestionListViewCell()));
            };
        }, UiPart.FLASH_TIME);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Card} using a {@code QuestionView}.
     */
    class QuestionListViewCell extends ListCell<Question> {
        private Integer indexOfQuestion;
        private boolean isCorrect;

        public QuestionListViewCell() {
            indexOfQuestion = null;
        }

        /**
         * Used for defining which cell to flash, with the boolean whether it is correct.
         * @param indexOfQuestion The targetIndex that is needed to be flashed.
         * @param isCorrect Whether the matching card is correct.
         */
        public QuestionListViewCell(int indexOfQuestion, boolean isCorrect) {
            this.indexOfQuestion = indexOfQuestion;
            this.isCorrect = isCorrect;
        }

        @Override
        protected void updateItem(Question question, boolean empty) {
            super.updateItem(question, empty);

            if (empty || question == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (indexOfQuestion == null || indexOfQuestion != getIndex()) {
                    setGraphic(new QuestionView(question, getIndex() + 1).getRoot());
                } else {
                    setGraphic(new QuestionView(question, getIndex() + 1, isCorrect).getRoot());
                }
            }
        }
    }
}

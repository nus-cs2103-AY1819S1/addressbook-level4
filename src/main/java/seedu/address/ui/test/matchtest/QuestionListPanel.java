package seedu.address.ui.test.matchtest;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.FlashMatchOutcomeEvent;
import seedu.address.model.card.IndexedQuestion;
import seedu.address.ui.UiPart;

/**
 * The panel which contains all the questions in a particular test.
 */
public class QuestionListPanel extends UiPart<Region> {
    private static final String FXML = "test/matchtest/QuestionListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(QuestionListPanel.class);

    @FXML
    private ListView<IndexedQuestion> matchTestQuestionListView;

    public QuestionListPanel(ObservableList<IndexedQuestion> questionList) {
        super(FXML);
        setConnections(questionList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<IndexedQuestion> questionList) {
        matchTestQuestionListView.setItems(questionList);
        matchTestQuestionListView.setCellFactory(listView -> new QuestionListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Card} using a {@code QuestionView}.
     */
    class QuestionListViewCell extends ListCell<IndexedQuestion> {
        private final Background originalBackground;

        public QuestionListViewCell() {
            registerAsAnEventHandler(this);
            this.originalBackground = this.getBackground();
        }

        @Override
        protected void updateItem(IndexedQuestion question, boolean empty) {
            super.updateItem(question, empty);

            if (empty || question == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new QuestionView(question).getRoot());
            }
        }

        @Subscribe
        private void handleFlashMatchOutcomeEvent(FlashMatchOutcomeEvent event) {
            if (getIndex() == event.indexOfQuestion) {
                if (event.isCorrect) {
                    flashBackgroundColor(this, new Color(0, 1, 0, 1), originalBackground);
                } else {
                    flashBackgroundColor(this, new Color(1, 0, 0, 1), originalBackground);
                }
            }
        }
    }
}

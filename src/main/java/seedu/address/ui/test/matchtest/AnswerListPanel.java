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

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Card} using a {@code AnswerView}.
     */
    class AnswerListViewCell extends ListCell<IndexedAnswer> {
        private final Background originalBackground;

        public AnswerListViewCell() {
            registerAsAnEventHandler(this);
            originalBackground = this.getBackground();
        }


        @Override
        protected void updateItem(IndexedAnswer answer, boolean empty) {
            super.updateItem(answer, empty);

            if (empty || answer == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AnswerView(answer).getRoot());
            }
        }

        @Subscribe
        private void handleFlashMatchOutcomeEvent(FlashMatchOutcomeEvent event) {
            if (getIndex() == event.indexOfAnswer) {
                if (event.isCorrect) {
                    flashBackgroundColor(this, new Color(0, 1, 0, 1), originalBackground);
                } else {
                    flashBackgroundColor(this, new Color(1, 0, 0, 1), originalBackground);
                }
            }
        }
    }
}

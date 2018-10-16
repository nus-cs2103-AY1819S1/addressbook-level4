package seedu.address.ui.test.matchtest;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.test.matchtest.MatchTest;
import seedu.address.ui.test.TriviaTestPage;

/**
 * The page that shows up when the user is in a matching test.
 */
public class MatchTestPage extends TriviaTestPage {
    private static final String FXML = "/test/matchtest/MatchTestPage.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private QuestionListPanel questionListPanel;
    private AnswerListPanel answerListPanel;

    @FXML
    private StackPane matchTestQuestionListPanelPlaceholder;

    @FXML
    private StackPane matchTestAnswerListPanelPlaceholder;

    public MatchTestPage(MatchTest matchTest) {
        super(FXML);

        questionListPanel = new QuestionListPanel(matchTest.getQuestions());
        matchTestQuestionListPanelPlaceholder.getChildren().add(questionListPanel.getRoot());

        answerListPanel = new AnswerListPanel(matchTest.getAnswers());
        matchTestAnswerListPanelPlaceholder.getChildren().add(answerListPanel.getRoot());
        registerAsAnEventHandler(this);
    }
}

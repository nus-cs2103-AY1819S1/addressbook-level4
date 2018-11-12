package guitests.guihandles.triviatest.matchtest;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * A handler for the UI class {@code TriviaTestPlaceholderPage}
 */
public class MatchTestPageHandle extends NodeHandle<Node> {

    public static final String QUESTION_LIST_PLACEHOLDER_ID = "#matchTestQuestionListView";
    public static final String ANSWER_LIST_PLACEHOLDER_ID = "#matchTestAnswerListView";
    public static final String MATCH_TEST_TOPIC_ID = "#matchTestTopicText";
    public static final String MATCH_TEST_DURATION_ID = "#matchTestDurationText";

    private AnswerListPanelHandle answerListPanelHandle;
    private QuestionListPanelHandle questionListPanelHandle;
    private Label matchTestTopic;
    private Label matchTestDuration;

    public MatchTestPageHandle(Node triviaTestPageNode) {
        super(triviaTestPageNode);

        questionListPanelHandle = new QuestionListPanelHandle(getChildNode(QUESTION_LIST_PLACEHOLDER_ID));
        answerListPanelHandle = new AnswerListPanelHandle(getChildNode(ANSWER_LIST_PLACEHOLDER_ID));
        matchTestTopic = getChildNode(MATCH_TEST_TOPIC_ID);
        matchTestDuration = getChildNode(MATCH_TEST_DURATION_ID);
    }

    public AnswerListPanelHandle getAnswerListPanelHandle() {
        return answerListPanelHandle;
    }

    public QuestionListPanelHandle getQuestionListPanelHandle() {
        return questionListPanelHandle;
    }

    public String getMatchTestTopic() {
        return matchTestTopic.getText();
    }

    public String getMatchTestDuration() {
        return matchTestDuration.getText();
    }
}

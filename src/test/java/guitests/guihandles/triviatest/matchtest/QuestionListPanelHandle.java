package guitests.guihandles.triviatest.matchtest;

import java.util.Set;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import seedu.address.model.card.IndexedQuestion;

/**
 * A handler for the UI class {@code QuestionListPanel}
 */
public class QuestionListPanelHandle extends NodeHandle<ListView<IndexedQuestion>> {

    public static final String QUESTION_VIEW_PANE_ID = "#questionViewPane";

    public QuestionListPanelHandle(ListView<IndexedQuestion> questionListPanel) {
        super(questionListPanel);
    }

    /**
     * Returns the card view handle of a question associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected question is currently not in the scene graph.
     */
    public QuestionViewHandle getQuestionViewHandle(int index) {
        return getAllQuestionNodes().stream()
                .map(QuestionViewHandle::new)
                .filter(handle -> handle.equals(getQuestion(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private IndexedQuestion getQuestion(int index) {
        return getRootNode().getItems().get(index);
    }

    public Color getBackgroundColor(int index) {
        Object[] cells = getRootNode().lookupAll(".cell").toArray();
        ListCell cell = (ListCell) cells[index];
        return (Color) cell.getBackground().getFills().get(0).getFill();
    }

    /**
     * Returns all question nodes in the scene graph.
     * Question nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllQuestionNodes() {
        return guiRobot.lookup(QUESTION_VIEW_PANE_ID).queryAll();
    }
}

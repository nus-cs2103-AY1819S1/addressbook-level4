package guitests.guihandles.triviatest.matchtest;

import java.util.Set;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import seedu.address.model.card.IndexedAnswer;

/**
 * A handler for the UI class {@code AnswerListPanel}.
 */
public class AnswerListPanelHandle extends NodeHandle<ListView<IndexedAnswer>> {

    public static final String ANSWER_VIEW_PANE_ID = "#answerViewPane";

    public AnswerListPanelHandle(ListView<IndexedAnswer> answerListPanel) {
        super(answerListPanel);
    }

    /**
     * Returns the card view handle of a answer associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected answer is currently not in the scene graph.
     */
    public AnswerViewHandle getAnswerViewHandle(int index) {
        return getAllAnswerNodes().stream()
                .map(AnswerViewHandle::new)
                .filter(handle -> handle.equals(getAnswer(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private IndexedAnswer getAnswer(int index) {
        return getRootNode().getItems().get(index);
    }

    public Color getBackgroundColor(int index) {
        Object[] cells = getRootNode().lookupAll(".cell").toArray();
        ListCell cell = (ListCell) cells[index];
        return (Color) cell.getBackground().getFills().get(0).getFill();
    }

    /**
     * Returns all answer nodes in the scene graph.
     * Answer nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllAnswerNodes() {
        return guiRobot.lookup(ANSWER_VIEW_PANE_ID).queryAll();
    }
}

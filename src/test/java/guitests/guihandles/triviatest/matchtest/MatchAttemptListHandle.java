package guitests.guihandles.triviatest.matchtest;

import java.util.Set;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.test.matchtest.MatchAttempt;

/**
 * A handler for the UI class {@code MatchAttemptList}
 */
public class MatchAttemptListHandle extends NodeHandle<ListView<MatchAttempt>> {

    public static final String QUESTION_VIEW_PANE_ID = "#matchAttemptViewPane";

    public MatchAttemptListHandle(ListView<MatchAttempt> matchAttemptListView) {
        super(matchAttemptListView);
    }

    /**
     * Returns the attempt view handle of a attempt associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected attempt is currently not in the scene graph.
     */
    public MatchAttemptViewHandle getAttemptViewHandle(int index) {
        return getAllAttemptNodes().stream()
                .map(MatchAttemptViewHandle::new)
                .filter(handle -> handle.equals(getAttempt(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private MatchAttempt getAttempt(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all question nodes in the scene graph.
     * Question nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllAttemptNodes() {
        return guiRobot.lookup(QUESTION_VIEW_PANE_ID).queryAll();
    }
}

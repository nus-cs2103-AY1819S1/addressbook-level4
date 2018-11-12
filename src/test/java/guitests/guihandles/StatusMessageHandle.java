package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * A handler for the {@code StatusMessage} of the UI, including directory and sortType
 */
public class StatusMessageHandle extends NodeHandle<Node> {

    private static final String DIRECTORY_FIELD_ID = "#directory";
    private static final String SORT_TYPE_ID = "#sortType";

    private final Label directory;
    private final Label sortType;

    public StatusMessageHandle(Node statusMessageNode) {
        super(statusMessageNode);
        directory = getChildNode(DIRECTORY_FIELD_ID);
        sortType = getChildNode(SORT_TYPE_ID);
    }

    public Label getDirectory() {
        return directory;
    }

    public Label getSortType() {
        return sortType;
    }
}

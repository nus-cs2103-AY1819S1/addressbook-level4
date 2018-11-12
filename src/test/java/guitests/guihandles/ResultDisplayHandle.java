package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.TextArea;

/**
 * A handler for the {@code ResultDisplay} of the UI
 */
public class ResultDisplayHandle extends NodeHandle<Node> {

    public static final String RESULT_DISPLAY_ID = "#resultDisplay";

    private final TextArea resultDisplay;
    private final StatusMessageHandle statusMessageHandle;

    public ResultDisplayHandle(Node resultDisplayNode) {
        super(resultDisplayNode);
        resultDisplay = getChildNode(RESULT_DISPLAY_ID);
        statusMessageHandle = new StatusMessageHandle(resultDisplayNode.getParent());
    }

    /**
     * Returns the text in the result display.
     */
    public String getText() {
        return resultDisplay.getText();
    }

    /**
     * Returns the text in the currentDirectory in the result display.
     */
    public String getDirectory() {
        return statusMessageHandle.getDirectory().getText();
    }

    /**
     * Returns the text in the sort type.
     */
    public String getSortType() {
        return statusMessageHandle.getSortType().getText();
    }
}

package guitests.guihandles;

import javafx.scene.Node;

/**
 * A handler for the {@code TaskViewPanel} of the UI.
 */
public class TaskViewPanelHandle extends NodeHandle<Node> {
    public static final String TASK_VIEW_PANEL_ID = "#taskViewGridPane";

    public TaskViewPanelHandle(Node taskViewPanelNode) {
        super(taskViewPanelNode);
    }
}

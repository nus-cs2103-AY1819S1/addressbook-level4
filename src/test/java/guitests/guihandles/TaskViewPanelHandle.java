package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * A handler for the {@code TaskViewPanel} of the UI.
 */
public class TaskViewPanelHandle extends NodeHandle<Node> {
    public static final String TASK_VIEW_PANEL_ID = "#taskViewGridPane";

    public TaskViewPanelHandle(Node taskViewPanelNode) {
        super(taskViewPanelNode);
    }
}

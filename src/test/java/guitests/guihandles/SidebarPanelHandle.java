package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.concurrent.Worker;
import javafx.scene.Node;

/**
 * A handler for the {@code SidebarPanel} of the UI.
 */
public class SidebarPanelHandle extends NodeHandle<Node> {

    public static final String SIDEBAR_ID = "#sidebar";

    public SidebarPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);

        /*new GuiRobot().interact(() -> engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.RUNNING) {
                isWebViewLoaded = false;
            } else if (newState == Worker.State.SUCCEEDED) {
                isWebViewLoaded = true;
            }
        }));*/
    }

}

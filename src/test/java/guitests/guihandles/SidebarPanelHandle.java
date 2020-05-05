package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Accordion;

/**
 * A handler for the {@code SidebarPanel} of the UI.
 */
public class SidebarPanelHandle extends NodeHandle<Node> {

    public static final String SIDEBAR_ID = "#sidebar";

    private final Accordion accordion;

    public SidebarPanelHandle(Node sidebarPanelNode) {
        super(sidebarPanelNode);
        accordion = getChildNode("#accordion");
    }

    public String getExpandedPaneName() {
        return accordion.getExpandedPane().getText();
    }
}

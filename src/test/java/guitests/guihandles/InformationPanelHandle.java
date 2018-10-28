package guitests.guihandles;

import javafx.scene.Node;

/**
 * A handler for the {@code InformationPanel} of the UI.
 */
public class InformationPanelHandle extends NodeHandle<Node> {

    public static final String INFORMATION_ID = "#information";

    public InformationPanelHandle(Node informationPanelNode) {
        super(informationPanelNode);
    }
}

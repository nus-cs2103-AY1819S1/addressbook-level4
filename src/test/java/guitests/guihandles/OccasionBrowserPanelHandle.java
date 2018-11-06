package guitests.guihandles;

import javafx.scene.Node;
import seedu.address.model.occasion.Occasion;

public class OccasionBrowserPanelHandle extends NodeHandle<Node> {

    public static final String BROWSER_ID = "#occasionBrowserPanel";

    // The currently selected person whose module and occasion information is displayed
    // on the panel.
    private Occasion lastRememberedOccasion;

    public OccasionBrowserPanelHandle(Node occasionBrowserPanelNode) {
        super(occasionBrowserPanelNode);
    }

    /**
     * Remembers the person whose details have been selected to be displayed on the panel.
     */
    public void rememberOccasion(Occasion occasion) {
        lastRememberedOccasion = occasion;
    }

    /**
     * Gets the person whose details are currently being displayed on the panel.
     */
    public Occasion getLastRememberedOccasion() {
        return lastRememberedOccasion;
    }
}

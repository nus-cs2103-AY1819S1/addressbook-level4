package guitests.guihandles;

import javafx.scene.control.TextArea;

/**
 * a handle encapsulating the TextArea in an ExpandedEntryPanel.
 */
public class ExpandedEntryPanelHandle extends NodeHandle<TextArea> {
    public static final String EXPANDED_ENTRY_PANEL_ID = "#expandedEntryText";

    public ExpandedEntryPanelHandle(TextArea expandedEntryPanelNode) {
        super(expandedEntryPanelNode);
    }

    /**
     * Returns the text in the result display.
     */
    public String getText() {
        return getRootNode().getText();
    }
}

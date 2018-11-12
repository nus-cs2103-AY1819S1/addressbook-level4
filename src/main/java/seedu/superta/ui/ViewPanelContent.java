package seedu.superta.ui;

import java.net.URL;

import javafx.scene.layout.Region;
import seedu.superta.model.ReadOnlySuperTaClient;

// @@author Caephler

/**
 * Abstract class for the bottom right panel.
 */
public abstract class ViewPanelContent extends UiPart<Region> {
    public ViewPanelContent(URL fxmlFileUrl) {
        super(fxmlFileUrl);
    }

    public ViewPanelContent(String fxmlFileName) {
        super(fxmlFileName);
    }

    public ViewPanelContent(URL fxmlFileUrl, Region root) {
        super(fxmlFileUrl, root);
    }

    public ViewPanelContent(String fxmlFileName, Region root) {
        super(fxmlFileName, root);
    }

    /**
     * This method should re-render the component.
     * This is used to update the component when there is a state change.
     */
    public abstract void update(ReadOnlySuperTaClient model);
}

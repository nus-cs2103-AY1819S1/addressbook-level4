package guitests.guihandles;

import javafx.scene.Node;

public class ModuleBrowserPanelHandle extends NodeHandle<Node> {

    public static final String BROWSER_ID = "#moduleBrowserPanel";

    // The currently selected person whose module and occasion information is displayed
    // on the panel.
    private Module lastRememberedModule;

    public ModuleBrowserPanelHandle(Node moduleBrowserPanelNode) {
        super(moduleBrowserPanelNode);
    }

    /**
     * Remembers the person whose details have been selected to be displayed on the panel.
     */
    public void rememberModule(Module module) {
        lastRememberedModule = module;
    }

    /**
     * Gets the person whose details are currently being displayed on the panel.
     */
    public Module getLastRememberedModule() {
        return lastRememberedModule;
    }
}

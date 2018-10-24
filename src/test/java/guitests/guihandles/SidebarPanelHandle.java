package guitests.guihandles;

import java.net.URL;

import javafx.scene.Node;

/**
 * A handler for the {@code SidebarPanel} of the UI.
 */
public class SidebarPanelHandle extends NodeHandle<Node> {

    public static final String SIDEBAR_ID = "#sidebar";

    private boolean isWebViewLoaded = true;

    private URL lastRememberedUrl;

    public SidebarPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);
        /*
        new GuiRobot().interact(() -> engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.RUNNING) {
                isWebViewLoaded = false;
            } else if (newState == Worker.State.SUCCEEDED) {
                isWebViewLoaded = true;
            }
        }));*/
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    //public URL getLoadedUrl() {
        //return WebViewUtil.getLoadedUrl(getChildNode(SIDEBAR_ID));
    //}

    /**
     * Remembers the {@code URL} of the currently loaded page.
     */
    //public void rememberUrl() {
        //lastRememberedUrl = getLoadedUrl();
    //}

    /**
     * Returns true if the current {@code URL} is different from the value remembered by the most recent
     * {@code rememberUrl()} call.
     */
    //public boolean isUrlChanged() {
        //return !lastRememberedUrl.equals(getLoadedUrl());
    //}

    /**
     * Returns true if the browser is done loading a page, or if this browser has yet to load any page.
     */
    //public boolean isLoaded() {
        //return isWebViewLoaded;
    //}
}

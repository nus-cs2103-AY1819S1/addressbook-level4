package guitests.guihandles;

import javafx.scene.web.WebView;

import org.w3c.dom.Document;

/**
 * Helper methods for dealing with {@code Profile View}.
 */
// @@author javenseow
public class ProfileViewUtil {

    /**
     * Returns the {@code htmlString} of the currently loaded profile.
     */
    public static Document getLoadedProfile(WebView webView) {
        return webView.getEngine().getDocument();
    }
}

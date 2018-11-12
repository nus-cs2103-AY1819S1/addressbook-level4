package guitests.guihandles;

import javafx.scene.Node;

/**
 * A handler for the {@code MainDisplay} UI class.
 */
public class MainDisplayHandle extends NodeHandle<Node> {
    public static final String MAIN_DISPLAY_ID = "#displayPagePlaceHolder";
    public static final String HOMEPAGE_PLACEHOLDER_ID = "#homepagePlaceholder";
    public static final String TRIVIA_TEST_PLACEHOLDER_ID = "#triviaTestPlaceholder";

    private HomepageHandle homepageHandle;

    public MainDisplayHandle(Node mainDisplayNode) {
        super(mainDisplayNode);

        homepageHandle = new HomepageHandle(getChildNode(HOMEPAGE_PLACEHOLDER_ID));
    }

    public CardListPanelHandle getCardListPanel() {
        return homepageHandle.getCardListPanel();
    }

    public InfoPanelHandle getInfoPanelHandle() {
        return homepageHandle.getInfoPanelHandle();
    }

    public BrowserPanelHandle getBrowserPanelHandle() {
        return homepageHandle.getBrowserPanelHandle();
    }
}

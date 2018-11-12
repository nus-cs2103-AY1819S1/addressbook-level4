package guitests.guihandles;

import javafx.scene.Node;

/**
 * A handler for the {@code InfoPanel} of the UI.
 */
public class InfoPanelHandle extends NodeHandle<Node> {
    public static final String INFO_ID = "#info";
    public static final String BROWSER_PANEL_ID = "#browserPanelPlaceholder";
    public static final String CARD_INFO_ID = "#cardInfoPanelPlaceholder";
    public static final String IMPORT_HELP_ID = "#importHelpDisplayPlaceholder";

    private final BrowserPanelHandle browserPanel;
    private final CardInfoPanelHandle cardInfoPanel;

    public InfoPanelHandle(Node infoPanelNode) {
        super(infoPanelNode);

        browserPanel = new BrowserPanelHandle(getChildNode(BROWSER_PANEL_ID));
        cardInfoPanel = new CardInfoPanelHandle(getChildNode(CARD_INFO_ID));
    }

    public BrowserPanelHandle getBrowserPanel() {
        return browserPanel;
    }

    public CardInfoPanelHandle getCardInfoPanel() {
        return cardInfoPanel;
    }
}

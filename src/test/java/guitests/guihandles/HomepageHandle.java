package guitests.guihandles;

import javafx.scene.Node;

/**
 * A handler for the UI class {@code Homepage}.
 */
public class HomepageHandle extends NodeHandle<Node> {
    private final CardListPanelHandle cardListPanel;
    private final InfoPanelHandle infoPanelHandle;


    public HomepageHandle(Node homepageNode) {
        super(homepageNode);

        cardListPanel = new CardListPanelHandle(getChildNode(CardListPanelHandle.CARD_LIST_VIEW_ID));
        infoPanelHandle = new InfoPanelHandle(getChildNode(InfoPanelHandle.INFO_ID));
    }

    public CardListPanelHandle getCardListPanel() {
        return cardListPanel;
    }

    public InfoPanelHandle getInfoPanelHandle() {
        return infoPanelHandle;
    }

    public BrowserPanelHandle getBrowserPanelHandle() {
        return infoPanelHandle.getBrowserPanel();
    }
}

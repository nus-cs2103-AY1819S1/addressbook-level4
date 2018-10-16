package seedu.address.ui.home;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.ui.UiPart;

/**
 * The default landing page of the application.
 */
public class Homepage extends UiPart<Region> {
    private static final String FXML = "/home/Homepage.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private BrowserPanel browserPanel;
    private ImportHelpDisplay importHelpDisplay;
    private CardListPanel cardListPanel;

    @FXML
    private StackPane extraInformationPlaceholder;

    @FXML
    private StackPane cardListPanelPlaceholder;

    public Homepage(Logic logic) {
        super(FXML);
        importHelpDisplay = new ImportHelpDisplay();
        extraInformationPlaceholder.getChildren().add(importHelpDisplay.getRoot());
        browserPanel = new BrowserPanel();
        extraInformationPlaceholder.getChildren().add(browserPanel.getRoot()); // overlays importHelpDisplay

        cardListPanel = new CardListPanel(logic.getFilteredCardList());
        cardListPanelPlaceholder.getChildren().add(cardListPanel.getRoot());

        registerAsAnEventHandler(this);
    }

    public CardListPanel getCardListPanel() {
        return cardListPanel;
    }

    public void releaseResources() {
        browserPanel.freeResources();
    }
}

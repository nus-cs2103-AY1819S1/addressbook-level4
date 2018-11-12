package seedu.address.ui.home;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DisplayCardInfoEvent;
import seedu.address.commons.events.ui.DisplayImportHelpEvent;
import seedu.address.commons.events.ui.DisplayResearchPageEvent;
import seedu.address.commons.events.ui.HideCardInfoPanelEvent;
import seedu.address.ui.UiPart;

/**
 * A placeholder to switch between the different kinds of Panels to be display on the InfoPanel.
 */
public class InfoPanel extends UiPart<Region> {
    private static final String FXML = "home/InfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private final BrowserPanel browserPanel;
    private final ImportHelpDisplay importHelpDisplay;
    private final CardInfoPanel cardInfoPanel;

    @FXML
    private StackPane info;

    @FXML
    private StackPane browserPanelPlaceholder;

    @FXML
    private StackPane importHelpDisplayPlaceholder;

    @FXML
    private StackPane cardInfoPanelPlaceholder;

    public InfoPanel() {
        super(FXML);

        browserPanel = new BrowserPanel();
        browserPanelPlaceholder.getChildren().add(browserPanel.getRoot());

        importHelpDisplay = new ImportHelpDisplay();
        importHelpDisplayPlaceholder.getChildren().add(importHelpDisplay.getRoot());

        cardInfoPanel = new CardInfoPanel();
        cardInfoPanelPlaceholder.getChildren().add(cardInfoPanel.getRoot());

        bindNodesVisibilityProperty(info);
        resetToOriginalState();

        registerAsAnEventHandler(this);
    }

    public void freeResources() {
        browserPanel.freeResources();
    }

    /**
     * Hides all the information in the info panel.
     */
    public void resetToOriginalState() {
        info.getChildren().forEach(node -> node.setVisible(false));
    }

    /**
     * Change the given panel to the given UI region.
     */
    private void changeToPanel(Node changeTo) {
        info.getChildren().forEach(node -> {
            if (node.equals(changeTo)) {
                node.setVisible(true);
            } else {
                node.setVisible(false);
            }
        });
    }

    @Subscribe
    private void handleDisplayResearchPageEvent(DisplayResearchPageEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserPanel.loadCardPage(event.getCardToResearch());
        changeToPanel(browserPanelPlaceholder);
    }

    @Subscribe
    private void handleDisplayImportHelpEvent(DisplayImportHelpEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        changeToPanel(importHelpDisplayPlaceholder);
    }

    @Subscribe
    private void handleDisplayCardInfoEvent(DisplayCardInfoEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        cardInfoPanel.loadCardPage(event.getCardToDisplay(), event.getAttemptsByCard());
        changeToPanel(cardInfoPanelPlaceholder);
    }

    @Subscribe
    private void handleHideCardInfoPanelEvent(HideCardInfoPanelEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        cardInfoPanelPlaceholder.setVisible(false);
    }
}

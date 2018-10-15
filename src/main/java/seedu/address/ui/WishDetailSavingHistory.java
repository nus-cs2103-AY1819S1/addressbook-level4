package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.WishPanelSelectionChangedEvent;
import seedu.address.model.wish.Wish;

/**
 * Panel containing the detail of wish.
 */
public class WishDetailSavingHistory extends UiPart<Region> {

    private static final String FXML = "WishDetailSavingHistory.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    public WishDetailSavingHistory() {
        super(FXML);

        registerAsAnEventHandler(this);
    }

    /**
     * Load the page that shows the detail of wish.
     */
    private void loadWishDetails(Wish wish) {
    }

    @Subscribe
    private void handleWishPanelSelectionChangedEvent(WishPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadWishDetails(event.getNewSelection());
    }
}

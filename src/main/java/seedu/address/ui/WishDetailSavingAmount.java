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
public class WishDetailSavingAmount extends UiPart<Region> {

    private static final String FXML = "WishDetailSavingAmount.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label price;

    @FXML
    private Label savedAmount;

    public WishDetailSavingAmount() {
        super(FXML);

        loadDefaultDetails();
        registerAsAnEventHandler(this);
    }

    /**
     * Load the default page.
     */
    public void loadDefaultDetails() {
        price.setText("");
        savedAmount.setText("");
    }

    /**
     * Load the page that shows the detail of wish.
     */
    private void loadWishDetails(Wish wish) {
        savedAmount.setText("$" + wish.getSavedAmount().toString());
        price.setText("/ $" + wish.getPrice().toString());
    }

    @Subscribe
    private void handleWishPanelSelectionChangedEvent(WishPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadWishDetails(event.getNewSelection());
    }
}

package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.WishPanelSelectionChangedEvent;
import seedu.address.model.wish.Wish;

/**
 * Panel containing the detail of wish.
 */
public class WishDetailPanel extends UiPart<Region> {

    private static final String FXML = "WishDetailPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label name;

    @FXML
    private Label price;

    @FXML
    private Label savedAmount;

    @FXML
    private Label url;

    @FXML
    private Label email;

    @FXML
    private Label remark;

    @FXML
    private FlowPane tags;

    public WishDetailPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Load the default page.
     */
    public void loadDefaultPage() {
        name.setText("Click on any wish for details");
        price.setText("");
        savedAmount.setText("");
        url.setText("");
        email.setText("");
        remark.setText("");
    }

    /**
     * Load the page that shows the detail of wish.
     */
    private void loadWishPage(Wish wish) {
        name.setText(wish.getName().fullName);
        savedAmount.setText("Saved: $" + wish.getSavedAmount().toString());
        price.setText("Price: $" + wish.getPrice().toString());
        url.setText("Product URL: " + wish.getUrl().value);
        email.setText("Email(?): " + wish.getEmail().value);
        remark.setText(wish.getRemark().value);
    }

    @Subscribe
    private void handleWishPanelSelectionChangedEvent(WishPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadWishPage(event.getNewSelection());
    }
}

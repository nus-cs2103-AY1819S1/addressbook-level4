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

public class WishDetailPanel extends UiPart<Region> {

    private static final String FXML = "WishDetailPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label name;

    @FXML
    private Label id;

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

    public void loadDefaultPage() {
        id.setText(". ");
        name.setText("Name");
        price.setText("Price");
        savedAmount.setText("$10,000");
        url.setText("url");
        email.setText("Email");
        remark.setText("Remark");
    }

    private void loadWishPage(Wish wish) {
        id.setText(". ");
        name.setText(wish.getName().fullName);
        price.setText(wish.getPrice().toString());
        savedAmount.setText(wish.getSavedAmount().toString());
        url.setText(wish.getUrl().value);
        email.setText(wish.getEmail().value);
        remark.setText(wish.getRemark().value);
    }

    @Subscribe
    private void handleWishPanelSelectionChangedEvent(WishPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadWishPage(event.getNewSelection());
    }
}

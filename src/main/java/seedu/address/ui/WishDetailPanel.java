package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.WishPanelSelectionChangedEvent;
import seedu.address.model.WishTransaction;
import seedu.address.model.wish.Wish;

/**
 * Panel containing the detail of wish.
 */
public class WishDetailPanel extends UiPart<Region> {

    private static final String FXML = "WishDetailPanel.fxml";
    private static final String[] TAG_COLORS = { "red", "yel", "blue", "navy", "ora", "green", "pink", "hot", "pur" };

    private final Logger logger = LogsCenter.getLogger(getClass());

    private WishDetailSavingAmount wishDetailSavingAmount;
    private WishDetailSavingHistory wishDetailSavingHistory;
    private BrowserPanel wishBrowserPanel;

    @FXML
    private StackPane wishSavingAmountPlaceholder;

    @FXML
    private StackPane wishSavingHistoryPlaceholder;

    @FXML
    private StackPane wishBrowserPlaceHolder;

    @FXML
    private Label name;

    @FXML
    private Label remarks;

    @FXML
    private FlowPane tags;

    public WishDetailPanel(WishTransaction wishTransaction) {
        super(FXML);
        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        wishDetailSavingAmount = new WishDetailSavingAmount();
        wishSavingAmountPlaceholder.getChildren().add(wishDetailSavingAmount.getRoot());

        wishDetailSavingHistory = new WishDetailSavingHistory(wishTransaction);
        wishSavingHistoryPlaceholder.getChildren().add(wishDetailSavingHistory.getRoot());

        wishBrowserPanel = new BrowserPanel();
        wishBrowserPlaceHolder.getChildren().add(wishBrowserPanel.getRoot());

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Load the default page.
     */
    public void loadDefaultPage() {
        name.setText("Click on any wish for details");
    }

    /**
     * Load the page that shows the detail of wish.
     */
    private void loadWishPage(Wish wish) {
        name.setText(wish.getName().fullName);
        initTags(wish);

        if (wish.getRemark().toString().equals("")) {
            remarks.setText("Save by: " + wish.getDate().toString());
        } else {
            remarks.setText("Save by: " + wish.getDate().toString() + " - " + wish.getRemark());
        }
    }

    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColorStyleFor(String tagName) {
        return TAG_COLORS[Math.abs(tagName.hashCode()) % TAG_COLORS.length];
    }

    /**
     * Creates the tag labels for {@code wish}.
     */
    private void initTags(Wish wish) {
        tags.getChildren().clear();
        wish.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.getStyleClass().add(getTagColorStyleFor(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    @Subscribe
    private void handleWishPanelSelectionChangedEvent(WishPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadWishPage(event.getNewSelection());
    }
}

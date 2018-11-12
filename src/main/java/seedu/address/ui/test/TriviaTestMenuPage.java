package seedu.address.ui.test;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.UiPart;

/**
 * Will display the UI for the TriviaTest's Menu page.
 */
public class TriviaTestMenuPage extends UiPart<Region> {

    private static final String FXML = "test/TriviaTestMenuPage.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private ImageView matchTestImage;
    @FXML
    private SplitPane testMenuSplitPane;

    public TriviaTestMenuPage() {
        super(FXML);

        matchTestImage.fitWidthProperty().bind(getRoot().widthProperty());
        testMenuSplitPane.lookupAll(".split-pane-divider").stream().forEach(div -> div.setMouseTransparent(true));
    }
}

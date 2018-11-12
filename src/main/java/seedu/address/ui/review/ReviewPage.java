package seedu.address.ui.review;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.ui.CommandBox;
import seedu.address.ui.UiPart;

/**
 * Ui class for the ReviewPage.
 */
public class ReviewPage extends UiPart<Region> {
    private static final String FXML = "review/ReviewPage.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);

    public ReviewPage() {
        super(FXML);
    }
}

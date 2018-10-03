package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the budget information.
 */
public class CategoriesPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(Title.class);
    private static final String FXML = "CategoriesPanel.fxml";

    public CategoriesPanel() {
        super(FXML);
    }
}

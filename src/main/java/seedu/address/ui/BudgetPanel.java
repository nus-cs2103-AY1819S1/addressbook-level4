package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the budget information.
 */
public class BudgetPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(Title.class);
    private static final String FXML = "BudgetPanel.fxml";

    public BudgetPanel () {
        super(FXML);
    }
}

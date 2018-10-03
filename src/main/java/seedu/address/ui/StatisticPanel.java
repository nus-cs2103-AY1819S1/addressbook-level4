package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the statistic information.
 */
public class StatisticPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(Title.class);
    private static final String FXML = "StatisticPanel.fxml";

    public StatisticPanel () {
        super(FXML);
    }
}

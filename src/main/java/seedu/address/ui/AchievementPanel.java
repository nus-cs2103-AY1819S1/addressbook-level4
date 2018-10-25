package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

public class AchievementPanel extends UiPart<Region>{
    private static final Logger logger = LogsCenter.getLogger(AchievementPanel.class);

    private static final String FXML = "AchievementPanel.fxml";

    @FXML
    private Label xpValueLabel;
    @FXML
    private Label LevelValueLabel;
    
    public AchievementPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

}

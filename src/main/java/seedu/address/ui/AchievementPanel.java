package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.achievement.AchievementRecord;
import seedu.address.model.task.Task;

public class AchievementPanel extends UiPart<Region>{
    private static final Logger logger = LogsCenter.getLogger(AchievementPanel.class);

    private static final String FXML = "AchievementPanel.fxml";

    @FXML
    private Label xpValueLabel;
    @FXML
    private Label LevelValueLabel;
    
    public AchievementPanel(SimpleObjectProperty<AchievementRecord> achievements) {
        super(FXML);
        setConnections(achievements);
        registerAsAnEventHandler(this);
    }
    
    public void setConnections(SimpleObjectProperty<AchievementRecord> achievements) {
        achievements.addListener(((observable, oldValue, newValue) -> {
            if (oldValue.getXp().equals(newValue.getXp())) {
                return;
            }
//            xpValueLabel.setText(newValue.getXp);
        }));
    }

}

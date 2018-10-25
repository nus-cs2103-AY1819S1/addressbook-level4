package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AchievementsUpdatedEvent;
import seedu.address.model.achievement.AchievementRecord;

/**
 * A ui for the achievement field that is displayed on top of the status bar.
 */
public class AchievementPanel extends UiPart<Region>{
    
    private static final Logger logger = LogsCenter.getLogger(AchievementPanel.class);

    private static final String FXML = "AchievementPanel.fxml";

    @FXML
    private Label xpValueLabel;
    @FXML
    private Label levelValueLabel;

    public AchievementPanel(AchievementRecord achievements) {
        super(FXML);
        setLabelValues(achievements);
        registerAsAnEventHandler(this);
    }

    public void setLabelValues(AchievementRecord achievements) {
        String xp = Integer.toString(achievements.getXpValue());
        String level = achievements.getLevel().toString();
        xpValueLabel.setText(xp);
        levelValueLabel.setText(level);
    }

    @Subscribe
    public void handleAddressBookChangedEvent(AchievementsUpdatedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setLabelValues(event.data);
    }

}

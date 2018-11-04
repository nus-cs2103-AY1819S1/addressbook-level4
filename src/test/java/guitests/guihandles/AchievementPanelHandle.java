package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * A handler for the {@code AchievementPanel} component of the UI
 */
public class AchievementPanelHandle extends NodeHandle<Node> {
    private static final String XP_FIELD_ID = "#xpValueLabel";
    private static final String LEVEL_FIELD_ID = "#levelValueLabel";
    private static final String TIME_FIELD_ID = "#timeSpanLabel";
    private static final String NUM_TASK_FIELD_ID = "#numTasksLabel";

    private final Label xpLabel;
    private final Label levelLabel;
    private final Label timeSpanLabel;
    private final Label numTaskLabel;

    public AchievementPanelHandle(Node achievementNode) {
        super(achievementNode);

        xpLabel = getChildNode(XP_FIELD_ID);
        levelLabel = getChildNode(LEVEL_FIELD_ID);
        timeSpanLabel = getChildNode(TIME_FIELD_ID);
        numTaskLabel = getChildNode(NUM_TASK_FIELD_ID);
    }

    /**
     * Returns the text on {@code xpValueLabel}.
     */
    public String getXp() {
        return xpLabel.getText();
    }

    /**
     * Returns the text on {@code levelValueLabel}.
     */
    public String getLevel() {
        return levelLabel.getText();
    }

    /**
     * Returns the text on {@code timeSpanLabel}.
     */
    public String getTimeSpan() {
        return timeSpanLabel.getText();
    }

    /**
     * Returns the text on {@code numTaskLabel}.
     */
    public String getNumTask() {
        return numTaskLabel.getText();
    }
}



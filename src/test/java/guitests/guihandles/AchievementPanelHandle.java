package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * A handler for the {@code AchievementPanel} component of the UI
 */
public class AchievementPanelHandle extends NodeHandle<Node> {
    private static final String XP_FIELD_ID = "#xpValueLabel";
    private static final String LEVEL_FIELD_ID = "#levelValueLabel";

    private final Label xpLabel;
    private final Label levelLabel;

    public AchievementPanelHandle(Node achievementNode) {
        super(achievementNode);

        xpLabel = getChildNode(XP_FIELD_ID);
        levelLabel = getChildNode(LEVEL_FIELD_ID);
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
}



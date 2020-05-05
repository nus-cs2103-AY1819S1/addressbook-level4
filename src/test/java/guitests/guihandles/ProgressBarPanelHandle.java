package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.ProgressBar;

/**
 * A handler for the {@code ProgressBarPanel} of the UI.
 */
public class ProgressBarPanelHandle extends NodeHandle<Node> {

    public static final String PROGRESSBAR_ID = "#progressbar";

    private final ProgressBar today;

    private final ProgressBar week;

    public ProgressBarPanelHandle(Node progressBarPanelNode) {
        super(progressBarPanelNode);
        today = getChildNode("#today");
        week = getChildNode("#week");
    }

    public Double getTodayProgress() {
        return today.getProgress();
    }

    public Double getWeekProgress() {
        return week.getProgress();
    }
}

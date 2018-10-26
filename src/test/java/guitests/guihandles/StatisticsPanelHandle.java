package guitests.guihandles;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * A handle to the {@code StatisticsPanel} in the GUI.
 */
public class StatisticsPanelHandle extends NodeHandle<Region> {

    public static final String STATISTIC_PANEL_ID = "#statisticPanel";
    public static final String CHART_AREA_ID = "#chartArea";

    private final StackPane chartArea;

    public StatisticsPanelHandle(Region statisticPanelHandle) {
        super(statisticPanelHandle);

        chartArea = getChildNode(CHART_AREA_ID);
    }

    public boolean isCategoryChart() {
        return chartArea.getChildren().get(0) instanceof PieChart;
    }

    public boolean isNoExpenseText() {
        return chartArea.getChildren().get(0) instanceof Text;
    }

    public boolean isTimeChart() {
        return chartArea.getChildren().get(0) instanceof BarChart;
    }

    public boolean isMatchingText(String matchingText) {
        if (chartArea.getChildren().get(0) instanceof Text){
            Text text = (Text) chartArea.getChildren().get(0);
            return matchingText.equals(text.getText());
        }
        return false;
    }
}

package seedu.address.model.analytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//@@author arsalanc-v2

/**
 * A data structure for statistics.
 * Exposes data to be displayed through convenient methods.
 * Employs the Factory Method design pattern.
 */
public class StatData {

    public static final int NUM_SUMMARY_ELEMENTS = 4;
    public static final int NUM_VISUALIZATION_ELEMENTS = 6;

    // lists are used to indicate ordering
    private List<Tuple<String, Integer>> summaryData;
    private List<VisualizationSeptuple> visualizationData;

    public StatData() {
        summaryData = new ArrayList<>();
        visualizationData = new ArrayList<>();
    }

    private class VisualizationSeptuple<T, U> {
        private String chartTitle;
        private String xTitle;
        private String yTitle;
        private List<T> xLabels;
        private List<U> yLabels;
        private List<Tuple<T, U>> dataPoints;

        private VisualizationSeptuple(String chartTitle, String xTitle, String yTitle, List<T> xLabels, List<U>
            yLabels, List<Tuple<T, U>> dataPoints) {
            this.chartTitle = chartTitle;
            this.xTitle = xTitle;
            this.yTitle = yTitle;
            this.xLabels = xLabels;
            this.yLabels = yLabels;
            this.dataPoints = dataPoints;
        }

        private VisualizationSeptuple(List visualizationElements) {
            assert visualizationElements.size() == NUM_VISUALIZATION_ELEMENTS : "There must be six visualization " +
                "elements";
            this.chartTitle = chartTitle;
            this.xTitle = xTitle;
            this.yTitle = yTitle;
            this.xLabels = xLabels;
            this.yLabels = yLabels;
            this.dataPoints = dataPoints;
        }

        public String getChartTitle() {
            return chartTitle;
        }

        public String getxTitle() {
            return xTitle;
        }

        public String getyTitle() {
            return yTitle;
        }

        public List<T> getxLabels() {
            return xLabels;
        }

        public List<U> getyLabels() {
            return yLabels;
        }

        public List<Tuple<T, U>> getDataPoints() {
            return dataPoints;
        }

    }

    private class Tuple<K, V> {
        private K key;
        private V value;

        private Tuple(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setKey(K newKey) {
            key = newKey;
        }

        public void setValue(V newValue) {
            value = newValue;
        }
    }

    public void setSummaryValues(List<Integer> summaryValues) {
        assert summaryValues.size() == NUM_SUMMARY_ELEMENTS : "There must be four summary elements";

       for (int i = 0; i < 4; i++) {
           summaryData.get(i).setValue(summaryValues.get(i));
       }
    }

    /**
     * factory
     * @param visualizationElements
     */
    public void addVisualization(List visualizationElements) {
        visualizationData.add(new VisualizationSeptuple(visualizationElements));
    }

    public List<Tuple<String, Integer>> getSummaryData() {
        return summaryData;
    }

    public List<VisualizationSeptuple> getVisualizationData() {
        return visualizationData;
    }

    public HashMap<String, List> getAllData() {
        return new HashMap<>() {{
            put("summary", summaryData);
            put("visualization", visualizationData);
        }};
    }
}

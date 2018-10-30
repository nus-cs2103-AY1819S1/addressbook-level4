package seedu.clinicio.model.analytics;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.clinicio.model.analytics.data.SummaryData;
import seedu.clinicio.model.analytics.data.Tuple;
import seedu.clinicio.model.analytics.data.VisualizationData;
import seedu.clinicio.model.appointment.Date;

// @@author arsalanc-v2

/**
 * Represents statistics of a general type.
 */
public abstract class Statistics {

    public static final int NUM_SUMMARY = 1;

    // summary fields
    public final String SUMMARY_TODAY = "Today";
    public final String SUMMARY_WEEK = "This Week";
    public final String SUMMARY_MONTH = "This Month";
    public final String SUMMARY_YEAR = "This Year";

    // protected so subclasses can access these fields
    protected final List<String> DEFAULT_SUMMARY_TEXTS = Arrays.asList(SUMMARY_TODAY, SUMMARY_WEEK, SUMMARY_MONTH,
        SUMMARY_YEAR);
    protected final List<Integer> DEFAULT_SUMMARY_VALUES = Arrays.asList(0, 0, 0, 0);

    protected StatData statData;

    /**
     * Initializes maps to store statistic names as keys and their corresponding values.
     */
    public Statistics() {
        statData = new StatData();
    }

    public void initializeSummaryValues(String summaryTitle, List<String> summaryTexts) {
        statData.initializeSummary(summaryTitle, summaryTexts);
    }

    public abstract void computeSummaryStatistics();
    public abstract void computeVisualizationStatistics();

    /**
     * Assumes values are not negative.
     * @param values
     * @param offset
     * @return
     */
    public Tuple<Integer, Integer> calculateAxisRange(List<Integer> values, int offset) {
        requireNonNull(values);
        // separate assertions for greater clarity in debugging
        assert offset >= 0 : "Offset must not be negative";
        assert offset >= 5 : "Offset must be at least five";

        if (values.isEmpty()) {
            return new Tuple<Integer, Integer>(0, offset);
        }

        int min = values.stream()
            .mapToInt(v -> v)
            .min()
            .orElseGet(() -> 0);
        min = Math.max(0, min - offset);

        int max = values.stream()
            .mapToInt(v -> v)
            .max()
            .orElseGet(() -> offset);
        max = max + offset;

        return new Tuple<Integer, Integer>(min, max);
    }

    /**
     *
     * @param map
     * @return
     */
    public List<Tuple<String, Integer>> getTupleDataPointsCategorical(Map<String, Integer> map) {
        return map.entrySet().stream()
            .map(entry -> new Tuple<String, Integer>(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }

    public List<Tuple<Integer, Integer>> getTupleDataPointsContinuous(Map<Integer, Integer> map) {
        return map.entrySet().stream()
            .map(entry -> new Tuple<Integer, Integer>(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }

    /**
     * Constructs data for a categorical plot for the days of the current week.
     * Returns counts in the order they were provided as inputs.
     * @param datesGroups
     */
    // fill absent days
    public List<List<Tuple<String, Integer>>> overNextWeekData(List<List<Date>>
        datesGroups) {
        assert datesGroups.size() >= 1 : "Invalid groups of dates supplied";

        List<List<Tuple<String, Integer>>> dataGroups = new ArrayList<>();

        for (List<Date> datesGroup : datesGroups) {
            Map<String, Integer> nextWeekDateCounts = DateTimeCount.eachDayOfNextWeek(datesGroup);

            List<Tuple<String, Integer>> dataGroup = nextWeekDateCounts.entrySet().stream()
                .map(entry -> new Tuple<DayOfWeek, Integer>(DayOfWeek.valueOf(entry.getKey()), entry.getValue()))
                .sorted((tuple1, tuple2) -> tuple1.getKey().compareTo(tuple2.getKey()))
                .map(tuple -> new Tuple<String, Integer>(tuple.getKey().name(), tuple.getValue()))
                .collect(Collectors.toList());

            dataGroups.add(dataGroup);
        }

        return dataGroups;
    }

    /**
     * Constructs data for a categorical plot for the days of the current week.
     * @param dates
     */

    // fill absent days
//    public void plotOverCurrentWeek(List<Date> dates, String chartTitle, String yTitle) {
//        Map<String, Integer> xy = DateTimeCount.eachDayOfCurrentWeek(dates);
//
//        List<Tuple<String, Integer>> dataPoints = xy.entrySet().stream()
//            .map(entry -> new Tuple<DayOfWeek, Integer>(DayOfWeek.valueOf(entry.getKey()), entry.getValue()))
//            .sorted((tuple1, tuple2) -> tuple1.getKey().compareTo(tuple2.getKey()))
//            .map(tuple -> new Tuple<String, Integer>(tuple.getKey().getAbbreviatedDay(), tuple.getValue()))
//            .collect(Collectors.toList());
//
//        Tuple<Integer, Integer> yAxisRange = calculateAxisRange(new ArrayList<>(xy.values()), 10);
//        statData.addCategoricalVisualization(ChartType.BAR, chartTitle, "Days of the week", "numbers", new
//            ArrayList<>(xy
//            .keySet()), yAxisRange, dataPoints);
//    }

    /**
     *
     * @param dates
     */
    // categorical vs continuous
    // fill absent months
//    public void plotOverCurrentYear(List<Date> dates, String chartTitle, String yTitle) {
//        Map<String, Integer> xy = DateTimeCount.eachMonthOfCurrentYear(dates);
//
//        List<Tuple<String, Integer>> dataPoints = xy.entrySet().stream()
//            .map(entry -> new Tuple<MonthOfYear, Integer>(MonthOfYear.valueOf(entry.getKey()), entry.getValue()))
//            .sorted((tuple1, tuple2) -> tuple1.getKey().compareTo(tuple2.getKey()))
//            .map(tuple -> new Tuple<String, Integer>(tuple.getKey().getAbbreviatedMonth(), tuple.getValue()))
//            .collect(Collectors.toList());
//
//        Tuple<Integer, Integer> yAxisRange = calculateAxisRange(new ArrayList<>(xy.values()), 10);
//        statData.addCategoricalVisualization(ChartType.BAR, chartTitle, "Months", yTitle, new
//            ArrayList<>(xy
//                .keySet()), yAxisRange, dataPoints);
//    }

    /**
     *
     * @return
     */
    public StatData getAllStatistics() {
        computeSummaryStatistics();
        computeVisualizationStatistics();
        return statData;
    }

    /**
     * @return The HashMap containing all statistics of a type.
     */
    public SummaryData getSummaryData() {
        computeSummaryStatistics();
        return statData.getSummaryData();
    }

    /**
     *
     */
    public List<VisualizationData> getVisualizationData() {
        computeVisualizationStatistics();
        return statData.getVisualizationData();
    }

//    /**
//     * @return The names of the different summary statistics.
//     */
//    public Set<String> getSummaryKeys() {
//        return summaryStatistics.keySet();
//    }
//
//    /**
//     * @return A String consisting of a statistic and its value, each on a separate line.
//     */
//    @Override
//    public String toString() {
//        StringBuilder toDisplay = new StringBuilder();
//        for (Map.Entry<String, Integer> entry : summaryStatistics.entrySet()) {
//            toDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n");
//        }
//
//        for (Map.Entry<String, Integer> entry : visualizationStatistics.entrySet()) {
//            toDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n");
//        }
//
//        return toDisplay.toString();
//    }
}

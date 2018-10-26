package seedu.souschef.model.planner;

import java.util.Comparator;

/**
 * Comparator class for Day which compares Day objects by LocalDate date.
 */
public class DayComparator implements Comparator<Day> {
    @Override
    public int compare(Day o1, Day o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}

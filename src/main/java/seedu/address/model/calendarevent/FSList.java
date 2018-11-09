package seedu.address.model.calendarevent;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * A List that combines SortedList and FilteredList
 *
 * FilteredList wrapped with SortedList
 */
public class FSList {

    private ArrayList<Predicate<CalendarEvent>> predicates;
    private ObservableList<CalendarEvent> calendarEvents;
    private SortedList<CalendarEvent> sortedList;
    private FilteredList<CalendarEvent> filteredList;

    public FSList(ObservableList<CalendarEvent> backingList) {
        predicates = new ArrayList<>();
        calendarEvents = backingList;
        sortedList = new SortedList<>(calendarEvents);
        filteredList = new FilteredList<>(sortedList);
    }

    public FilteredList<CalendarEvent> getFSList() {
        return filteredList;
    }

    /**
     * Set a single predicate for the filtered list
     * @param predicate predicate for filtering
     */
    public void setPredicate(Predicate<CalendarEvent> predicate) {
        clearPredicates();
        addPredicate(predicate);
    }

    /**
     * Clears all predicates so no filtering is done
     */
    public void clearPredicates() {
        predicates.clear();
        filteredList.setPredicate(null);
    }

    /**
     * Add another predicate to the filtered list
     * The added predicate will be applied to the list immediately
     * @param predicate additional predicate to filter
     */
    public void addPredicate(Predicate<CalendarEvent> predicate) {
        predicates.add(predicate);
        filteredList.setPredicate(getCombinedPredicate());
    }

    /**
     * Combines all the predicates in {@code predicates} into a new Predicate that is a logical AND of all predicates
     * @return the combined predicate
     */
    public Predicate<CalendarEvent> getCombinedPredicate() {
        return predicates.stream().reduce(Predicate::and).orElse(null);
    }

    public void setComparator(Comparator<CalendarEvent> comparator) {
        sortedList.setComparator(comparator);
    }

    public void clearComparator() {
        sortedList.setComparator(null);
    }
}

package seedu.address.model.calendarevent;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Filtered-and-Sorted List. A List of {@code CalendarEvents} that combines SortedList and FilteredList.
 * Also allows for multiple predicates to be used for filtering.
 *
 * Implemented using a SortedList wrapped wit a FilteredList.
 */
public class FSList {

    private ArrayList<Predicate<CalendarEvent>> predicates;
    private ObservableList<CalendarEvent> calendarEvents;
    private SortedList<CalendarEvent> sortedList;
    private FilteredList<CalendarEvent> filteredList;

    /**
     * Creates a new FSList wrapped around the backingList.
     * The FSList has no predicates and no comparators.
     *
     * @param backingList list of CalendarEvents to be filtered and sorted.
     */
    public FSList(ObservableList<CalendarEvent> backingList) {
        predicates = new ArrayList<>();
        calendarEvents = backingList;
        sortedList = new SortedList<>(calendarEvents);
        filteredList = new FilteredList<>(sortedList);
    }

    /**
     * @return the filtered-and-sorted list.
     */
    public FilteredList<CalendarEvent> getFSList() {
        return filteredList;
    }

    /**
     * Returns a new FSList wrapping backingList.
     * The new FSList uses the same predicates and comparator as the copied FSList.
     *
     * @param backingList the list to be filted-and-sorted.
     */
    public FSList copy(ObservableList<CalendarEvent> backingList) {
        FSList copy = new FSList(backingList);

        // copy predicates and comparator
        copy.predicates.addAll(this.predicates);
        copy.setComparator(getComparator());
        copy.combineAndApplyPredicates();
        return copy;
    }

    /**
     * Sets the predicate for filtering.
     * Clears all existing predicates from FSList.
     *
     * @param predicate for filtering FSList.
     */
    public void setPredicate(Predicate<CalendarEvent> predicate) {
        clearPredicates();
        addPredicate(predicate);
    }

    /**
     * Add a predicate to the filtered list, on top of existing predicates.
     *
     * @param predicate additional predicate to filter FSList.
     */
    public void addPredicate(Predicate<CalendarEvent> predicate) {
        predicates.add(predicate);
        combineAndApplyPredicates();
    }

    /**
     * Clears all predicates from FSList.
     */
    public void clearPredicates() {
        predicates.clear();
        filteredList.setPredicate(null);
    }

    /**
     * Combines all the predicates in {@code predicates} into a new Predicate that is a logical AND of all predicates.
     *
     * @return the combined predicate.
     */
    private Predicate<CalendarEvent> getCombinedPredicate() {
        return predicates.stream().reduce(Predicate::and).orElse(null);
    }

    /**
     * Convenience method
     */
    private void combineAndApplyPredicates() {
        filteredList.setPredicate(getCombinedPredicate());
    }

    // TODO: fix this warning
    private Comparator<CalendarEvent> getComparator() {
        return (Comparator<CalendarEvent>) sortedList.getComparator();
    }

    /**
     * Set the comparator for sorting the FSList.
     */
    public void setComparator(Comparator<CalendarEvent> comparator) {
        sortedList.setComparator(comparator);
    }

    public void clearComparator() {
        sortedList.setComparator(null);
    }
}

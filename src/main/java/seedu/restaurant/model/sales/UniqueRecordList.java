package seedu.restaurant.model.sales;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.restaurant.model.sales.exceptions.DuplicateRecordException;
import seedu.restaurant.model.sales.exceptions.SalesRecordNotFoundException;

/**
 * A list of records that enforces uniqueness between its elements and does not allow nulls.
 * A record is considered unique by comparing using {@code SalesRecord#isSameRecord(SalesRecord)}. As such, adding and
 * updating of
 * records uses SalesRecord#isSameRecord(SalesRecord) for equality so as to ensure that the record being added or
 * updated is
 * unique in terms of identity in the UniqueRecordList. However, the removal of a record uses SalesRecord#equals
 * (Object) so
 * as to ensure that the record with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see SalesRecord#isSameRecord(SalesRecord)
 */
public class UniqueRecordList implements Iterable<SalesRecord> {

    private final ObservableList<SalesRecord> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent record as the given argument.
     */
    public boolean contains(SalesRecord toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameRecord);
    }

    /**
     * Adds a record to the list.
     * The record must not already exist in the list.
     */
    public void add(SalesRecord toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRecordException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the record {@code target} in the list with {@code editedRecord}.
     * {@code target} must exist in the list.
     * The record identity of {@code editedRecord} must not be the same as another existing record in the list.
     */
    public void setRecord(SalesRecord target, SalesRecord editedRecord) {
        requireAllNonNull(target, editedRecord);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new SalesRecordNotFoundException();
        }

        if (!target.isSameRecord(editedRecord) && contains(editedRecord)) {
            throw new DuplicateRecordException();
        }

        internalList.set(index, editedRecord);
    }

    /**
     * Removes the equivalent record from the list.
     * The record must exist in the list.
     */
    public void remove(SalesRecord toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new SalesRecordNotFoundException();
        }
    }

    public void setRecords(UniqueRecordList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code records}.
     * {@code records} must not contain duplicate records.
     */
    public void setRecords(List<SalesRecord> records) {
        requireAllNonNull(records);
        if (!recordsAreUnique(records)) {
            throw new DuplicateRecordException();
        }

        internalList.setAll(records);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<SalesRecord> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Generates and returns the sales report of the specified date.
     * @param date Date of sales report to generate
     */
    public SalesReport generateSalesReport(Date date) {
        ObservableList<SalesRecord> observableRecordList = FXCollections.observableArrayList();
        for (SalesRecord salesRecord: internalList) {
            if (salesRecord.getDate().equals(date)) {
                observableRecordList.add(salesRecord);
            }
        }
        return new SalesReport(date, FXCollections.unmodifiableObservableList(observableRecordList));
    }

    /**
     * Generates and returns the ranking of existing records' dates according to total revenue as a Map
     */
    public Map<Date, Double> rankDateBasedOnRevenue() {
        Map<Date, Double> dateRevenueMap = computeDateRevenue();
        return sortDateByRevenue(dateRevenueMap);
    }

    /**
     * Computes the total revenue earned for every existing date in the record list
     */
    private Map<Date, Double> computeDateRevenue() {
        Map<Date, Double> dateRevenueMap = new HashMap<>();
        for (SalesRecord s : internalList) {
            Date date = s.getDate();
            if (!dateRevenueMap.containsKey(date)) {
                dateRevenueMap.put(date, 0.0);
            }
            double totalRevenue = dateRevenueMap.remove(date);
            dateRevenueMap.put(date, totalRevenue + s.getRevenue());
        }
        return dateRevenueMap;
    }

    /**
     * Sorts the given {@code dateRevenueMap} by revenue (i.e. its value) in descending order. Uses a LinkedHashMap
     * to preserve insertion order.
     */
    private Map<Date, Double> sortDateByRevenue(Map<Date, Double> dateRevenueMap) {
        List<Entry<Date, Double>> dateRevenueList = new ArrayList<>(dateRevenueMap.entrySet());
        dateRevenueList.sort(Entry.comparingByValue(Comparator.reverseOrder()));

        Map<Date, Double> sortedDateRevenue = new LinkedHashMap<>();
        for (Entry<Date, Double> entry : dateRevenueList) {
            sortedDateRevenue.put(entry.getKey(), entry.getValue());
        }

        return sortedDateRevenue;
    }

    /**
     * Generates and returns the ranking of existing records' items according to total revenue accumulated as a Map
     */
    public Map<ItemName, Double> rankItemBasedOnRevenue() {
        Map<ItemName, Double> itemRevenueMap = computeItemRevenue();
        return sortItemByRevenue(itemRevenueMap);
    }

    /**
     * Computes the total revenue accumulated for every existing item in the record list
     */
    private Map<ItemName, Double> computeItemRevenue() {
        Map<ItemName, Double> itemRevenueMap = new HashMap<>();
        for (SalesRecord s : internalList) {
            ItemName item = s.getName();
            if (!itemRevenueMap.containsKey(item)) {
                itemRevenueMap.put(item, 0.0);
            }
            double totalRevenue = itemRevenueMap.remove(item);
            itemRevenueMap.put(item, totalRevenue + s.getRevenue());
        }
        return itemRevenueMap;
    }

    /**
     * Sorts the given {@code itemRevenueMap} by revenue (i.e. its value) in descending order. Uses a LinkedHashMap
     * to preserve insertion order.
     */
    private Map<ItemName, Double> sortItemByRevenue(Map<ItemName, Double> itemRevenueMap) {
        List<Entry<ItemName, Double>> itemRevenueList = new ArrayList<>(itemRevenueMap.entrySet());
        itemRevenueList.sort(Entry.comparingByValue(Comparator.reverseOrder()));

        Map<ItemName, Double> sortedItemRevenue = new LinkedHashMap<>();
        for (Entry<ItemName, Double> entry : itemRevenueList) {
            sortedItemRevenue.put(entry.getKey(), entry.getValue());
        }

        return sortedItemRevenue;
    }

    /**
     * Returns all dates, each associated with its total revenue for the day, in chronological order.
     */
    public Map<Date, Double> getChronologicalSalesData() {
        Map<Date, Double> salesData = computeDateRevenue();
        return sortInChronologicalOrder(salesData);

    }

    /**
     * Sorts the {@code unsortedData} map chronologically by its key. TreeMap is used as the underlying data
     * structure to maintain natural ordering of dates.
     */
    private Map<Date, Double> sortInChronologicalOrder(Map<Date, Double> unsortedData) {
        Map<Date, Double> sortedData = new TreeMap<>();
        for (Entry<Date, Double> entry : unsortedData.entrySet()) {
            sortedData.put(entry.getKey(), entry.getValue());
        }
        return sortedData;
    }

    @Override
    public Iterator<SalesRecord> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueRecordList // instanceof handles nulls
                && internalList.equals(((UniqueRecordList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code records} contains only unique records.
     */
    private boolean recordsAreUnique(List<SalesRecord> records) {
        for (int i = 0; i < records.size() - 1; i++) {
            for (int j = i + 1; j < records.size(); j++) {
                if (records.get(i).isSameRecord(records.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

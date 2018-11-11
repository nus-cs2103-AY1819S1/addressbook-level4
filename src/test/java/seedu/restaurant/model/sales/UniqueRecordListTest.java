package seedu.restaurant.model.sales;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.restaurant.testutil.sales.TypicalRecords.RECORD_DEFAULT;
import static seedu.restaurant.testutil.sales.TypicalRecords.RECORD_ONE;
import static seedu.restaurant.testutil.sales.TypicalRecords.RECORD_THREE;
import static seedu.restaurant.testutil.sales.TypicalRecords.RECORD_TWO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.restaurant.model.sales.exceptions.DuplicateRecordException;
import seedu.restaurant.model.sales.exceptions.SalesRecordNotFoundException;
import seedu.restaurant.testutil.sales.RecordBuilder;
import seedu.restaurant.testutil.sales.ReportBuilder;

public class UniqueRecordListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final UniqueRecordList uniqueRecordList = new UniqueRecordList();

    @Test
    public void contains_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.contains(null);
    }
    @Test
    public void contains_recordNotInList_returnsFalse() {
        assertFalse(uniqueRecordList.contains(RECORD_DEFAULT));
    }
    @Test
    public void contains_recordInList_returnsTrue() {
        uniqueRecordList.add(RECORD_ONE);
        assertTrue(uniqueRecordList.contains(RECORD_ONE));
    }
    @Test
    public void add_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.add(null);
    }
    @Test
    public void add_duplicateRecord_throwsDuplicateRecordException() {
        uniqueRecordList.add(RECORD_ONE);
        thrown.expect(DuplicateRecordException.class);
        uniqueRecordList.add(RECORD_ONE);
    }
    @Test
    public void setRecord_nullTargetAudience_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecord(null, RECORD_DEFAULT);
    }
    @Test
    public void setRecord_nullEditedRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecord(RECORD_ONE, null);
    }
    @Test
    public void setRecord_targetRecordNotInList_throwsRecordNotFoundException() {
        thrown.expect(SalesRecordNotFoundException.class);
        uniqueRecordList.setRecord(RECORD_THREE, RECORD_DEFAULT);
    }
    @Test
    public void setRecord_editedSameRecord_success() {
        uniqueRecordList.add(RECORD_DEFAULT);
        uniqueRecordList.setRecord(RECORD_DEFAULT, RECORD_DEFAULT);
        UniqueRecordList expectedRecordList = new UniqueRecordList();
        expectedRecordList.add(RECORD_DEFAULT);
        assertEquals(expectedRecordList, uniqueRecordList);
    }
    @Test
    public void setRecord_editedRecordAlreadyExists_throwsDuplicateRecordException() {
        uniqueRecordList.add(RECORD_DEFAULT);
        uniqueRecordList.add(RECORD_ONE);
        thrown.expect(DuplicateRecordException.class);
        uniqueRecordList.setRecord(RECORD_DEFAULT, RECORD_ONE);
    }
    @Test
    public void remove_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.remove(null);
    }
    @Test
    public void remove_recordDoesNotExist_throwsRecordNotFoundException() {
        thrown.expect(SalesRecordNotFoundException.class);
        uniqueRecordList.remove(RECORD_DEFAULT);
    }
    @Test
    public void remove_existingRecord_removesRecord() {
        uniqueRecordList.add(RECORD_DEFAULT);
        uniqueRecordList.remove(RECORD_DEFAULT);
        UniqueRecordList expectedRecordList = new UniqueRecordList();
        assertEquals(expectedRecordList, uniqueRecordList);
    }
    @Test
    public void setRecords_nullUniqueRecordList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecords((UniqueRecordList) null);
    }
    @Test
    public void setRecords_uniqueRecordList_replacesOwnListWithProvidedUniqueRecordList() {
        uniqueRecordList.add(RECORD_DEFAULT);
        UniqueRecordList expectedRecordList = new UniqueRecordList();
        expectedRecordList.add(RECORD_ONE);
        uniqueRecordList.setRecords(expectedRecordList);
        assertEquals(expectedRecordList, uniqueRecordList);
    }
    @Test
    public void setRecords_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRecordList.setRecords((List<SalesRecord>) null);
    }
    @Test
    public void setRecords_list_replacesOwnListWithProvidedList() {
        uniqueRecordList.add(RECORD_DEFAULT);
        List<SalesRecord> records = Collections.singletonList(RECORD_ONE);
        uniqueRecordList.setRecords(records);
        UniqueRecordList expectedRecordList = new UniqueRecordList();
        expectedRecordList.add(RECORD_ONE);
        assertEquals(expectedRecordList, uniqueRecordList);
    }
    @Test
    public void setRecords_listWithDuplicateRecords_throwsDuplicateRecordException() {
        List<SalesRecord> listWithDuplicateRecords = Arrays.asList(RECORD_DEFAULT, RECORD_DEFAULT);
        thrown.expect(DuplicateRecordException.class);
        uniqueRecordList.setRecords(listWithDuplicateRecords);
    }
    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueRecordList.asUnmodifiableObservableList().remove(0);
    }

    @Test
    public void generateSalesReport_returnsSalesReport() {
        uniqueRecordList.add(RECORD_DEFAULT);
        SalesRecord recordWithDefaultDate =
                new RecordBuilder(RECORD_ONE).withDate(RECORD_DEFAULT.getDate().toString()).build();
        uniqueRecordList.add(recordWithDefaultDate);
        uniqueRecordList.add(RECORD_TWO);
        SalesReport report = uniqueRecordList.generateSalesReport(RECORD_DEFAULT.getDate());

        ObservableList<SalesRecord> recordList = FXCollections.observableArrayList();
        recordList.add(RECORD_DEFAULT);
        recordList.add(recordWithDefaultDate);
        assertEquals(new ReportBuilder(RECORD_DEFAULT.getDate(), recordList).build(), report);
    }

    @Test
    public void rankDateBasedOnRevenue() {
        uniqueRecordList.add(new RecordBuilder().withDate("27-08-2018").withQuantitySold("10").withPrice("2").build());
        uniqueRecordList.add(new RecordBuilder().withDate("28-08-2018").withQuantitySold("10").withPrice("20").build());
        uniqueRecordList.add(new RecordBuilder().withDate("26-08-2018").withQuantitySold("10").withPrice("10").build());
        Map<Date, Double> actualRanking = uniqueRecordList.rankDateBasedOnRevenue();
        assertEquals(actualRanking.size(), 3);

        List<Date> expectedDateOrder = new ArrayList<>();
        expectedDateOrder.add(new Date("28-08-2018"));
        expectedDateOrder.add(new Date("26-08-2018"));
        expectedDateOrder.add(new Date("27-08-2018"));
        Iterator i = expectedDateOrder.iterator();

        List<Double> expectedRevenueOrder = new ArrayList<>();
        expectedRevenueOrder.add(200.0);
        expectedRevenueOrder.add(100.0);
        expectedRevenueOrder.add(20.0);
        Iterator r = expectedRevenueOrder.iterator();

        for (Entry<Date, Double> entry : actualRanking.entrySet()) {
            Assertions.assertEquals(i.next(), entry.getKey());
            Assertions.assertEquals(r.next(), entry.getValue());
        }
    }

    @Test
    public void rankItemBasedOnRevenue() {
        uniqueRecordList.add(new RecordBuilder().withName("Fried Rice").withQuantitySold("10").withPrice("2").build());
        uniqueRecordList.add(new RecordBuilder().withName("Lasagna").withQuantitySold("10").withPrice("20").build());
        uniqueRecordList.add(new RecordBuilder().withName("Pasta").withQuantitySold("10").withPrice("10").build());
        Map<ItemName, Double> actualRanking = uniqueRecordList.rankItemBasedOnRevenue();
        assertEquals(actualRanking.size(), 3);

        List<ItemName> expectedDateOrder = new ArrayList<>();
        expectedDateOrder.add(new ItemName("Lasagna"));
        expectedDateOrder.add(new ItemName("Pasta"));
        expectedDateOrder.add(new ItemName("Fried Rice"));
        Iterator i = expectedDateOrder.iterator();

        List<Double> expectedRevenueOrder = new ArrayList<>();
        expectedRevenueOrder.add(200.0);
        expectedRevenueOrder.add(100.0);
        expectedRevenueOrder.add(20.0);
        Iterator r = expectedRevenueOrder.iterator();

        for (Entry<ItemName, Double> entry : actualRanking.entrySet()) {
            Assertions.assertEquals(i.next(), entry.getKey());
            Assertions.assertEquals(r.next(), entry.getValue());
        }
    }

    @Test
    public void getChronologicalSalesData() {
        uniqueRecordList.add(new RecordBuilder().withDate("01-01-2018").withQuantitySold("10").withPrice("10").build());
        uniqueRecordList.add(new RecordBuilder().withDate("12-12-2018").withQuantitySold("20").withPrice("20").build());
        uniqueRecordList.add(new RecordBuilder().withDate("06-06-2018").withQuantitySold("30").withPrice("30").build());
        uniqueRecordList.add(new RecordBuilder().withDate("03-03-2018").withQuantitySold("40").withPrice("40").build());
        uniqueRecordList.add(new RecordBuilder().withDate("09-09-2018").withQuantitySold("50").withPrice("50").build());
        Map<Date, Double> actualData = uniqueRecordList.getChronologicalSalesData();
        assertEquals(actualData.size(), 5);

        List<Date> expectedDateOrder = new ArrayList<>();
        expectedDateOrder.add(new Date("01-01-2018"));
        expectedDateOrder.add(new Date("03-03-2018"));
        expectedDateOrder.add(new Date("06-06-2018"));
        expectedDateOrder.add(new Date("09-09-2018"));
        expectedDateOrder.add(new Date("12-12-2018"));
        Iterator i = expectedDateOrder.iterator();

        List<Double> expectedRevenueOrder = new ArrayList<>();
        expectedRevenueOrder.add(100.0);
        expectedRevenueOrder.add(1600.0);
        expectedRevenueOrder.add(900.0);
        expectedRevenueOrder.add(2500.0);
        expectedRevenueOrder.add(400.0);
        Iterator r = expectedRevenueOrder.iterator();

        for (Entry<Date, Double> entry : actualData.entrySet()) {
            Assertions.assertEquals(i.next(), entry.getKey());
            Assertions.assertEquals(r.next(), entry.getValue());
        }
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        uniqueRecordList.add(RECORD_DEFAULT);
        assertTrue(uniqueRecordList.equals(uniqueRecordList));
    }
    @Test
    public void equals_sameList_returnsTrue() {
        uniqueRecordList.add(RECORD_DEFAULT);
        uniqueRecordList.add(RECORD_THREE);
        UniqueRecordList uniqueRecordListCopy = new UniqueRecordList();
        uniqueRecordListCopy.add(RECORD_DEFAULT);
        uniqueRecordListCopy.add(RECORD_THREE);
        assertTrue(uniqueRecordList.equals(uniqueRecordListCopy));
    }
    @Test
    public void equals_differentType_returnsFalse() {
        uniqueRecordList.add(RECORD_DEFAULT);
        assertFalse(uniqueRecordList.equals(2));
    }
    @Test
    public void equals_differentList_returnsFalse() {
        uniqueRecordList.add(RECORD_DEFAULT);
        UniqueRecordList uniqueRecordListCopy = new UniqueRecordList();
        uniqueRecordListCopy.add(RECORD_THREE);
        assertFalse(uniqueRecordList.equals(uniqueRecordListCopy));
    }
}

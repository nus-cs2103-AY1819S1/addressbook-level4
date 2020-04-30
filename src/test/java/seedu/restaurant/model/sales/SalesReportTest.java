package seedu.restaurant.model.sales;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_DATE_RECORD_TWO;
import static seedu.restaurant.testutil.sales.TypicalRecords.RECORD_DEFAULT;
import static seedu.restaurant.testutil.sales.TypicalRecords.RECORD_ONE;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.restaurant.testutil.sales.RecordBuilder;
import seedu.restaurant.testutil.sales.ReportBuilder;

//@@author HyperionNKJ
public class SalesReportTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {
        // same values -> returns true
        SalesReport report = new ReportBuilder().build();
        SalesReport reportCopy = new ReportBuilder().build();
        assertTrue(report.equals(reportCopy));

        // same object -> returns true
        assertTrue(report.equals(report));

        // null -> returns false
        assertFalse(report.equals(null));

        // different type -> returns false
        assertFalse(report.equals(5));

        SalesRecord record1 = new RecordBuilder(RECORD_DEFAULT).withDate(VALID_DATE_RECORD_TWO).build();
        SalesRecord record2 = new RecordBuilder(RECORD_ONE).withDate(VALID_DATE_RECORD_TWO).build();
        List<SalesRecord> records = List.of(record1, record2);
        SalesReport differentReport = new ReportBuilder(new Date(VALID_DATE_RECORD_TWO), records).build();

        // different report -> returns false
        assertFalse(report.equals(differentReport));

        // different date -> returns false
        SalesReport editedReport = new ReportBuilder().withDate(VALID_DATE_RECORD_TWO).build();
        assertFalse(report.equals(editedReport));

        // different record list -> returns false
        editedReport = new ReportBuilder().withRecords(records).build();
        assertFalse(report.equals(editedReport));
    }
}

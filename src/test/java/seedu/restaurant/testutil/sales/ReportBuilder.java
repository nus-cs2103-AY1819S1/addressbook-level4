package seedu.restaurant.testutil.sales;

import static seedu.restaurant.testutil.sales.TypicalRecords.RECORD_DEFAULT;
import static seedu.restaurant.testutil.sales.TypicalRecords.RECORD_ONE;

import java.util.List;

import seedu.restaurant.model.sales.Date;
import seedu.restaurant.model.sales.SalesRecord;
import seedu.restaurant.model.sales.SalesReport;

//@@author HyperionNKJ
/**
 * A utility class to help with building SalesReport objects.
 */
public class ReportBuilder {
    private static final String DEFAULT_REPORT_DATE = "02-02-2018";
    private static final List<SalesRecord> DEFAULT_REPORT_RECORDS = List.of(RECORD_ONE,
            new RecordBuilder(RECORD_DEFAULT).withDate(RECORD_ONE.getDate().toString()).build());

    private Date date;
    private List<SalesRecord> records;
    private double totalRevenue;

    /**
     * Initializes the ReportBuilder with default data.
     */
    public ReportBuilder() {
        date = new Date(DEFAULT_REPORT_DATE);
        records = DEFAULT_REPORT_RECORDS;
        computeTotalRevenue();
    }

    /**
     * Initializes the ReportBuilder with the data of {@code reportToCopy}.
     */
    public ReportBuilder(SalesReport reportToCopy) {
        date = reportToCopy.getDate();
        records = reportToCopy.getRecords();
        totalRevenue = reportToCopy.getTotalRevenue();
    }

    /**
     * Initializes the ReportBuilder with the given {@code date} and {@code records}.
     */
    public ReportBuilder(Date date, List<SalesRecord> records) {
        this.date = date;
        this.records = records;
        computeTotalRevenue();
    }

    /**
     * Computes the total revenue for the day using each SalesRecord's revenue
     */
    private void computeTotalRevenue() {
        double total = 0;
        for (SalesRecord s: records) {
            total += s.getRevenue();
        }
        totalRevenue = total;
    }

    /**
     * Sets the {@code Date} of the {@code SalesReport} that we are building.
     */
    public ReportBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code List<SalesRecord>} of the {@code SalesReport} that we are building.
     */
    public ReportBuilder withRecords(List<SalesRecord> records) {
        this.records = records;
        return this;
    }

    public SalesReport build() {
        return new SalesReport(date, records);
    }
}

package seedu.address.model.analytics;

/**
 *
 */
public enum MonthOfYear {
    JANUARY("Jan"),
    FEBRUARY("Feb"),
    MARCH("Mar"),
    APRIL("Apr"),
    MAY("May"),
    JUNE("Jun"),
    JULY("Jul"),
    AUGUST("Aug"),
    SEPTEMBER("Sep"),
    OCTOBER("Oct"),
    NOVEMBER("Nov"),
    DECEMBER("Dec");

    private final String abbreviatedMonth;

    MonthOfYear(String abbreviatedMonth) {
        this.abbreviatedMonth = abbreviatedMonth;
    }

    /**
     *
     * @return
     */
    public String getAbbreviatedMonth() {
        return abbreviatedMonth;
    }
}

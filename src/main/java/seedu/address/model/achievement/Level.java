package seedu.address.model.achievement;

/**
 * Represent the level of achievement in reached by the user.
 * There are only 5 valid levels, LEVEL_1 to LEVEL_5.
 */
public enum Level {
    LEVEL_1("lvl.1"), LEVEL_2("lvl.2"), LEVEL_3("lvl.3"), LEVEL_4("lvl.4"), LEVEL_5("lvl.5");

    public static final String MESSAGE_LEVEL_CONSTRAINTS =
            "Level should only have the value lvl.n, where n is integer from 1 to 5";
    private String levelValue;

    /**
     * Constructs a {@code Level}.
     *
     * @param levelValue A valid statusValue.
     */
    Level(String levelValue) {
        this.levelValue = levelValue;
    }

//    /**
//     * Returns true if a given string is a valid Status value.
//     */
//    public static boolean isValidStatus(String value) {
//        try {
//            return value.equals("IN PROGRESS") || value.equals("FINISHED") || value.equals("OVERDUE");
//        } catch (NullPointerException ex) {
//            return false;
//        }
//    }
//
//    /**
//     * Returns the corresponding status object of {@param value}.
//     */
//    public static Status getStatusFromValue(String value) {
//        if (!isValidStatus(value)) {
//            throw new IllegalArgumentException();
//        }
//        return value.equals("IN PROGRESS") ? Status.IN_PROGRESS : Status.valueOf(value);
//    }

    @Override
    public String toString() {
        return levelValue;
    }
}
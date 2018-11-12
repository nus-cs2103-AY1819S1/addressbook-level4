package seedu.address.model.test;

/**
 * Represents all the different kinds of test in 3VIA.
 */
public enum TestType {
    MATCH_TEST, OPEN_ENDED_TEST;

    public static final String MESSAGE_TEST_TYPE_CONSTRAINTS = "TestType should belong to one of the defined "
            + "TestType enum.";
}

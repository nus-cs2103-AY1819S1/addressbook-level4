package seedu.modsuni.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.modsuni.model.user.User;
import seedu.modsuni.model.user.student.Student;

/**
 * A utility class containing a list of {@code User} objects to be used in
 * tests.
 */
public class TypicalUsers {

    public static final String STUDENT_TEST_DATA = "test_8DB6604.xml";
    public static final String STUDENT_MAX_DATA = "max33.xml";

    public static final Student STUDENT_MAX = new StudentBuilder().build();
    public static final Student STUDENT_SEB = new StudentBuilder()
        .withName("Sebestian Vettel")
        .withUsername("vettel5")
        .withEnrollmentDate("17/06/2007")
        .withMajor(Arrays.asList("CS", "BA"))
        .withMinor(Arrays.asList("IS", "MA")).build();
    public static final Student STUDENT_TEST = new StudentBuilder()
            .withName("Test").build();

    private TypicalUsers() {
    }

    /**
     * Returns a {@code List<User>} with all the typical users.
     */
    public List<User> getTypicalUsers() {
        return new ArrayList<>(Arrays.asList(STUDENT_MAX, STUDENT_SEB));
    }
}

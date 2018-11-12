package seedu.superta.testutil;

import static seedu.superta.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.superta.model.SuperTaClient;
import seedu.superta.model.student.Student;

/**
 * A utility class containing a list of {@code Student} objects,
 * a {@code TutorialGroup} with an {@code Assignment} to be used in tests.
 */
public class TypicalSuperTaClient {

    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
        .withEmail("alice@example.com")
        .withPhone("94351253")
        .withStudentId("A0166733Y")
        .withTags("friends").build();
    public static final Student BENSON = new StudentBuilder().withName("Benson Meier")
        .withEmail("johnd@example.com").withPhone("98765432")
        .withStudentId("A1234567Y")
        .withTags("owesMoney", "friends").build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz").withPhone("95352563")
        .withEmail("heinz@example.com").withStudentId("A1245563Y").build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier").withPhone("87652533")
        .withEmail("cornelia@example.com").withStudentId("A0184123Y").withTags(
            "friends").build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer").withPhone("9482224")
        .withEmail("werner@example.com").withStudentId("A1403293Y").build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz").withPhone("9482427")
        .withEmail("lydia@example.com").withStudentId("A1820123Y")
                    .withFeedback("A little dumb.", "Is attentive during class.").build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best").withPhone("9482442")
        .withEmail("anna@example.com").withStudentId("A1309482Y").build();

    // Manually added
    public static final Student HOON = new StudentBuilder().withName("Hoon Meier").withPhone("8482424")
        .withEmail("stefan@example.com").withStudentId("A1234528Y").build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller").withPhone("8482131")
        .withEmail("hans@example.com").withStudentId("A1234529Y").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
        .withStudentId(VALID_STUDENT_ID_AMY)
        .withEmail(VALID_EMAIL_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Student BOB = new StudentBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
        .withStudentId(VALID_STUDENT_ID_BOB)
        .withEmail(VALID_EMAIL_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
        .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalSuperTaClient() {
    } // prevents instantiation

    /**
     * Returns an {@code SuperTaClient} with all the typical students.
     */
    public static SuperTaClient getTypicalSuperTaClient() {
        SuperTaClient ab = new SuperTaClient();
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }

        return ab;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}

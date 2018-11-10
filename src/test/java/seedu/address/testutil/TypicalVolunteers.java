package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DRIVER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_STUDENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.volunteer.Volunteer;

/**
 * A utility class containing a list of {@code Volunteer} objects to be used in tests.
 */

public class TypicalVolunteers {

    public static final Volunteer ALICE = new VolunteerBuilder()
            .withName("Alice Pauline").withVolunteerId("S9383064D")
            .withGender("f").withBirthday("01-02-1993")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("student").build();
    public static final Volunteer BENSON = new VolunteerBuilder()
            .withName("Benson Meier").withVolunteerId("F8859919P")
            .withGender("m").withBirthday("29-09-1988")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("driver", "student").build();
    public static final Volunteer CARL = new VolunteerBuilder()
            .withName("Carl Kurz").withVolunteerId("S9061638B").withGender("m")
            .withBirthday("27-05-1990").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Volunteer DANIEL = new VolunteerBuilder()
            .withName("Daniel Meier").withVolunteerId("T0124775H").withGender("m")
            .withBirthday("31-10-2001").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("student").build();
    public static final Volunteer ELLE = new VolunteerBuilder()
            .withName("Elle Meyer").withVolunteerId("G0090865M").withGender("f")
            .withBirthday("13-11-2000").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Volunteer FIONA = new VolunteerBuilder()
            .withName("Fiona Kunz").withVolunteerId("S9544754F").withGender("f")
            .withBirthday("12-01-1995").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Volunteer GEORGE = new VolunteerBuilder()
            .withName("George Best").withVolunteerId("S9501647B").withGender("m")
            .withBirthday("14-01-1995").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Volunteer HOON = new VolunteerBuilder()
            .withName("Hoon Meier").withVolunteerId("S9603718Z").withGender("m")
            .withBirthday("03-04-1996").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Volunteer IDA = new VolunteerBuilder()
            .withName("Ida Mueller").withVolunteerId("F9678805M").withGender("f")
            .withBirthday("15-04-1996").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Volunteer's details found in {@code CommandTestUtil}
    public static final Volunteer AMY = new VolunteerBuilder()
            .withName(VALID_NAME_AMY).withVolunteerId(VALID_ID_AMY).withGender(VALID_GENDER_AMY)
            .withBirthday(VALID_BIRTHDAY_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_STUDENT).build();
    public static final Volunteer BOB = new VolunteerBuilder()
            .withName(VALID_NAME_BOB).withVolunteerId(VALID_ID_BOB).withGender(VALID_GENDER_BOB)
            .withBirthday(VALID_BIRTHDAY_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_DRIVER, VALID_TAG_STUDENT).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalVolunteers() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical volunteers.
     */
    public static AddressBook getTypicalVolunteerAddressBook() {
        AddressBook ab = new AddressBook();
        for (Volunteer volunteer : getTypicalVolunteers()) {
            ab.addVolunteer(volunteer);
        }
        return ab;
    }

    public static List<Volunteer> getTypicalVolunteers() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}

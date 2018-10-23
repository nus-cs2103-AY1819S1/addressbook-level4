package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_TAG_DRIVER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_TAG_STUDENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.volunteer.Volunteer;

/**
 * A utility class containing a list of {@code Volunteer} objects to be used in tests.
 */

public class TypicalVolunteers {

    public static final Volunteer ALICE = new VolunteerBuilder().withVolunteerId(1)
            .withName("Alice Pauline")
            .withGender("f").withBirthday("21-08-1986")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("student").build();
    public static final Volunteer BENSON = new VolunteerBuilder().withVolunteerId(2)
            .withName("Benson Meier")
            .withGender("m").withBirthday("29-09-1988")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("driver", "student").build();
    public static final Volunteer CARL = new VolunteerBuilder().withVolunteerId(3)
            .withName("Carl Kurz").withGender("m")
            .withBirthday("27-05-1990").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Volunteer DANIEL = new VolunteerBuilder().withVolunteerId(4)
            .withName("Daniel Meier").withGender("m")
            .withBirthday("31-10-1993").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("student").build();
    public static final Volunteer ELLE = new VolunteerBuilder().withVolunteerId(5)
            .withName("Elle Meyer").withGender("f")
            .withBirthday("13-11-1994").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Volunteer FIONA = new VolunteerBuilder().withVolunteerId(6)
            .withName("Fiona Kunz").withGender("f")
            .withBirthday("12-01-1995").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Volunteer GEORGE = new VolunteerBuilder().withVolunteerId(7)
            .withName("George Best").withGender("m")
            .withBirthday("14-01-1995").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Volunteer HOON = new VolunteerBuilder()
            .withVolunteerId(8)
            .withName("Hoon Meier").withGender("m")
            .withBirthday("03-04-1996").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Volunteer IDA = new VolunteerBuilder()
            .withVolunteerId(9)
            .withName("Ida Mueller").withGender("f")
            .withBirthday("15-04-1996").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Volunteer's details found in {@code CommandTestUtil}
    public static final Volunteer AMY = new VolunteerBuilder()
            .withVolunteerId(10)
            .withName(VALID_VOLUNTEER_NAME_AMY).withGender(VALID_GENDER_AMY)
            .withBirthday(VALID_BIRTHDAY_AMY).withPhone(VALID_VOLUNTEER_PHONE_AMY)
            .withEmail(VALID_VOLUNTEER_EMAIL_AMY).withAddress(VALID_VOLUNTEER_ADDRESS_AMY)
            .withTags(VALID_VOLUNTEER_TAG_STUDENT).build();
    public static final Volunteer BOB = new VolunteerBuilder()
            .withVolunteerId(11)
            .withName(VALID_VOLUNTEER_NAME_BOB).withGender(VALID_GENDER_BOB)
            .withBirthday(VALID_BIRTHDAY_BOB).withPhone(VALID_VOLUNTEER_PHONE_BOB)
            .withEmail(VALID_VOLUNTEER_EMAIL_BOB).withAddress(VALID_VOLUNTEER_ADDRESS_BOB)
            .withTags(VALID_VOLUNTEER_TAG_DRIVER, VALID_VOLUNTEER_TAG_STUDENT).build();

    public static final String KEYWORD_VOLUNTEER_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

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

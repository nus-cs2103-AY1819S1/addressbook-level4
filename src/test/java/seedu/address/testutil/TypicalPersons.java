package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHOOL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withRoom("B314").withEmail("alice@example.com")
            .withPhone("94351253").withSchool("FoS")
        .withTags("Basketball").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withRoom("D111").withSchool("Medicine")
            .withEmail("johnd@example.com").withPhone("98765432")
        .withTags("Soccer", "Floorball").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withRoom("A421").withSchool("SDE")
        .withTags("Floorball", "Handball").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withRoom("C120").withSchool("Biz").withTags("soccer")
        .withTags("Floorball", "Handball").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withRoom("B316").withSchool("Engine").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withRoom("E321").withSchool("Soc").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withRoom("B420").withSchool("Engine").build();

    // Manually added Person
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withRoom("B110").withSchool("Biz").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withRoom("D210").withSchool("Law").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withRoom(VALID_ROOM_AMY).withSchool(VALID_SCHOOL_AMY).withTags(VALID_TAG_AMY)
            .build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withRoom(VALID_ROOM_BOB).withSchool(VALID_SCHOOL_BOB).withTags(VALID_TAG_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER
    public static final String KEYWORD_MATCHING_D111 = "D111"; // A keyword that matches D111
    public static final String KEYWORD_MATCHING_ENGINE = "Engine"; // A keyword that matches Engine

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}

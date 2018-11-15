package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_CATHY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_CATHY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ICNUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ICNUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ICNUMBER_CATHY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CATHY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_CATHY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Patient;

/**
 * A utility class containing a list of {@code Patient} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Patient ALICE = new PersonBuilder().withName("Alice Pauline").withIcNumber("S9015018I")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends")
            .withMedicalRecord("").build();
    public static final Patient BENSON = new PersonBuilder().withName("Benson Meier").withIcNumber("S0473586B")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends")
            .withMedicalRecord("").build();
    public static final Patient CARL = new PersonBuilder().withName("Carl Kurz").withIcNumber("S1608919B")
            .withPhone("95352563").withEmail("heinz@example.com").withAddress("wall street")
            .withMedicalRecord("").build();
    public static final Patient DANIEL = new PersonBuilder().withName("Daniel Meier").withIcNumber("S1556326E")
            .withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends")
            .withMedicalRecord("").build();
    public static final Patient ELLE = new PersonBuilder().withName("Elle Meyer").withIcNumber("S2231362B")
            .withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withMedicalRecord("").build();
    public static final Patient FIONA = new PersonBuilder().withName("Fiona Kunz").withIcNumber("S4965377H")
            .withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withMedicalRecord("").build();
    public static final Patient GEORGE = new PersonBuilder().withName("George Best").withIcNumber("S4463361B")
            .withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withMedicalRecord("").build();

    // Manually added
    public static final Patient HOON = new PersonBuilder().withName("Hoon Meier").withIcNumber("S3367711A")
            .withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Patient IDA = new PersonBuilder().withName("Ida Mueller").withIcNumber("S4630270B")
            .withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();
    public static final Patient TYPO_IN_NAME_ALICE = new PersonBuilder()
            .withName("Alice       Pauline")
            .withIcNumber("S2234567X")
            .withAddress("124, Jurong West Ave 6, #08-211").withEmail("typoalice@example.com")
            .withPhone("99351253")
            .withTags("friendly")
            .withMedicalRecord("").build();

    // Manually added - Patient's details found in {@code CommandTestUtil}
    public static final Patient AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withIcNumber(VALID_ICNUMBER_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Patient BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withIcNumber(VALID_ICNUMBER_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();
    public static final Patient CATHY = new PersonBuilder().withName(VALID_NAME_CATHY)
            .withIcNumber(VALID_ICNUMBER_CATHY).withPhone(VALID_PHONE_CATHY)
            .withEmail(VALID_EMAIL_CATHY).withAddress(VALID_ADDRESS_CATHY).build();
    public static final Patient MIX_N_MATCH = new PersonBuilder().withName(VALID_NAME_BOB)
            .withIcNumber(VALID_ICNUMBER_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();



    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Patient patient : getTypicalPersons()) {
            patient.leaveQueue();
            ab.addPerson(patient);
        }
        return ab;
    }

    public static List<Patient> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Patient> getSamplePersonsLinkedList() {
        return new LinkedList<>(Arrays.asList(ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Patient> getSamplePersonsArrayList() {
        return new ArrayList<>(Arrays.asList(ALICE, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}


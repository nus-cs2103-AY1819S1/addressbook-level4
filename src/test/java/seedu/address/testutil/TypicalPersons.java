package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_OASIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.ArchiveList;
import seedu.address.model.permission.Permission;
import seedu.address.model.permission.PermissionSet;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withSalary("10000").withEmail("alice@example.com")
            .withPhone("94351253").withUsername("Alice Pauline").withPassword("Ap12345678", SaltList.ALICE_SALT)
            .withPermissionSet(new PermissionSet(Permission.DELETE_EMPLOYEE))
            .build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("123, Jurong West Ave 6, #08-111").withSalary("10000")
            .withEmail("johnd@example.com").withPhone("98765432").withUsername("Benson Meier")
            .withPassword("Bm12345678", SaltList.BENSON_SALT)
            .withPermissionSet(new PermissionSet(PermissionSet.PresetPermission.ADMIN))
            .withLeaveApplications(Arrays.asList(TypicalLeaveApplications.BENSON_LEAVE))
            .build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("123, Jurong West Ave 6, #08-111").withSalary("10000")
            .withUsername("Carl Kurz").withPassword("P@55w0rd", SaltList.CARL_SALT).build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("123, Jurong West Ave 6, #08-111").withSalary("10000")
            .withProjects("OASIS").withUsername("Daniel Meier").withPassword("Pa55w0rd", SaltList.DANIEL_SALT).build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("123, Jurong West Ave 6, #08-111").withSalary("10000")
            .withUsername("Elle Meyer").withPassword("ElleI5gr8", SaltList.ELLE_SALT).build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("123, Jurong West Ave 6, #08-111").withSalary("10000")
            .withUsername("Fiona Kunz").withPassword("FionaI5H0t", SaltList.FIONA_SALT).build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("123, Jurong West Ave 6, #08-111").withSalary("10000")
            .withUsername("George Best").withPassword("Pa55w0rd", SaltList.GEORGE_SALT)
            .withLeaveApplications(Arrays.asList(TypicalLeaveApplications.ALICE_LEAVE)).build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("4th street").withSalary("10000")
            .withUsername("Hoon Meier").withPassword("Pa55w0rd").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("4th street").withSalary("10000")
            .withUsername("Ida Mueller").withPassword("Pa55w0rd").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withSalary("10000")
            .withProjects(VALID_PROJECT_OASIS).withUsername(VALID_USERNAME_AMY).withPassword(VALID_PASSWORD_AMY)
            .build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSalary("12000")
            .withProjects(VALID_PROJECT_OASIS).withUsername(VALID_USERNAME_BOB).withPassword(VALID_PASSWORD_BOB)
            .build();

    public static final Person JORDAN = new PersonBuilder().withName("Jordan Tan").withPhone("82829392")
            .withEmail("Jordan@example.com").withAddress("Highroad").withSalary("12000")
            .withProjects(VALID_PROJECT_OASIS).withUsername("Jordan Tan").withPassword("Pa55w0rd")
            .build();
    public static final Person BARRY = new PersonBuilder().withName("Barry Lim").withPhone("82738273")
            .withEmail("Barry@example.com").withAddress("Highstreet").withSalary("12000")
            .withProjects(VALID_PROJECT_OASIS).withUsername("Barry Lim").withPassword("Pa55w0rd")
            .build();
    public static final Person HARRY = new PersonBuilder().withName("Harry Tan").withPhone("82829392")
            .withEmail("J"
                    + "Harry@example.com").withAddress("Highroad").withSalary("12000")
            .withProjects(VALID_PROJECT_OASIS).withUsername("Harry Tan").withPassword("Pa55w0rd")
            .build();
    public static final Person SIMON = new PersonBuilder().withName("Simon Lim").withPhone("82738273")
            .withEmail("Simon@example.com").withAddress("Highstreet").withSalary("12000")
            .withProjects(VALID_PROJECT_OASIS).withUsername("Simon Lim").withPassword("Pa55w0rd")
            .build();
    public static final Person MARY = new PersonBuilder().withName("Mary Tan").withPhone("82829392")
            .withEmail("Mary@example.com").withAddress("Highroad").withSalary("12000")
            .withProjects(VALID_PROJECT_OASIS).withUsername("Mary Tan").withPassword("Pa55w0rd")
            .build();
    public static final Person JACK = new PersonBuilder().withName("Jack Lim").withPhone("82738273")
            .withEmail("Jack@example.com").withAddress("Highstreet").withSalary("12000")
            .withProjects(VALID_PROJECT_OASIS).withUsername("Jack Lim").withPassword("Pa55w0rd")
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {
    } // prevents instantiation

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

    /**
     * Returns an {@code ArchiveList} with all the typical persons.
     */
    public static ArchiveList getTypicalArchiveList() {
        ArchiveList al = new ArchiveList();
        for (Person person: getTypicalArchivedPersons()) {
            al.addPerson(person);
        }
        return al;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Person> getTypicalArchivedPersons() {
        return new ArrayList<>(Arrays.asList(JORDAN, BARRY, HARRY, SIMON, MARY, JACK));
    }
}

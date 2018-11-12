package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPARTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPARTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MANAGER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Ssenisub;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withRating("0").withDepartment("Accounting").withManager("Ben Leong")
            .withSalary("0").withHours("0").withRate("0").withDeductibles("0").withFeedback("-NO FEEDBACK YET-")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("benson@example.com").withPhone("98765432").withRating("0")
            .withDepartment("Marketing").withManager("Marcus Tan")
            .withSalary("0").withHours("0").withRate("0").withDeductibles("0").withFeedback("-NO FEEDBACK YET-")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("carl@example.com").withAddress("wall street").withRating("0")
            .withDepartment("Tech").withManager("Moses Lim")
            .withSalary("0").withHours("0").withRate("0").withDeductibles("0").withFeedback("-NO FEEDBACK YET-")
            .build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("daniel@example.com").withAddress("10th street")
            .withRating("0").withDepartment("Accounting").withManager("Lionel Lim")
            .withSalary("0").withHours("0").withRate("0").withDeductibles("0").withFeedback("-NO FEEDBACK YET-")
            .withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("94822241")
            .withEmail("elle@example.com").withAddress("michegan ave").withRating("0")
            .withDepartment("Marketing").withManager("Edward Loh")
            .withSalary("0").withHours("0").withRate("0").withDeductibles("0").withFeedback("-NO FEEDBACK YET-")
            .build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("94824271")
            .withEmail("fiona@example.com").withAddress("little tokyo").withRating("0")
            .withDepartment("Tech").withManager("Joanne Lee")
            .withSalary("0").withHours("0").withRate("0").withDeductibles("0").withFeedback("-NO FEEDBACK YET-")
            .build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("94824421")
            .withEmail("george@example.com").withAddress("4th street").withRating("0")
            .withDepartment("Accounting").withManager("Ben Leong")
            .withSalary("0").withHours("0").withRate("0").withDeductibles("0").withFeedback("-NO FEEDBACK YET-")
            .build();
    public static final Person HARRY = new PersonBuilder().withName("Harry Potter").withPhone("95284922")
            .withEmail("harry@example.com").withAddress("9.75 platform").withRating("0")
            .withDepartment("Tech").withManager("Sirius Snape")
            .withSalary("0").withHours("0").withRate("0").withDeductibles("0").withFeedback("-NO FEEDBACK YET-")
            .withFavourite(true).build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("84824241")
            .withEmail("hoon@example.com").withAddress("little india").withRating("0")
            .withDepartment("Marketing").withManager("Marcus Tan")
            .withSalary("0").withHours("0").withRate("0").withDeductibles("0").withFeedback("-NO FEEDBACK YET-")
            .build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("84821311")
            .withEmail("ida@example.com").withAddress("chicago ave").withRating("0")
            .withDepartment("Tech").withManager("Moses Lim")
            .withSalary("0").withHours("0").withRate("0").withDeductibles("0").withFeedback("-NO FEEDBACK YET-")
            .build();
    public static final Person JASON = new PersonBuilder().withName("Jason Samuel").withPhone("98832818")
            .withEmail("jasonsamuel@example.com").withAddress("PGPR").withRating("4")
            .withDepartment("Marketing").withManager("Michael Lam").withSalary("0").withHours("0").withRate("0")
            .withDeductibles("0").withFeedback("-NO FEEDBACK YET-").build();
    public static final Person KEVIN = new PersonBuilder().withName("Kevin William").withPhone("92734819")
            .withEmail("kevinwilliam@example.com").withAddress("Surabaya").withRating("7")
            .withDepartment("Tech").withManager("Michael Lam").withSalary("0").withHours("0").withRate("0")
            .withDeductibles("0").withFeedback("-NO FEEDBACK YET-").build();
    public static final Person MICHAEL = new PersonBuilder().withName("Michael Lam").withPhone("92734819")
            .withEmail("michaellam@example.com").withAddress("Farrer Road").withRating("9")
            .withDepartment("Tech").withManager("Michael Lam").withSalary("0").withHours("0").withRate("0")
            .withDeductibles("0").withFeedback("-NO FEEDBACK YET-").build();
    public static final Person NINJA = new PersonBuilder().withName("Ninja Warrior").withPrivatePhone("92349234")
            .withPrivateEmail("ninja@example.com").withPrivateAddress("Ninja Cave").withRating("0")
            .withDepartment("Assasin").withManager("Bruce Lee").withSalary("0").withHours("0").withRate("0")
            .withDeductibles("0").withFeedback("-NO FEEDBACK YET-")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withRating("0")
            .withDepartment(VALID_DEPARTMENT_AMY).withManager(VALID_MANAGER_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withRating("0")
            .withDepartment(VALID_DEPARTMENT_BOB).withManager(VALID_MANAGER_BOB)
            .withTags(VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER
    public static final String KEYWORD_MATCHING_MOSES = "Moses"; // A keyword that matches MOSES
    public static final String KEYWORD_MATCHING_TECH = "Tech"; // A keyword that matches TECH
    public static final String KEYWORD_MATCHING_EMAIL = "benson@example.com"; // A keyword that matches EMAIL

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code Ssenisub} with all the typical persons.
     */
    public static Ssenisub getTypicalSsenisub() {
        Ssenisub ab = new Ssenisub();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}

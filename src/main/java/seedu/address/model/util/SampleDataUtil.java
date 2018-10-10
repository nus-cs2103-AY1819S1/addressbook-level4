package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.BudgetBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyBudgetBook;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.Transaction;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Room;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Room("A412"), new School("Fos"),
                getTagSet("soccer")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Room("B124"), new School("SoC"),
                getTagSet("basketball", "soccer")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Room("E216"), new School("Biz"),
                getTagSet("choir")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Room("A320"), new School("Engine"),
                getTagSet("frisbee")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Room("C403"), new School("Fass"),
                getTagSet("softball")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Room("D401"), new School("SDE"),
                getTagSet("track"))
        };
    }

    private static Cca[] getSampleCcas() {
        return new Cca[] {
            new Cca(
                new Tag("Basketball"),
                new Person(new Name("MrYanDao")),
                new Person(new Name("XiaoMing")),
                new Budget(500, 300, 200, new Transaction("transaction_log_1"))),
            new Cca(
                new Tag("Floorball"),
                new Person(new Name("XiaoBitch")),
                new Person(new Name("RisLow")),
                new Budget(600, 500, 100, new Transaction("transaction_log_2"))),
            new Cca(
                new Tag("Handball"),
                new Person(new Name("Steven Lim")),
                new Person(new Name("Bumble Bee")),
                new Budget(100, 100, 0, new Transaction("transaction_log_2"))),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static ReadOnlyBudgetBook getSampleBudgetBook() {
        BudgetBook sampleBb = new BudgetBook();
        for (Cca sampleCca : getSampleCcas()) {
            sampleBb.addCca(sampleCca);
        }
        return sampleBb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}

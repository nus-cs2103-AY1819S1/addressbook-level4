package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlySsenisub;
import seedu.address.model.Ssenisub;
import seedu.address.model.person.Address;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.Feedback;
import seedu.address.model.person.Manager;
import seedu.address.model.person.Name;
import seedu.address.model.person.OtHour;
import seedu.address.model.person.OtRate;
import seedu.address.model.person.PayDeductibles;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Salary;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Ssenisub} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Rating("10"), new Department("Accounting"),
                    new Manager("Ben Leong"), new Salary("2000"), new OtHour("10"), new OtRate("10"),
                    new PayDeductibles("200"), new Feedback("You have alot of potential."),
                    getTagSet("friends"), false),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Rating("2"), new Department("Marketing"),
                    new Manager("Marcus Tan"), new Salary("2500"), new OtHour("5"), new OtRate("20"),
                    new PayDeductibles("250"), new Feedback("Try to come earlier to work!"),
                    getTagSet("colleagues", "friends"), false),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Rating("5"), new Department("Tech"),
                    new Manager("Moses Lim"), new Salary("3000"), new OtHour("15"), new OtRate("15"),
                    new PayDeductibles("300"), new Feedback("I am disappointed that you failed to seal the deal."),
                    getTagSet("neighbours"), false),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Rating("8"),
                    new Department("Accounting"), new Manager("Lionel Lim"), new Salary("3500"),
                    new OtHour("25"), new OtRate("10"), new PayDeductibles("350"),
                    new Feedback("Congratulations for hitting your KPI! Strive foe greatness."),
                    getTagSet("family"), false),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Rating("7"), new Department("Marketing"),
                    new Manager("Edward Loh"), new Salary("4000"), new OtHour("40"), new OtRate("10"),
                    new PayDeductibles("400"), new Feedback("I see you are making good progress with the client"),
                    getTagSet("classmates"), false),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Rating("3"), new Department("Tech"),
                    new Manager("Joanne Lee"), new Salary("5000"), new OtHour("10"), new OtRate("10"),
                    new PayDeductibles("500"), new Feedback("Your work performance is worrying me."),
                    getTagSet("colleagues"), false)
        };
    }

    public static ReadOnlySsenisub getSampleSsenisub() {
        Ssenisub sampleAb = new Ssenisub();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
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

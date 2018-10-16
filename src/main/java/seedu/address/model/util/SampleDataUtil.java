package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTriviaBundle;
import seedu.address.model.TriviaBundle;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Question;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.topic.Topic;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTopicSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTopicSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTopicSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTopicSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTopicSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTopicSet("colleagues"))
        };
    }

    public static Card[] getSampleCards() {
        return new Card[] {
            new Card(new Question("Why is the earth round?"), new Answer("Because of gravity!"),
                    getTopicSet("Physics")),
            new Card(new Question("Which git command will get a copy of an online repository to your computer?"),
                    new Answer("git clone"), getTopicSet("Git")),
            new Card(new Question("What is the formula for calculating force?"),
                    new Answer("force = mass * acceleration"), getTopicSet("Physics")),
            new Card(new Question("What are the ways to merge 2 branches?"),
                    new Answer("rebase and merge"), getTopicSet("Git")),
            new Card(new Question("What is the capital of Singapore?"), new Answer("Singapore"),
                    getTopicSet("GeneralKnowledge")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static ReadOnlyTriviaBundle getSampleTriviaBundle() {
        TriviaBundle sampleTriviaBundle = new TriviaBundle();
        for (Card sampleCard : getSampleCards()) {
            sampleTriviaBundle.addCard(sampleCard);
        }
        return sampleTriviaBundle;
    }

    /**
     * Returns a topic set containing the list of strings given.
     */
    public static Set<Topic> getTopicSet(String... strings) {
        return Arrays.stream(strings)
                .map(Topic::new)
                .collect(Collectors.toSet());
    }

}

package seedu.address.model.util;

import java.util.Arrays;
import java.util.Date;
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
import seedu.address.model.test.Attempt;
import seedu.address.model.test.ReadOnlyTriviaResults;
import seedu.address.model.test.TestType;
import seedu.address.model.test.TriviaResult;
import seedu.address.model.test.TriviaResults;
import seedu.address.model.topic.Topic;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    private static final Card Q_EARTH_ROUND = new Card(new Question("Why is the earth round?"),
            new Answer("Because of gravity!"), getTopicSet("Physics"));
    private static final Card Q_GIT_CLONE = new Card(
            new Question("Which git command will get a copy of an online repository to your computer?"),
            new Answer("git clone"), getTopicSet("Git"));
    private static final Card Q_FORCE_FORMULA = new Card(new Question("What is the formula for calculating force?"),
            new Answer("mass * acceleration"), getTopicSet("Physics"));
    private static final Card Q_GIT_MERGE = new Card(new Question("What are the ways to merge 2 branches?"),
            new Answer("rebase and merge"), getTopicSet("Git"));
    private static final Card Q_CAPITAL_OF_SG = new Card(new Question("What is the capital of Singapore?"),
            new Answer("Singapore"), getTopicSet("GeneralKnowledge"));

    private static final Date ATTEMPT_ON_EARTH_TIMESTAMP = new Date(2018 - 1900, 6, 1, 16, 13);
    private static final Date ATTEMPT_ON_GIT_CLONE_TIMESTAMP = new Date(2018 - 1900, 6, 1, 16, 14);
    private static final Date ATTEMPT_ON_FORCE_FORMULA_TIMESTAMP = new Date(2018 - 1900, 6, 1, 16, 15);
    private static final Date ATTEMPT_ON_GIT_MERGE_TIMESTAMP = new Date(2018 - 1900, 6, 2, 12, 30);

    private static final Attempt ATTEMPT_ON_EARTH = new Attempt(Q_EARTH_ROUND, "mass * acceleration", false,
            ATTEMPT_ON_EARTH_TIMESTAMP);
    private static final Attempt ATTEMPT_ON_GIT_CLONE = new Attempt(Q_GIT_CLONE, "git copy", false,
            ATTEMPT_ON_GIT_CLONE_TIMESTAMP);
    private static final Attempt ATTEMPT_ON_FORCE_FORMULA = new Attempt(Q_FORCE_FORMULA, "mass * acceleration", true,
            ATTEMPT_ON_FORCE_FORMULA_TIMESTAMP);
    private static final Attempt ATTEMPT_ON_GIT_MERGE = new Attempt(Q_GIT_MERGE, "rebase and master", true,
            ATTEMPT_ON_GIT_MERGE_TIMESTAMP);

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
        return new Card[] { Q_EARTH_ROUND, Q_GIT_CLONE, Q_FORCE_FORMULA, Q_GIT_MERGE, Q_CAPITAL_OF_SG };
    }

    public static TriviaResult[] getSampleResults() {
        return new TriviaResult[] {
            new TriviaResult(TestType.MATCH_TEST, new Topic("Physics"),
                    new Date(2018 - 1990, 0, 31, 8, 30), 9,
                    Arrays.asList(ATTEMPT_ON_EARTH)),
            new TriviaResult(TestType.MATCH_TEST, new Topic("Git"),
                    new Date(2018 - 1990, 0, 31, 8, 0), 10,
                    Arrays.asList(ATTEMPT_ON_GIT_CLONE, ATTEMPT_ON_GIT_MERGE)),
            new TriviaResult(TestType.MATCH_TEST, new Topic("Physics"),
                    new Date(2018 - 1990, 0, 30, 14, 20), 11.5,
                    Arrays.asList(ATTEMPT_ON_FORCE_FORMULA, ATTEMPT_ON_EARTH))
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

    public static ReadOnlyTriviaResults getSampleTriviaResults() {
        TriviaResults sampleTriviaResults = new TriviaResults();
        for (TriviaResult result : getSampleResults()) {
            sampleTriviaResults.addTriviaResult(result);
        }
        return sampleTriviaResults;
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

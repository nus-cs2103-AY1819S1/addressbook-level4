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

    private static final Card Q_MOMENTUM_FORMULA = new Card(new Question("What is the formula for momentum?"),
            new Answer("mass * velocity"), getTopicSet("Physics", "PhysicsFormula"));
    private static final Card Q_GIT_CLONE = new Card(
            new Question("Which git command will get a copy of an online repository to your computer?"),
            new Answer("git clone"), getTopicSet("Git"));
    private static final Card Q_FORCE_FORMULA = new Card(new Question("What is the formula for calculating force?"),
            new Answer("mass * acceleration"), getTopicSet("Physics", "PhysicsFormula"));
    private static final Card Q_GIT_MERGE = new Card(new Question("What are the ways to merge 2 branches?"),
            new Answer("rebase and merge"), getTopicSet("Git"));
    private static final Card Q_CAPITAL_OF_SG = new Card(new Question("What is the capital of Singapore?"),
            new Answer("Singapore"), getTopicSet("GeneralKnowledge"));
    private static final Card Q_GRAVITATIONAL_FORMULA = new Card(
            new Question("What is the formula for gravitational potential energy?"),
            new Answer("mass * gravity * height"), getTopicSet("Physics", "PhysicsFormula"));
    private static final Card Q_WORK_DONE_FORMULA = new Card(
            new Question("What is the formula for work done?"),
            new Answer("force * distance"), getTopicSet("Physics", "PhysicsFormula"));
    private static final Card Q_POWER_FORMULA = new Card(
            new Question("What is the formula for Power?"),
            new Answer("Current(I) * Voltage(V)"), getTopicSet("Physics", "PhysicsFormula"));
    private static final Card Q_RESISTANCE_FORMULA = new Card(
            new Question("What is the formula for voltage?"),
            new Answer("Current(I) * Resistance(R)"), getTopicSet("Physics", "PhysicsFormula"));
    private static final Card Q_ELASTIC_ENERGY_FORMULA = new Card(
            new Question("What is the formula for elastic potential energy?"),
            new Answer("1/2 * SpringConstant(K) * SpringDisplacement(x)^2"),
            getTopicSet("Physics", "PhysicsFormula"));
    private static final Card Q_GRAV_FORCE_FORMULA = new Card(
            new Question("What is the formula for calculating the gravitational force between 2 objects?"),
            new Answer("(G(m1)(m2))/(r^2), where G is the universal constant, m1 and m2 represent the mass of each "
                    + "object, r represent the distance between the objects"),
            getTopicSet("Physics", "PhysicsFormula"));
    private static final Card Q_PRESSURE_FORMULA = new Card(new Question("What is the formula for pressure?"),
            new Answer("Force(F) / Area(A)"), getTopicSet("Physics", "PhysicsFormula"));
    private static final Card Q_PRESSURE_IN_LIQUID_FORMULA = new Card(
            new Question("What is the formula for pressure in liquid?"),
            new Answer("DepthOfWater(d) * densityOfWater(p) * GravitationalFieldStrength(g)"),
            getTopicSet("Physics", "PhysicsFormula"));
    private static final Card Q_DIFFERENCE_BETWEEN_FRAMEWORK_LIBRARIES = new Card(
            new Question("What is the main difference between a framework and a library?"),
            new Answer("Libraries are meant to be used ‘as is’ while frameworks are meant"
                    + "to be customized/extended"),
            getTopicSet("CS2103T", "ComputerScience"));
    private static final Card Q_DIFFERENCE_BETWEEN_ASSOCIATION_DEPENDENCIES = new Card(
            new Question("What is the difference between an association and a"
                    + "dependency in a UML?"),
            new Answer("Association is a reference based relationship between 2 classes. "
                    + "A dependency is normally created when you receive a "
                    + "reference to a class as part of a particular operation/ method."),
            getTopicSet("CS2103T", "ComputerScience", "UML"));
    private static final Card Q_DESIGN_PATTERN = new Card(
            new Question("What is a design pattern?"),
            new Answer("An elegant reusable solution to a commonly recurring problem with"
                    + "a given context in software design."),
            getTopicSet("CS2103T", "ComputerScience"));
    private static final Card Q_PURPOSE_OF_STAGING = new Card(
            new Question("What is the purpose of staging?"),
            new Answer("This intermediate step allows us to commit only some changes while"
                    + "saving other changes for a later commit."),
            getTopicSet("CS2103T", "ComputerScience", "Git"));
    private static final Card Q_REVISION_CONTROL = new Card(
            new Question("What is revision control?"),
            new Answer("The process of managing multiple versions of a piece of information."),
            getTopicSet("CS2103T", "ComputerScience"));


    private static final Date ATTEMPT_ON_MOMENTUM_WRONG_TIMESTAMP = new Date(2018 - 1900, 6, 1, 16, 12);
    private static final Date ATTEMPT_ON_MOMENTUM_CORRECT_TIMESTAMP = new Date(2018 - 1900, 6, 1, 16, 13);
    private static final Date ATTEMPT_ON_GIT_CLONE_TIMESTAMP = new Date(2018 - 1900, 6, 1, 16, 14);
    private static final Date ATTEMPT_ON_FORCE_FORMULA_TIMESTAMP = new Date(2018 - 1900, 6, 1, 16, 15);
    private static final Date ATTEMPT_ON_GIT_MERGE_TIMESTAMP = new Date(2018 - 1900, 6, 2, 12, 30);

    private static final Attempt ATTEMPT_ON_MOMENTUM_WRONG = new Attempt(Q_MOMENTUM_FORMULA, "mass * acceleration",
            false, ATTEMPT_ON_MOMENTUM_WRONG_TIMESTAMP);
    private static final Attempt ATTEMPT_ON_MOMENTUM_CORRECT = new Attempt(Q_MOMENTUM_FORMULA, "mass * velocity",
            true, ATTEMPT_ON_MOMENTUM_CORRECT_TIMESTAMP);
    private static final Attempt ATTEMPT_ON_GIT_CLONE = new Attempt(Q_GIT_CLONE, "git copy",
            false, ATTEMPT_ON_GIT_CLONE_TIMESTAMP);
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
        return new Card[] {Q_MOMENTUM_FORMULA, Q_GIT_CLONE, Q_FORCE_FORMULA, Q_GIT_MERGE, Q_CAPITAL_OF_SG,
            Q_GRAVITATIONAL_FORMULA, Q_WORK_DONE_FORMULA, Q_POWER_FORMULA, Q_RESISTANCE_FORMULA,
            Q_ELASTIC_ENERGY_FORMULA, Q_GRAV_FORCE_FORMULA, Q_PRESSURE_FORMULA, Q_PRESSURE_IN_LIQUID_FORMULA,
            Q_DIFFERENCE_BETWEEN_FRAMEWORK_LIBRARIES, Q_DIFFERENCE_BETWEEN_ASSOCIATION_DEPENDENCIES,
            Q_DESIGN_PATTERN, Q_PURPOSE_OF_STAGING, Q_REVISION_CONTROL};
    }

    public static TriviaResult[] getSampleResults() {
        return new TriviaResult[] {
            new TriviaResult(TestType.MATCH_TEST, new Topic("Physics"),
                    new Date(2018 - 1990, 0, 31, 8, 30), 9,
                    Arrays.asList(ATTEMPT_ON_MOMENTUM_WRONG, ATTEMPT_ON_MOMENTUM_CORRECT)),
            new TriviaResult(TestType.MATCH_TEST, new Topic("Git"),
                    new Date(2018 - 1990, 0, 31, 8, 0), 10,
                    Arrays.asList(ATTEMPT_ON_GIT_CLONE, ATTEMPT_ON_GIT_MERGE)),
            new TriviaResult(TestType.MATCH_TEST, new Topic("Physics"),
                    new Date(2018 - 1990, 0, 30, 14, 20), 11.5,
                    Arrays.asList(ATTEMPT_ON_FORCE_FORMULA, ATTEMPT_ON_MOMENTUM_CORRECT))
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

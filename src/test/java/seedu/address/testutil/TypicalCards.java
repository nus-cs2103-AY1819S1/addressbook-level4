package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_EARTH_FLAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_PM_OF_SG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_EARTH_FLAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_PM_OF_SG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_GEN_KNOWLEDGE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_GIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_PHYSICS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TriviaBundle;
import seedu.address.model.card.Card;

/**
 * A utility class containing a list of {@code Card} objects to be used in tests.
 */
public class TypicalCards {

    public static final Card Q_EARTH_ROUND = new CardBuilder().withQuestion("Why is the earth round?")
            .withAnswer("Because of gravity!")
            .withTopics("Physics").build();
    public static final Card Q_GIT_MERGE = new CardBuilder().withQuestion("What are the ways to merge to branches?")
            .withAnswer("rebase and merge")
            .withTopics("Git").build();
    public static final Card Q_FORCE_FORMULA = new CardBuilder().withQuestion("What is the formula for force?")
            .withAnswer("force = mass * acceleration")
            .withTopics("Physics").build();
    public static final Card Q_CAPITAL_SG = new CardBuilder().withQuestion("Name of Singapore's capital")
            .withAnswer("Singapore")
            .withTopics("GeneralKnowledge").build();
    public static final Card Q_GIT_CLONE = new CardBuilder()
            .withQuestion("Which git command will get a copy of an online repository to your computer?")
            .withAnswer("git clone")
            .withTopics("Git").build();
    public static final Card Q_DENSITY_FORMULA = new CardBuilder().withQuestion("Formula of density")
            .withAnswer("density = mass / volume")
            .withTopics("Physics").build();
    public static final Card Q_TALLEST_BUILDING = new CardBuilder()
            .withQuestion("Tallest building in the world?")
            .withAnswer("Burj Khalifa")
            .withTopics("GeneralKnowledge").build();
    public static final Card Q_RANDOM_QUESTION = new CardBuilder()
            .withQuestion("When is my girlfriend's birthday?")
            .withAnswer("7 September?")
            .withTopics().build(); // empty topic

    // Manually added
    public static final Card Q_CS2103_PROF = new CardBuilder().withQuestion("Who is the prof for CS2103?")
            .withAnswer("Prof Da Myth")
            .withTopics("GeneralKnowledge").build();
    public static final Card Q_APP_MADE = new CardBuilder().withQuestion("When is this trivia app made?")
            .withAnswer("In the year 2018")
            .withTopics("GeneralKnowledge").build();

    // Manually added - Card's details found in {@code CommandTestUtil}
    public static final Card Q_FLAT_EARTH = new CardBuilder().withQuestion(VALID_QUESTION_EARTH_FLAT)
            .withAnswer(VALID_ANSWER_EARTH_FLAT)
            .withTopics(VALID_TOPIC_PHYSICS).build();
    public static final Card Q_GIT_COMMIT = new CardBuilder().withQuestion(VALID_QUESTION_GIT_COMMIT)
            .withAnswer(VALID_ANSWER_GIT_COMMIT)
            .withTopics(VALID_TOPIC_GIT)
            .build();
    public static final Card Q_PM_OF_SG = new CardBuilder().withQuestion(VALID_QUESTION_PM_OF_SG)
            .withAnswer(VALID_ANSWER_PM_OF_SG)
            .withTopics(VALID_TOPIC_GEN_KNOWLEDGE)
            .build();

    public static final String KEYWORD_MATCHING_WHAT = "What"; // A keyword that matches MEIER

    private TypicalCards() {} // prevents instantiation

    /**
     * Returns an {@code TriviaBundle} with all the typical persons.
     */
    public static TriviaBundle getTypicalTriviaBundle() {
        TriviaBundle triviaBundle = new TriviaBundle();
        for (Card card : getTypicalCards()) {
            triviaBundle.addCard(card);
        }
        return triviaBundle;
    }

    public static List<Card> getTypicalCards() {
        return new ArrayList<>(Arrays.asList(Q_EARTH_ROUND, Q_GIT_MERGE, Q_FORCE_FORMULA, Q_CAPITAL_SG, Q_GIT_CLONE,
                Q_DENSITY_FORMULA, Q_TALLEST_BUILDING));
    }
}

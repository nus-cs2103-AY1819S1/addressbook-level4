package seedu.address.testutil;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.card.IndexedAnswer;
import seedu.address.model.card.IndexedQuestion;
import seedu.address.model.state.State;
import seedu.address.model.test.matchtest.MatchAttempt;
import seedu.address.model.test.matchtest.MatchTest;
import seedu.address.model.topic.Topic;

/**
 * A utility class for Match Test.
 */
public class MatchTestUtil {
    /**
     * Used to obtain the base0 indexes of matching question and answer in the test.
     * @param test The matchTest that is ongoing.
     * @param card The card that is you want to match.
     * @return the an array of indexes of size 2. With the first and second indexes representing the question and
     * answer respectively.
     */
    public static Index[] getIndexes(MatchTest test, Card card) {
        return new Index[] {
                Index.fromZeroBased(test.getQuestions().indexOf(card.getQuestion())),
                Index.fromZeroBased(test.getAnswers().indexOf(card.getAnswer()))
        };
    }

    /**
     * Will generate a correct MatchAttempt.
     */
    public static MatchAttempt generateCorrectMatchAttempt(MatchIndexPair indexPair) {
        Card cardToMatch = indexPair.getAttemptedCard();
        return new MatchAttempt(cardToMatch,
                new IndexedQuestion(cardToMatch.getQuestion(), indexPair.getQIndex().getOneBased()),
                new IndexedAnswer(cardToMatch.getAnswer(), indexPair.getAIndex().getOneBased()));
    }

    /**
     * Start a new matchTest with the given topic and model.
     */
    public static MatchTest startMatchTest(Model model, Topic topic) {
        MatchTest matchTest = new MatchTest(topic, model.getTriviaBundle());
        model.startTriviaTest(matchTest);
        return matchTest;
    }

    /**
     * Will complete the provided matchTest. The resultant state of the App would be in MATCH_TEST_RESULT.
     */
    public static void completeMatchTest(Model model, Topic topic) {
        MatchTest matchTest = new MatchTest(topic, model.getTriviaBundle());
        completeMatchTest(model, matchTest);
    }

    /**
     * Will complete the given matchtest.
     */
    public static void completeMatchTest(Model model, MatchTest matchTest) {
        model.startTriviaTest(matchTest);

        List<MatchIndexPair> pairList = new ArrayList<>();
        for (Card card : matchTest.getCardsTested()) {
            pairList.add(new MatchIndexPair(matchTest, card));
        }

        for (int i = 0; i < pairList.size(); i++) {
            if (i == pairList.size() - 1) {
                break;
            }
            MatchIndexPair pair = pairList.get(i);
            matchTest.respondToCorrectAttempt(generateCorrectMatchAttempt(pair));
        }

        // complete the matchtest and update the model state.
        MatchIndexPair pair = pairList.get(pairList.size() - 1);
        model.matchQuestionAndAnswer(pair.getQIndex(), pair.getAIndex());
        assertEquals(model.getAppState(), State.MATCH_TEST_RESULT);
    }
}

package seedu.address.testutil;

import static seedu.address.testutil.MatchTestUtil.getIndexes;

import seedu.address.commons.core.index.Index;
import seedu.address.model.card.Card;
import seedu.address.model.test.matchtest.MatchTest;

/**
 * A class to encapsulate a pair of question and answer index which will lead to a correct match.
 */
public class MatchIndexPair {
    private final Index questionIndex;
    private final Index answerIndex;
    private final Card card;

    public MatchIndexPair(MatchTest test, Card card) {
        Index[] indexes = getIndexes(test, card);
        this.questionIndex = indexes[0];
        this.answerIndex = indexes[1];
        this.card = card;
    }

    public Index getQIndex() {
        return questionIndex;
    }

    public Index getAIndex() {
        return answerIndex;
    }

    public Card getAttemptedCard() {
        return card;
    }
}

package seedu.address.model.test;

import javafx.collections.ObservableList;
import seedu.address.model.card.Card;

/**
 * Unmodifiable view of an TriviaResults
 */
public interface ReadOnlyTriviaResults {
    /**
     * Returns an unmodifiable view of the TriviaResults.
     */
    ObservableList<TriviaResult> getResultList();

    /**
     * Returns a list of Attempts made by the given card.
     */
    ObservableList<Attempt> getAttemptsByCard(Card card);
}

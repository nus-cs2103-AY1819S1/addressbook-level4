package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.card.Card;

/**
 * Unmodifiable view of an Trivia Bundle
 */
public interface ReadOnlyTriviaBundle {
    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Card> getCardList();

}

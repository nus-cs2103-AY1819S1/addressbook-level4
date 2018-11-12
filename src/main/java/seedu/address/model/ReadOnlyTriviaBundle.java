package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.card.Card;

/**
 * Unmodifiable view of an Trivia Bundle
 */
public interface ReadOnlyTriviaBundle {
    /**
     * Returns an unmodifiable view of the cards list.
     * This list will not contain any duplicate cards.
     */
    ObservableList<Card> getCardList();

    /**
     * Returns a new unmodifiable list of cards that is filtered by the given topic.
     */
    ObservableList<Card> getListOfCardFilteredByTopic(Predicate<Card> predicate);

}

package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.Anakin_deck.Anakin_Card;
import seedu.address.model.Anakin_deck.Anakin_Deck;

/**
 * The API of the Anakin_Model component
 */
public interface Anakin_Model {

    /** {@code Predicate} that always evaluate to true */
    Predicate<Anakin_Deck> PREDICATE_SHOW_ALL_DECKS = unused -> true;

    void resetData(Anakin_ReadOnlyAnakin newData);

    Anakin_ReadOnlyAnakin getAnakin();

    boolean hasDeck(Anakin_Deck deck);

    void addDeck(Anakin_Deck deck);

    void deleteDeck(Anakin_Deck deck);

    void updateDeck(Anakin_Deck target, Anakin_Deck editedDeck);

    ObservableList<Anakin_Deck> getFilteredDeckList();

    void updateFilteredDeckList(Predicate<Anakin_Deck> predicate);

    ObservableList<Anakin_Card> getFilteredCardList();

    void updateFilteredCardList(Predicate<Anakin_Card> predicate);

    boolean canUndoAnakin();

    boolean canRedoAnakin();

    void undoAnakin();

    void redoAnakin();

    void commitAnakin();
}

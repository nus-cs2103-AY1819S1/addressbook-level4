package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.card.Card;
import seedu.address.model.card.UniqueCardList;

/**
 * Wrap all the data at the trivia bundle level.
 * Duplicates are not allowed by (.equals comparison)
 */
public class TriviaBundle implements ReadOnlyTriviaBundle {
    private final UniqueCardList cards;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        cards = new UniqueCardList();
    }

    public TriviaBundle() {}

    /**
     * Creates a AddressBook using the Persons in the {@code toBeCopied}
     */
    public TriviaBundle(ReadOnlyTriviaBundle toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the card list with {@code cards}.
     * {@code cards} must not contain duplicate cards.
     */
    public void setCards(List<Card> cards) {
        this.cards.setCards(cards);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyTriviaBundle newData) {
        requireNonNull(newData);

        setCards(newData.getCardList());
    }

    //// card-level operations

    /**
     * Returns true if a card with the same identity as {@code card} exists in the address book.
     */
    public boolean hasCard(Card card) {
        requireNonNull(card);
        return cards.contains(card);
    }

    /**
     * Adds a card to the trivia bundle.
     * The card must not already exist in the bundle.
     */
    public void addCard(Card c) {
        cards.add(c);
    }


    /**
     * Replaces the given card {@code target} in the list with {@code editedCard}.
     * {@code target} must exist in the trivia bundle.
     * The card identity of {@code editedCard} must not be the same as another existing card in the trivia bundle.
     */
    public void updateCard(Card target, Card editedCard) {
        requireNonNull(editedCard);

        cards.setCard(target, editedCard);
    }

    //// util methods

    @Override
    public String toString() {
        return cards.asUnmodifiableObservableList().size() + " cards";
        // TODO: refine later
    }

    @Override
    public ObservableList<Card> getCardList() {
        return cards.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TriviaBundle // instanceof handles nulls
                && cards.equals(((TriviaBundle) other).cards));
    }

    @Override
    public int hashCode() {
        return cards.hashCode();
    }
}

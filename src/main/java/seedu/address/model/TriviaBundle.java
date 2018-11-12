package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
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

    /**
     *
     * Return if the card list {@code cards} can be cleared
     */
    public boolean canClearCardList() {
        return !cards.isEmpty();
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
     * Returns true if a card with the same identity as {@code card} exists in the address book.
     */
    public boolean haveAnyCard(UniqueCardList cardList) {
        requireNonNull(cardList);
        return cardList.toStream().anyMatch(card -> cards.contains(card));
    }

    /**
     * Adds a card to the trivia bundle.
     * The card must not already exist in the bundle.
     */
    public void addCard(Card c) {
        cards.add(c);
    }

    /**
     * Adds a list of cards to the trivia bundle and marks each of them as recently imported.
     * The cards must not already exist in the bundle.
     */
    public void addMultipleCards(UniqueCardList cardList) {
        for (Card c : cardList) {
            cards.add(c.updateRecentlyImportedStatus(true));
        }
    }

    /**
     * Removes {@code key} from this {@code TriviaBundle}.
     * {@code key} must exist in the trivia bundle.
     */
    public void removeCard(Card key) {
        cards.remove(key);
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

    /**
     * Resets the recently import status of all cards to false whenever a new import is executed.
     */
    public void resetRecentlyImportStatus() {
        for (Card c : cards) {
            cards.setCard(c, c.updateRecentlyImportedStatus(false));
        }
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
    public ObservableList<Card> getListOfCardFilteredByTopic(Predicate<Card> predicate) {
        ObservableList<Card> filteredCards = FXCollections.observableList(getCardList().stream()
                .filter(predicate)
                .collect(Collectors.toList()));
        return FXCollections.unmodifiableObservableList(filteredCards);
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

package seedu.address.model.Anakin_deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Represents a Deck inside Anakin.
 */
public class Anakin_Deck {

    private final String name;  // Change to Name name later
    private final List<Anakin_Card> cards;

    public Anakin_Deck(String name) {
        this.name = name;
        cards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Anakin_Card> getCards() {
        return cards;
    }

    /**
     * Returns true if 2 decks are the same, or have identical name.
     */
    public boolean isSameDeck(Anakin_Deck otherDeck) {
        if (otherDeck == this) {
            return true;
        }

        return otherDeck.getName().equals(getName());
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Anakin_Deck)) {
            return false;
        }

        return isSameDeck((Anakin_Deck) other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        return builder.toString();
    }

}

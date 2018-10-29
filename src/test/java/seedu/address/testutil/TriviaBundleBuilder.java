package seedu.address.testutil;

import seedu.address.model.TriviaBundle;
import seedu.address.model.card.Card;

/**
 * A utility class to help with building TriviaBundle objects.
 * Example usage: <br>
 *     {@code TriviaBundle ab = new TriviaBundleBuilder().withCard(Q_EARTH_ROUND).build();}
 */
public class TriviaBundleBuilder {

    private TriviaBundle triviaBundle;

    public TriviaBundleBuilder() {
        triviaBundle = new TriviaBundle();
    }

    public TriviaBundleBuilder(TriviaBundle triviaBundle) {
        this.triviaBundle = triviaBundle;
    }

    /**
     * Adds a new {@code Card} to the {@code TriviaBundle} that we are building.
     */
    public TriviaBundleBuilder withCard(Card card) {
        triviaBundle.addCard(card);
        return this;
    }

    public TriviaBundle build() {
        return triviaBundle;
    }
}

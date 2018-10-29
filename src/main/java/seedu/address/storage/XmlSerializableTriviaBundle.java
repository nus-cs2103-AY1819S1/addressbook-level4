package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTriviaBundle;
import seedu.address.model.TriviaBundle;
import seedu.address.model.card.Card;

/**
 * An Immutable TriviaBundle that is serializable to XML format
 */
@XmlRootElement(name = "triviabundle")
public class XmlSerializableTriviaBundle {
    public static final String MESSAGE_DUPLICATE_CARD = "Cards list contains duplicate cards(s).";

    @XmlElement
    private List<XmlAdaptedCard> cards;

    /**
     * Creates an empty XmlSerializableTriviaBundle.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableTriviaBundle() {
        cards = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableTriviaBundle(ReadOnlyTriviaBundle src) {
        this();
        cards.addAll(src.getCardList().stream().map(XmlAdaptedCard::new).collect(Collectors.toList()));
    }

    /**
     * Converts this triviabundle into the model's {@code TriviaBundle} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedCard}.
     */
    public TriviaBundle toModelType() throws IllegalValueException {
        TriviaBundle triviaBundle = new TriviaBundle();
        for (XmlAdaptedCard p : cards) {
            Card card = p.toModelType();
            if (triviaBundle.hasCard(card)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CARD);
            }
            triviaBundle.addCard(card);
        }
        return triviaBundle;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableTriviaBundle)) {
            return false;
        }
        return cards.equals(((XmlSerializableTriviaBundle) other).cards);
    }
}

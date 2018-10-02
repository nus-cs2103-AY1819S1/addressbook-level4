package seedu.address.model.card;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represent a card in a deck.
 * Guarantees: details are present and not null, field values are immutable.
 */
public class Card {
    // identity fields
    private final Question question;

    // data fields
    private final Answer answer;
    private final Set<Tag> tags = new HashSet<>();

    public Card(Question question, Answer answer, Set<Tag> tags) {
        this.question = question;
        this.answer = answer;
        if (tags.isEmpty()) {
            this.tags.add(new Tag("NoTag"));
        } else {
            this.tags.addAll(tags);
        }
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both cards have the same identity fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Card)) {
            return false;
        }

        Card otherPerson = (Card) other;
        return otherPerson.getQuestion().equals(getQuestion());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(question, answer, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Question: ")
                .append(getQuestion())
                .append(" Answer: ")
                .append(getAnswer())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}

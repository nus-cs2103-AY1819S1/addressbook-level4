package seedu.address.model.card;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.topic.Topic;

/**
 * Represent a card in a deck.
 * Guarantees: details are present and not null, field values are immutable.
 */
public class Card {
    // identity fields
    private final Question question;

    // data fields
    private final Answer answer;
    private final Set<Topic> topics = new HashSet<>();

    // attribute fields
    private boolean isRecentlyAdded = false;

    public Card(Question question, Answer answer, Set<Topic> topics) {
        this.question = question;
        this.answer = answer;
        if (topics.isEmpty()) {
            this.topics.add(new Topic("NoTopic"));
        } else {
            this.topics.addAll(topics);
        }
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    /**
     * Returns an immutable topic set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Topic> getTopics() {
        return Collections.unmodifiableSet(topics);
    }

    /**
     * Returns a new card with an updated isRecentlyUpdated status.
     */
    public Card updateRecentlyImportedStatus(boolean status) {
        Card updated = new Card(this.question, this.answer, this.topics);
        updated.isRecentlyAdded = status;
        return updated;
    }

    /**
     * Returns true if the cards have just been imported.
     */
    public boolean isRecentlyAdded() {
        return isRecentlyAdded;
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

        Card otherCard = (Card) other;
        return otherCard.getQuestion().equals(getQuestion());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(question);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Question: ")
                .append(getQuestion())
                .append(" Answer: ")
                .append(getAnswer())
                .append(" Topics: ");
        getTopics().forEach(builder::append);
        return builder.toString();
    }
}

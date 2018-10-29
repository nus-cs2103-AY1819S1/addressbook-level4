package seedu.address.model.card;

import java.util.function.Predicate;

/**
 * Tests that a {@code Card}'s {@code Topic} matches any of the keywords given.
 */
public class TopicIsKeywordPredicate implements Predicate<Card> {
    private final String keyword;

    public TopicIsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    public String getTopicKeyword() {
        return keyword;
    }

    @Override
    public boolean test(Card card) {
        return card.getTopics().stream()
                .anyMatch(topic -> topic.topicName.toLowerCase().equals(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TopicIsKeywordPredicate// instanceof handles nulls
                && keyword.equals(((TopicIsKeywordPredicate) other).keyword)); // state check
    }
}

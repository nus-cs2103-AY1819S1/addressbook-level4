package seedu.learnvocabulary.model.word;

import seedu.learnvocabulary.commons.util.StringUtil;
import seedu.learnvocabulary.model.tag.Tag;

import java.util.List;
import java.util.function.Predicate;
//@@author Harryqu123
/**
 * Tests that a {@code Word}'s {@code Name} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Word> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getTag() {
        return keywords.get(0);
    }
    @Override
    public boolean test(Word word) {
        return keywords.stream()
                .anyMatch(keyword -> word.getTags().contains(new Tag(keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
//@@author
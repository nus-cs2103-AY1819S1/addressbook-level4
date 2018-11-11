package seedu.restaurant.model.account;

import java.util.List;
import java.util.function.Predicate;

import seedu.restaurant.commons.util.StringUtil;

//@@author AZhiKai

/**
 * Tests that a {@code Account}'s {@code Username} matches any of the keywords given.
 */
public class UsernameContainsKeywordsPredicate implements Predicate<Account> {

    private final List<String> keywords;

    public UsernameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Account account) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(account.getUsername().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UsernameContainsKeywordsPredicate // instanceof handles nulls
                    && keywords.equals(((UsernameContainsKeywordsPredicate) other).keywords)); // state check
    }
}

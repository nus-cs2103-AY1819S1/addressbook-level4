package seedu.restaurant.model.account;

import java.util.function.Predicate;

import seedu.restaurant.commons.util.StringUtil;

//@@author AZhiKai

/**
 * Tests that a {@code Account}'s {@code Username} matches any of the keywords given.
 */
public class UsernameContainsKeywordPredicate implements Predicate<Account> {

    private final String keyword;

    public UsernameContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Account account) {
        return StringUtil.containsSubstringIgnoreCase(account.getUsername().toString(), keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UsernameContainsKeywordPredicate // instanceof handles nulls
                    && keyword.equals(((UsernameContainsKeywordPredicate) other).keyword)); // state check
    }
}

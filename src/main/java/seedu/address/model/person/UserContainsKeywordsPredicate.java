package seedu.address.model.person;

import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code attributes} matches any of the associated keywords given.
 */
public class UserContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final List<String> phoneKeywords;
    private final List<String> addressKeywords;
    private final List<String> emailKeywords;
    private final List<String> interestKeywords;
    private final List<String> tagKeywords;

    public UserContainsKeywordsPredicate(List<String> nameKeywords,
                                         List<String> phoneKeywords,
                                         List<String> addressKeywords,
                                         List<String> emailKeywords,
                                         List<String> interestKeywords,
                                         List<String> tagKeywords) {
        this.nameKeywords = nameKeywords;
        this.phoneKeywords = phoneKeywords;
        this.addressKeywords = addressKeywords;
        this.emailKeywords = emailKeywords;
        this.interestKeywords = interestKeywords;
        this.tagKeywords = tagKeywords;
    }

    @Override
    public boolean test(Person person) {
        boolean hasNameKeywords = false;
        boolean hasPhoneKeywords = false;
        boolean hasAddressKeywords = false;
        boolean hasEmailKeywords = false;
        boolean hasInterestKeywords = false;
        boolean hasTagKeywords = false;

        if (nameKeywords != null) {
            hasNameKeywords = nameKeywords.stream()
                    .anyMatch(nameKeyword -> StringUtil.containsWordIgnoreCase(person.getName().value, nameKeyword));
        }

        if (phoneKeywords != null) {
            hasPhoneKeywords = phoneKeywords.stream()
                    .anyMatch(phoneKeyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, phoneKeyword));
        }

        if (addressKeywords != null) {
            hasAddressKeywords = addressKeywords.stream()
                    .anyMatch(addressKeyword -> StringUtil.containsWordIgnoreCase(person.getAddress().value,
                            addressKeyword));
        }

        if (emailKeywords != null) {
            hasEmailKeywords = emailKeywords.stream()
                    .anyMatch(emailKeyword -> StringUtil.containsWordIgnoreCase(person.getEmail().value, emailKeyword));
        }

        if (interestKeywords != null) {
            hasInterestKeywords = interestKeywords.stream()
                    .anyMatch(interestKeyword -> StringUtil.containsWordIgnoreCase(person.getInterests()
                            .stream()
                            .map(x -> x.interestName)
                            .collect(joining(" ")), interestKeyword));
        }

        if (tagKeywords != null) {
            hasTagKeywords = tagKeywords.stream()
                    .anyMatch(tagKeyword -> StringUtil.containsWordIgnoreCase(person.getTags()
                            .stream()
                            .map(x -> x.tagName)
                            .collect(joining(" ")), tagKeyword));
        }
        return hasNameKeywords || hasPhoneKeywords || hasAddressKeywords || hasEmailKeywords || hasInterestKeywords
                || hasTagKeywords;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserContainsKeywordsPredicate // instanceof handles nulls
                && (nameKeywords == null
                || nameKeywords.equals(((UserContainsKeywordsPredicate) other).nameKeywords))
                && (phoneKeywords == null
                || phoneKeywords.equals(((UserContainsKeywordsPredicate) other).phoneKeywords))
                && (addressKeywords == null
                || addressKeywords.equals(((UserContainsKeywordsPredicate) other).addressKeywords))
                && (emailKeywords == null
                || emailKeywords.equals(((UserContainsKeywordsPredicate) other).emailKeywords))
                && (interestKeywords == null
                || interestKeywords.equals(((UserContainsKeywordsPredicate) other).interestKeywords))
                && (tagKeywords == null
                || tagKeywords.equals(((UserContainsKeywordsPredicate) other).tagKeywords)));
    }
}

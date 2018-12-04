package seedu.thanepark.model.ride;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import seedu.thanepark.commons.util.CollectionUtil;
import seedu.thanepark.commons.util.StringUtil;
import seedu.thanepark.model.tag.Tag;


/**
 * Tests that a {@code Ride}'s attributes  matches any of the keywords given.
 */
public class RideContainsKeywordsPredicate implements Predicate<Ride> {
    private final List<String> keywords;
    private Optional<Zone> addressKeyWords;
    private Set<Tag> tagKeyWords;

    public RideContainsKeywordsPredicate(List<String> keywords, Optional... other) {
        this.keywords = keywords;
        addressKeyWords = Optional.empty();
        tagKeyWords = new HashSet<>();
        getKeyWords(other);
    }

    /**
     * Get other keywords
     * @param others
     */
    private void getKeyWords(Optional... others) {
        for (Optional keywords : others) {
            if (keywords.isPresent()) {
                if (keywords.get()instanceof Zone) {
                    addressKeyWords = keywords;
                }
                if (keywords.get().getClass().equals(HashSet.class)) {
                    Set<Tag> tags = (HashSet) keywords.get();
                    if (!tags.isEmpty()) {
                        tagKeyWords = tags;
                    }
                }
            }
        }
    }

    @Override
    public boolean test(Ride ride) {
        boolean result = false;
        if (addressKeyWords.isPresent()) {
            result = result || StringUtil.containsStringIgnoreCase(ride.getZone().value,
                    addressKeyWords.get().value);
        }
        if (!tagKeyWords.isEmpty()) {
            result = result || CollectionUtil.containsAny(ride.getTags(), tagKeyWords);
        }
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(ride.getName().fullName, keyword)) || result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RideContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((RideContainsKeywordsPredicate) other).keywords)); // state check
    }

}

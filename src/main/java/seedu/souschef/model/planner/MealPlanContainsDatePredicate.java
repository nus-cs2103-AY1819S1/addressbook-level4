package seedu.souschef.model.planner;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Day}'s {@code LocalDate} matches any of the keywords given.
 */
public class MealPlanContainsDatePredicate implements Predicate<Day> {
    private final List<String> keywords;

    public MealPlanContainsDatePredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Day day) {
        for (String keyword : keywords) {
            if (day.getDate().toString().equals(keyword)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof MealPlanContainsDatePredicate // instanceof handles nulls
            && keywords.equals(((MealPlanContainsDatePredicate) other).keywords)); // state check
    }
}


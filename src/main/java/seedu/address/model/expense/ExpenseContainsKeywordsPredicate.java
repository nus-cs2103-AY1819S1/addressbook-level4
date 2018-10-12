package seedu.address.model.expense;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.ArgumentMultimap;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Tests that a {@code Expense}'s {@code Name} matches any of the keywords given.
 */
public class ExpenseContainsKeywordsPredicate implements Predicate<Expense>{
    private final ArgumentMultimap keywords;

    public ExpenseContainsKeywordsPredicate(ArgumentMultimap keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Expense expense){
        String nameKeywords = keywords.getValue(PREFIX_NAME).orElse("");
        String categoryKeywords = keywords.getValue(PREFIX_CATEGORY).orElse("");
        List<String> tagKeywords = keywords.getAllValues(PREFIX_TAG);
        String dateKeywords = keywords.getValue(PREFIX_DATE).orElse("");
        String costKeywords = keywords.getValue(PREFIX_COST).orElse("");

        if (!nameKeywords.equals("")){
            List<String> separatedNameKeywords = Arrays.asList(nameKeywords.trim().split("\\s+"));
            return separatedNameKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(expense.getName().expenseName, keyword));
        }
        if (!categoryKeywords.equals("")){
            List<String> separatedCategoryKeywords = Arrays.asList(categoryKeywords.trim().split("\\s+"));
            return separatedCategoryKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(expense.getCategory().categoryName, keyword));
        }
        if (!costKeywords.equals("")){

        }
        if (!dateKeywords.equals("")){

        }
        if (!tagKeywords.isEmpty()){
            List<String> separatedTagKeywordsList = new ArrayList<>();
            for (String tag : tagKeywords){
                separatedTagKeywordsList.addAll(Arrays.asList(tag.split("\\s+")));
            }
            return tagKeywords.stream()
                    .anyMatch(keyword -> expense.getTags().stream()
                    .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword)));
        }
        return false;
    }



    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenseContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ExpenseContainsKeywordsPredicate) other).keywords)); // state check
    }

}

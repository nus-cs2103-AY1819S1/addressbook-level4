package seedu.address.model.budget;
//@@author winsonhys

import seedu.address.model.expense.Category;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents maximum budget of a category in the expense tracker
 * Guarantees: details are present and not null, field values are validated, mutable. Budget here never exceeds the
 * total budget of the expense tracker
 */
public class CategoryBudget extends Budget {

    private Category belongsToCategory;


    public CategoryBudget (String category, String budget) {
        super(budget);
        checkArgument(Category.isValidCategory(category), Category.MESSAGE_CATEGORY_CONSTRAINTS);
        this.belongsToCategory = new Category(category);
    }

    public Category getCategory() {
        return this.belongsToCategory;
    }


    @Override
    public boolean equals(Object categoryBudget) {
        CategoryBudget otherCategoryBudget = (CategoryBudget) categoryBudget;
        return this.belongsToCategory.equals(otherCategoryBudget.getCategory())
        && super.equals(categoryBudget);
    }

    @Override
    public int hashCode () {
        return belongsToCategory.hashCode();
    }
}

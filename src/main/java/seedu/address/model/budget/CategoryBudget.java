package seedu.address.model.budget;
//@@author winsonhys

import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.expense.Category;

/**
 * Represents maximum budget of a category in the expense tracker
 * Guarantees: details are present and not null, field values are validated, mutable. Budget here never exceeds the
 * total budget of the expense tracker
 */
public class CategoryBudget extends Budget {

    private Category belongsToCategory;


    public CategoryBudget (String category, String budget) {
        //TODO: Refactor Budget out as an abstract class as this violates Liskov
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
        return super.equals(categoryBudget) && this.belongsToCategory.equals(otherCategoryBudget.getCategory());
    }

    @Override
    public int hashCode () {
        return belongsToCategory.hashCode();
    }
}

package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import seedu.address.model.budget.CategoryBudget;

//@@author Snookerballs
/**
 * Provides a handle to a category icon in the categories panel.
 */
public class CategoryIconHandle extends NodeHandle<Node> {
    public static final String CATEGORIES_ICON_ID = "#categoriesIcon";
    private static final String CATEGORY_NAME_FIELD_ID = "#categoryName";
    private static final String CATEGORY_BUDGET_CAP_ID = "#categoryBudgetCap";

    private final Label categoryName;
    private final Label categoryBudgetCap;


    public CategoryIconHandle(Node node) {
        super(node);

        categoryBudgetCap = getChildNode(CATEGORY_BUDGET_CAP_ID);
        categoryName = getChildNode(CATEGORY_NAME_FIELD_ID);
    }

    public String getCategoryName() {
        return categoryName.getText();
    }

    public double getCategoryBudgetCap() {
        return Double.parseDouble(categoryBudgetCap.getText().substring(1));
    }
    /**
     * Returns true if the information in this object matches the information in the {@code budget} object
     * @param budget to compare against.
     * @return
     */
    public boolean equals(CategoryBudget budget) {
        return categoryName.getText().equals(budget.toString())
                && categoryBudgetCap.getText().equals("$" + budget.getBudgetCap());
    }

    /**
     * Converts the {@code CategoryIconHandle} to a {@code CategoryBudget} object.
     * @return {@code this} as a {@code CategoryBudget} object.
     */
    public CategoryBudget toBudget() {
        return new CategoryBudget(categoryName.getText(),
                String.format("%.2f", Double.parseDouble(categoryBudgetCap.getText().substring(1))));
    }
}

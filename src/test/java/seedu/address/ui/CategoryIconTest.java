package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import guitests.guihandles.CategoryIconHandle;
import seedu.address.model.budget.CategoryBudget;

//@author Snookerballs
public class CategoryIconTest extends GuiUnitTest {
    private static final double DELTA = 1e-15;

    @Test
    public void display() {
        CategoryBudget budget = new CategoryBudget("CAT", "40.00");
        CategoryIcon categoryIcon = new CategoryIcon(budget);
        uiPartRule.setUiPart(categoryIcon);
        CategoryIconHandle categoryIconHandle = new CategoryIconHandle(getChildNode(categoryIcon.getRoot(),
                CategoryIconHandle.CATEGORIES_ICON_ID));
        assertEquals(categoryIconHandle.getCategoryBudgetCap(), budget.getBudgetCap(), DELTA);
        assertEquals(categoryIconHandle.getCategoryName(), budget.toString());
    }

}

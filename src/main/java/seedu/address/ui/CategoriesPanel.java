package seedu.address.ui;

import java.util.Iterator;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.budget.CategoryBudget;

//@@author snookerballs
/**
 * Panel containing the totalBudget information.
 */
public class CategoriesPanel extends UiPart<Region> {

    private static final String FXML = "CategoriesPanel.fxml";
    private static final int MAX_COL = 4;
    private static int currentColumn;
    private static int currentRow;

    @FXML
    private GridPane categoriesGrid;

    /**
     * Create categoriesPanel with a list of categories
     * @param categories to list
     */
    public CategoriesPanel(Iterator<CategoryBudget> categories) {
        super(FXML);
        currentColumn = 0;
        currentRow = 0;
        setConnection(categories);
    }

    public void setConnection(Iterator<CategoryBudget> budgets) {
        categoriesGrid.getChildren().clear();
        currentColumn = 0;
        while (currentColumn < MAX_COL && budgets.hasNext()) {
            updateCategories(budgets.next());
        }
    }

    /**
    * Update the list of categories being displayed
    */
    public void updateCategories(CategoryBudget category) {
        CategoryIcon categoryIcon = new CategoryIcon(category);
        categoriesGrid.add(categoryIcon.getRoot(), currentColumn, currentRow);
        currentColumn++;
    }

}

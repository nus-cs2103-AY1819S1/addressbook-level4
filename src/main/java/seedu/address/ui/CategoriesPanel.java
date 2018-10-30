package seedu.address.ui;

import java.util.Iterator;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

//@@author snookerballs
/**
 * Panel containing the totalBudget information.
 */
public class CategoriesPanel extends UiPart<Region> {
    private static final String FXML = "CategoriesPanel.fxml";
    private static final String IMAGE_PATH_PREFIX = "/images/categoryIcons/";
    private static final String IMAGE_PATH_POSTFIX = ".png";
    private static final int MAX_COL = 4;
    private static int currentColumn;
    private static int currentRow;

    @FXML
    private GridPane categoriesGrid;

    /**
     * Create categoriesPanel with a list of categories
     * @param categories to list
     */
    public CategoriesPanel(Iterator<Map.Entry<String, Double>> categories) {
        super(FXML);
        currentColumn = 0;
        currentRow = 0;
        categories.forEachRemaining(entry -> updateCategories(entry.getKey()));
    }

    /**
     * Update the list of categories being displayed
     * @param name of category to update
     */
    public void updateCategories(String name) {
        if (currentColumn / MAX_COL > 1) {
            currentColumn = 0;
            currentRow++;
        }

        CategoryIcon categoryIcon = new CategoryIcon(name, IMAGE_PATH_PREFIX + "bulb" + IMAGE_PATH_POSTFIX);
        categoriesGrid.add(categoryIcon.getRoot(), currentColumn, currentRow);
        currentColumn++;
    }


}

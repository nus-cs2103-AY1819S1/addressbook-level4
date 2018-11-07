package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.model.budget.CategoryBudget;

/**
 * A UI Component that displays information of a {@code Category}
 */

//@@author snookerballs
public class CategoryIcon extends UiPart<Region> {
    private static final String FXML = "CategoryIcon.fxml";
    private static final String IMAGE_PATH = "/images/categoryIcons/categoryIcon.png";

    @FXML
    private Label categoryName;

    @FXML
    private ImageView categoryIcon;

    @FXML
    private Label categoryBudgetCap;

    public CategoryIcon(CategoryBudget budget) {
        super(FXML);
        categoryBudgetCap.setText("$" + Double.toString(budget.getBudgetCap()));
        categoryName.setText(budget.toString());
        Image image = new Image(IMAGE_PATH);
        categoryIcon.setImage(image);
    }

}

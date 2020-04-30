package seedu.restaurant.ui.sales;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.ui.testutil.GuiTestAssert.assertStackPanelDisplaysRecord;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import guitests.guihandles.sales.RecordStackPanelHandle;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.sales.SalesRecord;
import seedu.restaurant.testutil.sales.RecordBuilder;
import seedu.restaurant.ui.GuiUnitTest;

//@@author HyperionNKJ
public class RecordStackPanelTest extends GuiUnitTest {

    @Test
    public void display() {
        // without ingredientUsed
        SalesRecord recordWithoutRequiredIngredients = new RecordBuilder().build();
        RecordStackPanel recordStackPanel = new RecordStackPanel(recordWithoutRequiredIngredients);
        uiPartRule.setUiPart(recordStackPanel);
        assertStackPanelDisplay(recordStackPanel, recordWithoutRequiredIngredients);

        // with ingredientUsed
        Map<IngredientName, Integer> ingredientUsed = new HashMap<>();
        ingredientUsed.put(new IngredientName("Egg"), 50);
        ingredientUsed.put(new IngredientName("Carrot"), 20);
        SalesRecord recordWithRequiredIngredients = new RecordBuilder().withIngredientUsed(ingredientUsed).build();
        recordStackPanel = new RecordStackPanel(recordWithRequiredIngredients);
        uiPartRule.setUiPart(recordStackPanel);
        assertStackPanelDisplay(recordStackPanel, recordWithRequiredIngredients);

    }
    @Test
    public void equals() {
        SalesRecord salesRecord = new RecordBuilder().build();
        RecordStackPanel recordStackPanel = new RecordStackPanel(salesRecord);

        // same record, same index -> returns true
        RecordStackPanel copy = new RecordStackPanel(salesRecord);
        assertTrue(recordStackPanel.equals(copy));

        // same object -> returns true
        assertTrue(recordStackPanel.equals(recordStackPanel));

        // null -> returns false
        assertFalse(recordStackPanel.equals(null));

        // different types -> returns false
        assertFalse(recordStackPanel.equals(0));

        // different record date, same index -> returns false
        SalesRecord differentRecord = new RecordBuilder().withDate("05-11-2018").build();
        assertFalse(recordStackPanel.equals(new RecordStackPanel(differentRecord)));

        // different record name, same index -> returns false
        differentRecord = new RecordBuilder().withName("Different Name").build();
        assertFalse(recordStackPanel.equals(new RecordStackPanel(differentRecord)));
    }

    /**
     * Asserts that {@code recordStackPanel} displays the details of {@code expectedRecord} correctly.
     */
    private void assertStackPanelDisplay(RecordStackPanel recordStackPanel, SalesRecord expectedRecord) {
        guiRobot.pauseForHuman();
        RecordStackPanelHandle recordStackPanelHandle = new RecordStackPanelHandle(recordStackPanel.getRoot());
        // verify record details are displayed correctly
        assertStackPanelDisplaysRecord(expectedRecord, recordStackPanelHandle);
    }
}

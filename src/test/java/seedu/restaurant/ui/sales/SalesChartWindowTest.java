package seedu.restaurant.ui.sales;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_DATE_RECORD_ONE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_DATE_RECORD_THREE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_DATE_RECORD_TWO;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import seedu.restaurant.model.sales.Date;
import seedu.restaurant.ui.GuiUnitTest;

public class SalesChartWindowTest extends GuiUnitTest {

    private SalesChartWindow salesChartWindow;
    //private SalesChartWindowHandle salesChartWindowHandle; // Handle to be used in the future

    @Before
    public void setUp() throws Exception {
        Map<Date, Double> salesData = new TreeMap<>();
        salesData.put(new Date(VALID_DATE_RECORD_TWO), 200.0);
        salesData.put(new Date(VALID_DATE_RECORD_THREE), 300.0);
        salesData.put(new Date(VALID_DATE_RECORD_ONE), 100.0);
        guiRobot.interact(() -> salesChartWindow = new SalesChartWindow(salesData));
        FxToolkit.registerStage(salesChartWindow::getRoot);
        //salesChartWindowHandle = new SalesChartWindowHandle(salesChartWindow.getRoot(), salesData);
    }

    @Test
    public void isShowing_salesChartWindowIsShowing_returnsTrue() {
        guiRobot.interact(salesChartWindow::show);
        assertTrue(salesChartWindow.isShowing());
    }

    @Test
    public void isShowing_salesChartWindowIsHiding_returnsFalse() {
        assertFalse(salesChartWindow.isShowing());
    }
}

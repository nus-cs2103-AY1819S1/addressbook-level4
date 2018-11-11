package seedu.restaurant.ui.sales;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import seedu.restaurant.ui.GuiUnitTest;

public class SalesRankingWindowTest extends GuiUnitTest {

    private SalesRankingWindow salesRankingWindow;
    //private SalesRankingWindowHandle salesRankingWindowHandle; // Handle to be used in the future

    @Before
    public void setUp() throws Exception {
        Map<String, String> salesRanking = new LinkedHashMap<>();
        salesRanking.put("abc", "def");
        salesRanking.put("123", "456");
        salesRanking.put("abc", "456");
        salesRanking.put("123", "def");
        guiRobot.interact(() -> salesRankingWindow = new SalesRankingWindow(salesRanking));
        FxToolkit.registerStage(salesRankingWindow::getRoot);
        //salesRankingWindowHandle = new SalesRankingWindowHandle(salesRankingWindow.getRoot(), salesRanking);
    }

    @Test
    public void isShowing_salesRankingWindowIsShowing_returnsTrue() {
        guiRobot.interact(salesRankingWindow::show);
        assertTrue(salesRankingWindow.isShowing());
    }

    @Test
    public void isShowing_salesRankingWindowIsHiding_returnsFalse() {
        assertFalse(salesRankingWindow.isShowing());
    }
}

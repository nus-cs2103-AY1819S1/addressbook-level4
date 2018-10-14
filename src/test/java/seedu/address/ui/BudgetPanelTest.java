package seedu.address.ui;

import guitests.guihandles.BudgetPanelHandle;
import javafx.animation.Timeline;
import seedu.address.commons.events.ui.UpdateBudgetPanelEvent;
import seedu.address.model.budget.Budget;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EventsUtil.postNow;



public class BudgetPanelTest extends GuiUnitTest {
    private BudgetPanel budgetPanel;
    private BudgetPanelHandle budgetPanelHandle;
    private Timeline timeline;

    @Before
    public void setUp() throws InterruptedException {
        budgetPanel = new BudgetPanel(new Budget(0,0));
        Thread.sleep(1000);
        uiPartRule.setUiPart(budgetPanel);

        timeline = budgetPanel.getTimeline();
        budgetPanelHandle = new BudgetPanelHandle(budgetPanel.getRoot());
    }

    @Test
    public void addPanel_UpdateBudgetPanelEventPosted() {
        run(100, 200);
        assertTrue(budgetPanelHandle.isColorGreen());
    }

    @Test
    public void colorChangeToRedWhenOverBudget_UpdateBudgetPanelEventPosted () {
        run(200, 100);
        assertTrue(budgetPanelHandle.isColorRed());
    }

    private void run(double expense, double budget) {
        postNow(generateUpdateBudgetPanelEvent(expense, budget));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(budgetPanelHandle.isExpenseCorrect(String.format("%.2f", expense)));
        assertTrue(budgetPanelHandle.isBudgetBarProgressAccurate(expense/budget));
        assertTrue(budgetPanelHandle.isBudgetCorrect(String.format("%.2f", budget)));
    }

    private UpdateBudgetPanelEvent generateUpdateBudgetPanelEvent(double expense, double budget) {
        return new UpdateBudgetPanelEvent(new Budget(budget, expense));
    }

}

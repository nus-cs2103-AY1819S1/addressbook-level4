package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import seedu.address.commons.events.ui.UpdateBudgetPanelEvent;
import seedu.address.model.budget.Budget;

import com.google.common.eventbus.Subscribe;

//@@author snookerballs
/**
 * Panel containing the budget information.
 */
public class BudgetPanel extends UiPart<Region> {
    private static final String FXML = "BudgetPanel.fxml";
    
    @FXML
    private ProgressBar budgetBar;
    
    @FXML
    private Text budgetDisplay;
    
    @FXML
    private Text expenseDisplay;
    
    public BudgetPanel (Budget budget) {
        super(FXML);
        update(budget);
    }

    /**
     * Update the budgetDisplay, expenseDisplay and budgetBar
     * @param budget to update from
     */
    public void update(Budget budget) {
        double budgetCap = budget.getBudgetCap();
        double currentExpenses = budget.getCurrentExpenses();
        System.out.println(budgetCap);
        System.out.println(currentExpenses);
        
        updateBudgetCapTextDisplay(budgetCap);
        updateExpenseTextDisplay(currentExpenses);
        updateBudgetBar(budgetCap, currentExpenses);
    }
    
    /**
     * Update the text to display the correct budget
     * @param budgetCap 
     */
    public void updateBudgetCapTextDisplay(double budgetCap){
        this.budgetDisplay.setText("/ $" + String.format("%.2f", budgetCap));
    }

    /**
     * Update the text to display the correct expenses
     * @param expense 
     */
    public void updateExpenseTextDisplay(double expense){
        this.expenseDisplay.setText("$" + String.format("%.2f", expense));
    }

    /**
     * update the percentage of the progress bar
     * @param budgetCap 
     * @param currentExpenses
     */
    public void updateBudgetBar(double budgetCap, double currentExpenses){
        double currentPercentage = currentExpenses/budgetCap;
        budgetBar.setProgress(currentPercentage);
    }
}

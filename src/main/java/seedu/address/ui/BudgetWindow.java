package seedu.address.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.commons.events.ui.ExitBudgetWindowRequestEvent;
import seedu.address.logic.Logic;
import seedu.address.model.UserPrefs;
import seedu.address.model.cca.CcaName;

//@@author ericyjw
/**
 * The Budget Window. Displays the CCAs available and the budget information of each CCA
 *
 * @author ericyjw
 */
public class BudgetWindow extends UiPart<Stage> {

    private static final String FXML = "BudgetWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Logic logic;

    private boolean isShowing;

    // Independent Ui parts residing in this Ui container
    private BudgetBrowserPanel budgetBrowserPanel;
    private CcaListPanel ccaListPanel;
    private UserPrefs prefs;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private MenuItem budgetMenuItem;

    @FXML
    private StackPane ccaListPanelPlaceholder;

    public BudgetWindow(Stage root) {
        super(FXML, root);
    }

    public BudgetWindow(Logic logic, UserPrefs prefs) {
        this(new Stage());
        this.prefs = prefs;
        this.logic = logic;
        this.isShowing = false;
    }


    /**
     * Fills up all the placeholders of this window.
     * @param ccaName the name of the CCA to be viewed
     */
    void fillInnerParts(CcaName ccaName) {
        if(Optional.ofNullable(ccaName).isPresent()) {
            budgetBrowserPanel = new BudgetBrowserPanel(ccaName);
        } else {
            budgetBrowserPanel = new BudgetBrowserPanel();
        }
        browserPlaceholder.getChildren().add(budgetBrowserPanel.getRoot());

        ccaListPanel = new CcaListPanel(logic.getFilteredCcaList());
        ccaListPanelPlaceholder.getChildren().add(ccaListPanel.getRoot());
    }

    /**
     * Closes the budget window.
     */
    @FXML
    private void handleExit() {
        this.isShowing = false;
        raise(new ExitBudgetWindowRequestEvent());
    }

    public CcaListPanel getCcaListPanel() {
        return ccaListPanel;
    }

    void releaseResources() {
        budgetBrowserPanel.freeResources();
    }

    /**
     * Show budget window
     * @param ccaName
     */
    public void show(CcaName ccaName) {
        logger.fine("Showing budget list of the hostel.");
        getRoot().show();
        fillInnerParts(ccaName);
        this.isShowing = true;
    }

    /**
     * Returns true if the budget window is currently being shown.
     */
    public boolean isShowing() {
        return this.isShowing;
    }

    /**
     * Focuses on the budget window.
     */
    public void focus(CcaName ccaName) {
        fillInnerParts(ccaName);
        getRoot().requestFocus();
    }

}

package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.accounting.Debt;
/**
 * An UI component that displays information of a {@code Debt}.
 */
public class DebtCard extends UiPart<Region> {

    private static final String FXML = "DebtListCard.fxml";

    public final Debt debt;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label creditor;
    @FXML
    private Label debtor;
    @FXML
    private Label amount;
    @FXML
    private Label status;

    public DebtCard(Debt debt, int displayedIndex) {
        super(FXML);
        this.debt = debt;
        id.setText(displayedIndex + ". ");
        creditor.setText("Creditor: " + debt.getCreditor().getUsername().toString());
        debtor.setText("Debtor: " + debt.getDebtor().getUsername().toString());
        amount.setText("Amount: " + String.valueOf(debt.getAmount().toDouble()));
        status.setText("Status: " + debt.getDebtStatus().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DebtCard)) {
            return false;
        }

        // state check
        DebtCard card = (DebtCard) other;
        return debt.equals(card.debt);
    }
}


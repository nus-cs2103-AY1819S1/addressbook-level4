package seedu.address.model.accounting;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Class representing the debt ID.
 */
public class DebtId {

    public static final String MESSAGE_DEBTID_CONSTRAINTS =
            "Debt ID should only contain numbers, and it should be 14 digits long";
    public static final String DEBTID_VALIDATION_REGEX = "\\d{14}";
    private String id;

    public DebtId() {
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddHHmmssSS");
        String strNow = ft.format(now);
        this.id = strNow;
    }

    public DebtId(String debtId) {
        this.id = debtId;
    }

    @Override
    public String toString() {
        return this.id;
    }

    public static boolean isValidDebtId(String test) {
        return test.matches(DEBTID_VALIDATION_REGEX);
    }
}

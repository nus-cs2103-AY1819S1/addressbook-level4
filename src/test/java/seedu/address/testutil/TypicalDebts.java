package seedu.address.testutil;

import static seedu.address.testutil.TypicalUsers.getTypicalUsers;

import seedu.address.model.accounting.Amount;
import seedu.address.model.accounting.Debt;

/**
 * A utility class containing a list of {@code Debt} objects to be used in tests.
 */

public class TypicalDebts {

    public static final Debt DEBT_A =
            new Debt(getTypicalUsers().get(0), getTypicalUsers().get(1), new Amount("18"));

    public static final Debt DEBT_B =
            new Debt(getTypicalUsers().get(1), getTypicalUsers().get(0), new Amount("7"));

    public static final Debt DEBT_C =
            new Debt(getTypicalUsers().get(1), getTypicalUsers().get(2), new Amount("9"));


    public TypicalDebts() {}
}

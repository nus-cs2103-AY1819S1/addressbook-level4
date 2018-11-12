package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OUTSTANDING_BASKETBALL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPENT_BASKETBALL;
import static seedu.address.testutil.TypicalEntries.getTransactionEmpty;
import static seedu.address.testutil.TypicalEntries.getTransactionFourEntries;
import static seedu.address.testutil.TypicalEntries.getTransactionTwoEntries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.BudgetBook;
import seedu.address.model.cca.Cca;

//@@author javenseow ericyjw
/**
 * A utility class containing a list of {@code Cca} objects to be used in tests.
 */
public class TypicalCcas {
    public static final Cca BASKETBALL =
        new CcaBuilder()
            .withCcaName("BASKETBALL")
            .withHead("Carl Kurz")
            .withViceHead("Daniel Meier")
            .withBudget(Integer.valueOf(VALID_BUDGET_BASKETBALL))
            .withSpent(Integer.valueOf(VALID_SPENT_BASKETBALL))
            .withOutstanding(Integer.valueOf(VALID_OUTSTANDING_BASKETBALL))
            .withTransaction(getTransactionTwoEntries())
            .build();

    public static final Cca TRACK =
        new CcaBuilder()
            .withCcaName("track")
            .withHead("Alice Pauline")
            .withViceHead("Benson Meier")
            .withBudget(500)
            .withSpent(300)
            .withOutstanding(200)
            .withTransaction(getTransactionFourEntries())
            .build();
    public static final Cca BADMINTON =
        new CcaBuilder()
            .withCcaName("Badminton")
            .withHead("Fiona Kunz")
            .withViceHead("George Best")
            .withBudget(500)
            .withSpent(0)
            .withOutstanding(600)
            .withTransaction(getTransactionTwoEntries())
            .build();
    public static final Cca FLOORBALL =
        new CcaBuilder()
            .withCcaName("FLOORBALL")
            .withHead("-")
            .withViceHead("-")
            .withBudget(600)
            .withSpent(0)
            .withOutstanding(0)
            .withTransaction(getTransactionEmpty())
            .build();

    // Manually added Cca
    public static final Cca HOCKEY = new CcaBuilder()
        .withCcaName("Hockey")
        .withBudget(400)
        .build();
    public static final Cca SOFTBALL = new CcaBuilder()
        .withCcaName("softball")
        .withBudget(450)
        .withHead("Bob")
        .build();

    private TypicalCcas() {} // prevents instantiation

    /**
     * Returns a {@code BudgetBook} with all the typical Ccas.
     */
    public static BudgetBook getTypicalBudgetBook() {
        BudgetBook bb = new BudgetBook();
        for (Cca cca : getTypicalCcas()) {
            bb.addCca(cca);
        }
        return bb;
    }

    public static List<Cca> getTypicalCcas() {
        return new ArrayList<>(Arrays.asList(TRACK, BADMINTON, BASKETBALL));
    }
}

package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.BudgetBook;
import seedu.address.model.cca.Cca;

/**
 * A utility class containing a list of {@code Cca} objects to be used in tests.
 */
//@@author javenseow
public class TypicalCcas {
    public static final Cca TRACK = new CcaBuilder().withCcaName("track").withHead(ALICE).withViceHead(DANIEL)
            .withBudget(500).build();
    public static final Cca BADMINTON = new CcaBuilder().withCcaName("badminton").withHead(BENSON).withViceHead(CARL)
            .withBudget(500).build();

    private TypicalCcas() {} // prevents instantiation

    /**
     * Returns a {@code BudgetBook} with all the typical ccas.
     */
    public static BudgetBook getTypicalBudgetBook() {
        BudgetBook bb = new BudgetBook();
        for (Cca cca : getTypicalCcas()) {
            bb.addCca(cca);
        }
        return bb;
    }

    public static List<Cca> getTypicalCcas() {
        return new ArrayList<>(Arrays.asList(TRACK, BADMINTON));
    }
}

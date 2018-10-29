package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_EARTH_FLAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_PM_OF_SG;
import static seedu.address.testutil.TypicalCards.Q_FLAT_EARTH;
import static seedu.address.testutil.TypicalCards.Q_FORCE_FORMULA;

import seedu.address.model.test.Attempt;

/**
 * A utility class containing a list of {@code Attempts} objects to be used in tests.
 */
public class TypicalAttempts {

    public static final Attempt FLAT_EARTH_CORRECT_ATTEMPT = new Attempt(Q_FLAT_EARTH,
            VALID_ANSWER_EARTH_FLAT, true);

    public static final Attempt FLAT_EARTH_WRONG_ATTEMPT = new Attempt(Q_FLAT_EARTH,
            VALID_ANSWER_GIT_COMMIT, false);

    public static final Attempt FORCE_CORRECT_ATTEMPT = new Attempt(Q_FORCE_FORMULA,
            "mass * acceleration", true);
    public static final Attempt FORCE_WRONG_ATTEMPT = new Attempt(Q_FORCE_FORMULA,
            VALID_ANSWER_PM_OF_SG, false);
}

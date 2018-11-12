package seedu.modsuni.model.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_EMPLOY_DATE_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_SALARY_AMY;
import static seedu.modsuni.testutil.TypicalAdmins.ALICE;
import static seedu.modsuni.testutil.TypicalAdmins.BRAD;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.testutil.AdminBuilder;

public class AdminTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameAdmin() {
        // same object -> returns true
        assertTrue(ALICE.isSameAdmin(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameAdmin(null));

        // different salary and employ date -> returns false
        Admin editedAlice = new AdminBuilder(ALICE)
                .withSalary(VALID_SALARY_AMY)
                .withEmployedDate(VALID_EMPLOY_DATE_AMY)
                .build();
        assertFalse(ALICE.isSameAdmin(editedAlice));

        // different name -> returns false
        editedAlice = new AdminBuilder(ALICE).withName(VALID_NAME_AMY).build();
        assertFalse(ALICE.isSameAdmin(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Admin aliceCopy = new AdminBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BRAD));

        // different name -> returns false
        Admin editedAlice = new AdminBuilder(ALICE).withName(VALID_NAME_AMY).build();
        assertFalse(ALICE.equals(editedAlice));

        // different salary -> returns false
        editedAlice = new AdminBuilder(ALICE).withSalary(VALID_SALARY_AMY).build();
        assertFalse(ALICE.equals(editedAlice));

        // different employ date -> returns false
        editedAlice = new AdminBuilder(ALICE).withEmployedDate(VALID_EMPLOY_DATE_AMY).build();
        assertFalse(ALICE.equals(editedAlice));

    }

    @Test
    public void testHashCode() {
        // same admin
        Admin max = new AdminBuilder().withName("Max").build();
        Admin maxCopy = new AdminBuilder(max).build();
        assertTrue(max.hashCode() == maxCopy.hashCode());

        // diff admin
        Admin seb = new AdminBuilder().withName("Seb").build();
        assertFalse(max.hashCode() == seb.hashCode());
    }

    @Test
    public void toDisplayUi() {
        // same UI details
        Admin max = new AdminBuilder().withUsername("Max").build();
        Admin maxCopy = new AdminBuilder(max).build();
        assertTrue(max.toDisplayUi().equals(maxCopy.toDisplayUi()));

        // diff UI details
        Admin seb = new AdminBuilder().withUsername("Seb").build();
        assertFalse(max.toDisplayUi().equals(seb.toDisplayUi()));
    }
}

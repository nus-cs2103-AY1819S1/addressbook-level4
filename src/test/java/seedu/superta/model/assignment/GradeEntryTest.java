package seedu.superta.model.assignment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.superta.testutil.TypicalSuperTaClient.ALICE;
import static seedu.superta.testutil.TypicalSuperTaClient.BENSON;

import org.junit.Test;

public class GradeEntryTest {

    @Test
    public void equalsItself_success() {
        GradeEntry entry = new GradeEntry(BENSON.getStudentId(), 10.0);
        assertEquals(entry, entry);
    }

    @Test
    public void equalsOtherWithSameParams_success() {
        GradeEntry entry = new GradeEntry(BENSON.getStudentId(), 10.0);
        GradeEntry entry2 = new GradeEntry(BENSON.getStudentId(), 10.0);
        assertEquals(entry, entry2);
    }

    @Test
    public void doesNotEqualUnequal_success() {
        GradeEntry entry = new GradeEntry(BENSON.getStudentId(), 10.0);
        GradeEntry entry2 = new GradeEntry(ALICE.getStudentId(), 10.0);
        assertNotEquals(entry, entry2);

        entry = new GradeEntry(BENSON.getStudentId(), 10.0);
        entry2 = new GradeEntry(BENSON.getStudentId(), 15.0);
        assertNotEquals(entry, entry2);
    }

    @Test
    public void toString_success() {
        GradeEntry entry = new GradeEntry(BENSON.getStudentId(), 10.0);
        assertNotNull(entry);
    }

}

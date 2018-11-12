package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.HARRY;

import org.junit.Test;

import seedu.address.model.Ssenisub;
import seedu.address.testutil.SsenisubBuilder;

public class SortDeptCommandTest {

    @Test
    public void execute_sortList() {
        Ssenisub ssenisub = new SsenisubBuilder().withPerson(BENSON).withPerson(ALICE).withPerson(CARL).build();
        Ssenisub expectedSsenisub = new SsenisubBuilder().withPerson(ALICE).withPerson(BENSON).withPerson(CARL).build();
        ssenisub.sortByDept();
        assertEquals(ssenisub, expectedSsenisub);
    }

    @Test
    public void execute_sortListWithFavourite() {

        // typical person Harry is set to be favourited
        Ssenisub ssenisub = new SsenisubBuilder().withPerson(BENSON).withPerson(HARRY).withPerson(ALICE)
                .withPerson(CARL).build();
        Ssenisub expectedSsenisub = new SsenisubBuilder().withPerson(ALICE).withPerson(BENSON).withPerson(HARRY)
                .withPerson(CARL).build();

        ssenisub.sortByDept();
        assertEquals(ssenisub, expectedSsenisub);

        ssenisub.sortByDept();
        assertEquals(ssenisub, expectedSsenisub);
    }
}

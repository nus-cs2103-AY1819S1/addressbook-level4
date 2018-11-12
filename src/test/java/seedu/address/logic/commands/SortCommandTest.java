package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.HARRY;

import org.junit.Test;

import seedu.address.model.Ssenisub;
import seedu.address.testutil.SsenisubBuilder;

public class SortCommandTest {

    @Test
    public void execute_sortList() {
        Ssenisub ssenisub = new SsenisubBuilder().withPerson(BOB).withPerson(ALICE).build();
        Ssenisub expectedSsenisub = new SsenisubBuilder().withPerson(ALICE).withPerson(BOB).build();
        ssenisub.sortByName();
        assertEquals(ssenisub, expectedSsenisub);
    }

    @Test
    public void execute_sortListWithFavourite() {

        // typical person Carl is set to be favourited
        Ssenisub ssenisub = new SsenisubBuilder().withPerson(BOB).withPerson(ALICE).withPerson(HARRY).build();
        Ssenisub expectedSsenisub = new SsenisubBuilder().withPerson(HARRY).withPerson(ALICE)
                .withPerson(BOB).build();

        ssenisub.sortByName();
        assertEquals(ssenisub, expectedSsenisub);
    }
}

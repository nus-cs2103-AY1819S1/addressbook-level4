package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.JASON;
import static seedu.address.testutil.TypicalPersons.KEVIN;
import static seedu.address.testutil.TypicalPersons.MICHAEL;

import org.junit.Test;

import seedu.address.model.Ssenisub;
import seedu.address.testutil.SsenisubBuilder;

public class SortRatingDownCommandTest {

    @Test
    public void execute_sortList() {

        Ssenisub ssenisub = new SsenisubBuilder().withPerson(KEVIN).withPerson(MICHAEL).withPerson(JASON).build();
        Ssenisub expectedSsenisub = new SsenisubBuilder().withPerson(MICHAEL).withPerson(KEVIN)
                .withPerson(JASON).build();
        ssenisub.sortByRatingDown();
        assertEquals(ssenisub, expectedSsenisub);
    }
}

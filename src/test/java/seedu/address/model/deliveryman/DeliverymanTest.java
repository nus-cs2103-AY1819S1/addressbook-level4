package seedu.address.model.deliveryman;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalDeliverymen.CHIKAO;
import static seedu.address.testutil.TypicalDeliverymen.YINJING;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.DeliverymanBuilder;

public class DeliverymanTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameDeliveryman() {
        // same object -> returns true
        assertTrue(CHIKAO.isSameDeliveryman(CHIKAO));

        // null -> returns false
        assertFalse(CHIKAO.isSameDeliveryman(null));

        // different name -> returns false
        Deliveryman differentChikao = new DeliverymanBuilder(CHIKAO).withName(VALID_NAME_BOB).build();
        assertFalse(CHIKAO.isSameDeliveryman(differentChikao));

        // completely different deliveryman -> returns false
        assertFalse(CHIKAO.isSameDeliveryman(YINJING));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Deliveryman chikaoCopy = new DeliverymanBuilder(CHIKAO).build();
        assertTrue(CHIKAO.equals(chikaoCopy));

        // same deliveryman -> returns true
        assertTrue(CHIKAO.equals(CHIKAO));

        // null returns false
        assertFalse(CHIKAO.equals(null));

        // different type -> returns false
        assertFalse(CHIKAO.equals(5));

        // different deliveryman -> returns false
        assertFalse(CHIKAO.equals(YINJING));
    }
}

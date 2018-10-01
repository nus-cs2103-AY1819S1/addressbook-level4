package seedu.address.model.deliveryman;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalDeliverymen.MANIKA;
import static seedu.address.testutil.TypicalDeliverymen.getTypicalDeliverymenList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.DeliverymanBuilder;

public class DeliverymenListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final DeliverymenList deliverymenList = new DeliverymenList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), deliverymenList.getDeliverymenList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        deliverymenList.resetData(null);
    }

    @Test
    public void resetData_withValidReadyOnlyDeliverymenList_replacesData() {
        DeliverymenList newData = getTypicalDeliverymenList();
        deliverymenList.resetData(newData);
        assertEquals(newData, deliverymenList);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Deliveryman editedManika = new DeliverymanBuilder(MANIKA).withName(VALID_NAME_BOB)
            .build();
        List<Deliveryman> newDeliverymen = Arrays.asList(MANIKA, editedManika);
        DeliverymenListStub newData = new DeliverymenListStub(newDeliverymen);

        thrown.expect(DuplicatePersonException.class);
        deliverymenList.resetData(newData);
    }

    @Test
    public void hasDeliveryman_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        deliverymenList.hasDeliveryman(null);
    }

    @Test
    public void hasDeliveryman_personNotInAddressBook_returnsFalse() {
        assertFalse(deliverymenList.hasDeliveryman(MANIKA));
    }

    @Test
    public void hasDeliveryman_deliverymanInDeliverymenList_returnsTrue() {
        deliverymenList.addDeliveryman(MANIKA);
        assertTrue(deliverymenList.hasDeliveryman(MANIKA));
    }

    @Test
    public void getDeliverymenList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        deliverymenList.getDeliverymenList().remove(0);
    }

    /**
     * A stub DeliverymenList whose deliverymen list can violate interface constraints.
     */
    private static class DeliverymenListStub extends DeliverymenList {
        private final ObservableList<Deliveryman> deliverymen = FXCollections.observableArrayList();

        DeliverymenListStub(Collection<Deliveryman> deliverymen) {
            this.deliverymen.setAll(deliverymen);
        }

        @Override
        public ObservableList<Deliveryman> getDeliverymenList() {
            return deliverymen;
        }
    }
}

package seedu.inventory.model.purchaseorder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_SUPPLIER_SONY;
import static seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder.LGPO;
import static seedu.inventory.testutil.purchaseorder.TypicalPurchaseOrder.SAMSUNGNOTEPO;

import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.model.purchaseorder.exceptions.PurchaseOrderNotFoundException;
import seedu.inventory.testutil.purchaseorder.PurchaseOrderBuilder;

public class NonUniquePurchaseOrderListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final NonUniquePurchaseOrderList nonUniquePoList = new NonUniquePurchaseOrderList();

    @Test
    public void contains_nullPurchaseOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        nonUniquePoList.contains(null);
    }

    @Test
    public void contains_purchaseOrderNotInList_returnsFalse() {
        assertFalse(nonUniquePoList.contains(LGPO));
    }

    @Test
    public void contains_purchaseOrderInList_returnsTrue() {
        nonUniquePoList.add(LGPO);
        assertTrue(nonUniquePoList.contains(LGPO));
    }

    @Test
    public void contains_purchaseOrderWithSameIdentityFieldsInList_returnsTrue() {
        nonUniquePoList.add(LGPO);
        PurchaseOrder editedPo = new PurchaseOrderBuilder(LGPO).withSupplier(VALID_SUPPLIER_SONY).build();
        assertTrue(nonUniquePoList.contains(editedPo));
    }

    @Test
    public void add_nullPurchaseOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        nonUniquePoList.add(null);
    }

    @Test
    public void setPurchaseOrder_nullTargetPurchaseOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        nonUniquePoList.setPurchaseOrder(null, LGPO);
    }

    @Test
    public void setPurchaseOrder_nullEditedPurchaseOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        nonUniquePoList.setPurchaseOrder(LGPO, null);
    }

    @Test
    public void setPurchaseOrder_targetPurchaseOrderNotInList_throwsPurchaseOrderNotFoundException() {
        thrown.expect(PurchaseOrderNotFoundException.class);
        nonUniquePoList.setPurchaseOrder(LGPO, LGPO);
    }

    @Test
    public void remove_nullPurchaseOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        nonUniquePoList.remove(null);
    }

    @Test
    public void remove_purchaseOrderDoesNotExist_throwsPurchaseOrderNotFoundException() {
        thrown.expect(PurchaseOrderNotFoundException.class);
        nonUniquePoList.remove(LGPO);
    }

    @Test
    public void remove_existingPurchaseOrder_removesPurchaseOrder() {
        nonUniquePoList.add(LGPO);
        nonUniquePoList.remove(LGPO);
        NonUniquePurchaseOrderList expectednonUniquePoList = new NonUniquePurchaseOrderList();
        assertEquals(expectednonUniquePoList, nonUniquePoList);
    }

    @Test
    public void setPurchaseOrders_nullNonUniquePoList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        nonUniquePoList.setPurchaseOrders((NonUniquePurchaseOrderList) null);
    }

    @Test
    public void setPurchaseOrders_nonUniquePoList_replacesOwnListWithProvidednonUniquePoList() {
        nonUniquePoList.add(LGPO);
        NonUniquePurchaseOrderList expectedNonUniquePoList = new NonUniquePurchaseOrderList();
        expectedNonUniquePoList.add(SAMSUNGNOTEPO);
        nonUniquePoList.setPurchaseOrders(expectedNonUniquePoList);
        assertEquals(expectedNonUniquePoList, nonUniquePoList);
    }

    @Test
    public void setPurchaseOrders_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        nonUniquePoList.setPurchaseOrders((List<PurchaseOrder>) null);
    }

    @Test
    public void setPurchaseOrders_list_replacesOwnListWithProvidedList() {
        nonUniquePoList.add(LGPO);
        List<PurchaseOrder> purchaseOrderList = Collections.singletonList(SAMSUNGNOTEPO);
        nonUniquePoList.setPurchaseOrders(purchaseOrderList);
        NonUniquePurchaseOrderList expectednonUniquePoList = new NonUniquePurchaseOrderList();
        expectednonUniquePoList.add(SAMSUNGNOTEPO);
        assertEquals(expectednonUniquePoList, nonUniquePoList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        nonUniquePoList.asUnmodifiableObservableList().remove(0);
    }

}

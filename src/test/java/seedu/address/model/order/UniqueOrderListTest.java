package seedu.address.model.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FOOD_BURGER;
import static seedu.address.testutil.TypicalOrders.ALICE;
import static seedu.address.testutil.TypicalOrders.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.order.exceptions.OrderNotFoundException;
import seedu.address.testutil.OrderBuilder;

public class UniqueOrderListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueOrderList uniqueOrderList = new UniqueOrderList();

    @Test
    public void contains_nullOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOrderList.contains(null);
    }

    @Test
    public void contains_orderNotInList_returnsFalse() {
        assertFalse(uniqueOrderList.contains(ALICE));
    }

    @Test
    public void contains_orderInList_returnsTrue() {
        uniqueOrderList.add(ALICE);
        assertTrue(uniqueOrderList.contains(ALICE));
    }

    @Test
    public void contains_orderWithSameIdentityFieldsInList_returnsTrue() {
        uniqueOrderList.add(ALICE);
        Order editedAlice = new OrderBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withFood(VALID_FOOD_BURGER)
                .build();
        assertTrue(uniqueOrderList.contains(editedAlice));
    }

    @Test
    public void add_nullOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOrderList.add(null);
    }

    @Test
    public void add_duplicateOrder_throwsDuplicateOrderException() {
        uniqueOrderList.add(ALICE);
        thrown.expect(DuplicateOrderException.class);
        uniqueOrderList.add(ALICE);
    }

    @Test
    public void setOrder_nullTargetOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOrderList.setOrder(null, ALICE);
    }

    @Test
    public void setOrder_nullEditedOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOrderList.setOrder(ALICE, null);
    }

    @Test
    public void setOrder_targetOrderNotInList_throwsOrderNotFoundException() {
        thrown.expect(OrderNotFoundException.class);
        uniqueOrderList.setOrder(ALICE, ALICE);
    }

    @Test
    public void setOrder_editedOrderIsSameOrder_success() {
        uniqueOrderList.add(ALICE);
        uniqueOrderList.setOrder(ALICE, ALICE);
        UniqueOrderList expectedUniqueOrderList = new UniqueOrderList();
        expectedUniqueOrderList.add(ALICE);
        assertEquals(expectedUniqueOrderList, uniqueOrderList);
    }

    @Test
    public void setOrder_editedOrderHasSameIdentity_success() {
        uniqueOrderList.add(ALICE);
        Order editedAlice = new OrderBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withFood(VALID_FOOD_BURGER)
                .build();
        uniqueOrderList.setOrder(ALICE, editedAlice);
        UniqueOrderList expectedUniqueOrderList = new UniqueOrderList();
        expectedUniqueOrderList.add(editedAlice);
        assertEquals(expectedUniqueOrderList, uniqueOrderList);
    }

    @Test
    public void setOrder_editedOrderHasDifferentIdentity_success() {
        uniqueOrderList.add(ALICE);
        uniqueOrderList.setOrder(ALICE, BOB);
        UniqueOrderList expectedUniqueOrderList = new UniqueOrderList();
        expectedUniqueOrderList.add(BOB);
        assertEquals(expectedUniqueOrderList, uniqueOrderList);
    }

    @Test
    public void setOrder_editedOrderHasNonUniqueIdentity_throwsDuplicateOrderException() {
        uniqueOrderList.add(ALICE);
        uniqueOrderList.add(BOB);
        thrown.expect(DuplicateOrderException.class);
        uniqueOrderList.setOrder(ALICE, BOB);
    }

    @Test
    public void remove_nullOrder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOrderList.remove(null);
    }

    @Test
    public void remove_orderDoesNotExist_throwsOrderNotFoundException() {
        thrown.expect(OrderNotFoundException.class);
        uniqueOrderList.remove(ALICE);
    }

    @Test
    public void remove_existingOrder_removesOrder() {
        uniqueOrderList.add(ALICE);
        uniqueOrderList.remove(ALICE);
        UniqueOrderList expectedUniqueOrderList = new UniqueOrderList();
        assertEquals(expectedUniqueOrderList, uniqueOrderList);
    }

    @Test
    public void setOrder_nullUniqueOrderList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOrderList.setOrder((UniqueOrderList) null);
    }

    @Test
    public void setOrder_uniqueOrderList_replacesOwnListWithProvidedUniqueOrderList() {
        uniqueOrderList.add(ALICE);
        UniqueOrderList expectedUniqueOrderList = new UniqueOrderList();
        expectedUniqueOrderList.add(BOB);
        uniqueOrderList.setOrder(expectedUniqueOrderList);
        assertEquals(expectedUniqueOrderList, uniqueOrderList);
    }

    @Test
    public void setOrder_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOrderList.setOrder((List<Order>) null);
    }

    @Test
    public void setOrder_list_replacesOwnListWithProvidedList() {
        uniqueOrderList.add(ALICE);
        List<Order> orderList = Collections.singletonList(BOB);
        uniqueOrderList.setOrder(orderList);
        UniqueOrderList expectedUniqueOrderList = new UniqueOrderList();
        expectedUniqueOrderList.add(BOB);
        assertEquals(expectedUniqueOrderList, uniqueOrderList);
    }

    @Test
    public void setOrder_listWithDuplicateOrders_throwsDuplicatePersonException() {
        List<Order> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateOrderException.class);
        uniqueOrderList.setOrder(listWithDuplicatePersons);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueOrderList.asUnmodifiableObservableList().remove(0);
    }
}

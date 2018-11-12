package seedu.address.model.deliveryman;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalDeliverymen.RAJUL;
import static seedu.address.testutil.TypicalDeliverymen.YINJING;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.common.exceptions.DuplicatePersonException;
import seedu.address.model.common.exceptions.PersonNotFoundException;

public class UniqueDeliverymenListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueDeliverymenList uniqueDeliverymenList = new UniqueDeliverymenList();

    @Test
    public void contains_nullDeliverymen_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDeliverymenList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueDeliverymenList.contains(RAJUL));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueDeliverymenList.add(RAJUL);
        assertTrue(uniqueDeliverymenList.contains(RAJUL));
    }

    @Test
    public void add_nullDeliveryman_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDeliverymenList.add(null);
    }

    @Test
    public void add_duplicateDeliveryman_throwsDuplicateDeliverymanException() {
        uniqueDeliverymenList.add(RAJUL);
        thrown.expect(DuplicatePersonException.class);
        uniqueDeliverymenList.add(RAJUL);
    }

    @Test
    public void setDeliveryman_nullTargetDeliveryman_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDeliverymenList.setDeliveryman(null, RAJUL);
    }

    @Test
    public void setDeliveryman_nullEditedDeliveryman_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDeliverymenList.setDeliveryman(RAJUL, null);
    }

    @Test
    public void setDeliveryman_targetDeliverymanNotInList_throwsDeliverymanNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        uniqueDeliverymenList.setDeliveryman(RAJUL, RAJUL);
    }

    @Test
    public void setDeliveryman_editedDeliverymanIsSameDeliveryman_success() {
        uniqueDeliverymenList.add(RAJUL);
        uniqueDeliverymenList.setDeliveryman(RAJUL, RAJUL);
        UniqueDeliverymenList expectedUniqueDeliverymenList = new UniqueDeliverymenList();
        expectedUniqueDeliverymenList.add(RAJUL);
        assertEquals(expectedUniqueDeliverymenList, uniqueDeliverymenList);
    }

    @Test
    public void setDeliveryman_editedDeliverymanHasDifferentIdentity_success() {
        uniqueDeliverymenList.add(RAJUL);
        uniqueDeliverymenList.setDeliveryman(RAJUL, YINJING);
        UniqueDeliverymenList expectedUniqueDeliverymenList = new UniqueDeliverymenList();
        expectedUniqueDeliverymenList.add(YINJING);
        assertEquals(expectedUniqueDeliverymenList, uniqueDeliverymenList);
    }

    @Test
    public void setDeliveryman_editedDeliverymanHasNonUniqueIdentity_throwsDuplicateDeliverymanException() {
        uniqueDeliverymenList.add(RAJUL);
        uniqueDeliverymenList.add(YINJING);
        thrown.expect(DuplicatePersonException.class);
        uniqueDeliverymenList.setDeliveryman(RAJUL, YINJING);
    }

    @Test
    public void remove_nullDeliveryman_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDeliverymenList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsDeliverymanNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        uniqueDeliverymenList.remove(RAJUL);
    }

    @Test
    public void remove_existingDeliveryman_removesDeliveryman() {
        uniqueDeliverymenList.add(RAJUL);
        uniqueDeliverymenList.remove(RAJUL);
        UniqueDeliverymenList expectedUniqueDeliverymenList = new UniqueDeliverymenList();
        assertEquals(expectedUniqueDeliverymenList, uniqueDeliverymenList);
    }

    @Test
    public void setDeliverymen_nullUniqueDeliverymenList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDeliverymenList.setDeliverymen((UniqueDeliverymenList) null);
    }

    @Test
    public void setDeliverymen_uniqueDeliverymenList_replacesOwnListWithProvidedUniqueDeliverymenList() {
        uniqueDeliverymenList.add(RAJUL);
        UniqueDeliverymenList expectedUniqueDeliverymenList = new UniqueDeliverymenList();
        expectedUniqueDeliverymenList.add(YINJING);
        uniqueDeliverymenList.setDeliverymen(expectedUniqueDeliverymenList);
        assertEquals(expectedUniqueDeliverymenList, uniqueDeliverymenList);
    }

    @Test
    public void setDeliverymen_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueDeliverymenList.setDeliverymen((List<Deliveryman>) null);
    }

    @Test
    public void setDeliverymen_list_replacesOwnListWithProvidedList() {
        uniqueDeliverymenList.add(RAJUL);
        List<Deliveryman> personList = Collections.singletonList(YINJING);
        uniqueDeliverymenList.setDeliverymen(personList);
        UniqueDeliverymenList expectedUniqueDeliverymenList = new UniqueDeliverymenList();
        expectedUniqueDeliverymenList.add(YINJING);
        assertEquals(expectedUniqueDeliverymenList, uniqueDeliverymenList);
    }

    @Test
    public void setDeliverymen_listWithDuplicateDeliverymen_throwsDuplicateDeliverymanException() {
        List<Deliveryman> listWithDuplicateDeliverymen = Arrays.asList(RAJUL, RAJUL);
        thrown.expect(DuplicatePersonException.class);
        uniqueDeliverymenList.setDeliverymen(listWithDuplicateDeliverymen);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueDeliverymenList.asUnmodifiableObservableList().remove(0);
    }

}

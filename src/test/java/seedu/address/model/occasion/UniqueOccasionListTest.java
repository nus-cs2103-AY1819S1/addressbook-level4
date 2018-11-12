package seedu.address.model.occasion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONLOCATION_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_TAG_SLEEP;
import static seedu.address.testutil.TypicalOccasions.OCCASION_ONE;
import static seedu.address.testutil.TypicalOccasions.OCCASION_TWO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.occasion.exceptions.DuplicateOccasionException;
import seedu.address.model.occasion.exceptions.OccasionNotFoundException;
import seedu.address.testutil.OccasionBuilder;


public class UniqueOccasionListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueOccasionList uniqueOccasionList = new UniqueOccasionList(new ArrayList<>());

    @Test
    public void contains_nullOccasion_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOccasionList.contains(null);
    }

    @Test
    public void contains_occasionNotInList_returnsFalse() {
        assertFalse(uniqueOccasionList.contains(OCCASION_ONE));
    }

    @Test
    public void contains_occasionInList_returnsTrue() {
        uniqueOccasionList.add(OCCASION_ONE);
        assertTrue(uniqueOccasionList.contains(OCCASION_ONE));
    }

    @Test
    public void contains_occasionWithSameIdentityFieldsInList_returnsTrue() {
        uniqueOccasionList.add(OCCASION_ONE);
        Occasion editedOccasionOne = new OccasionBuilder(OCCASION_ONE).withOccasionLocation(VALID_OCCASIONLOCATION_TWO)
                .withTags(VALID_TAG_SLEEP)
                .build();
        assertTrue(uniqueOccasionList.contains(editedOccasionOne));
    }

    @Test
    public void add_nullOccasion_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOccasionList.add(null);
    }

    @Test
    public void add_duplicateOccasion_throwsDuplicateOccasionException() {
        uniqueOccasionList.add(OCCASION_ONE);
        thrown.expect(DuplicateOccasionException.class);
        uniqueOccasionList.add(OCCASION_ONE);
    }

    @Test
    public void setOccasion_nullTargetOccasion_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOccasionList.setOccasion(null, OCCASION_ONE);
    }

    @Test
    public void setOccasion_nullEditedOccasion_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOccasionList.setOccasion(OCCASION_ONE, null);
    }

    @Test
    public void setOccasion_targetOccasionNotInList_throwsOccasionNotFoundException() {
        thrown.expect(OccasionNotFoundException.class);
        uniqueOccasionList.setOccasion(OCCASION_ONE, OCCASION_ONE);
    }

    @Test
    public void setOccasion_editedOccasionIsSameOccasion_success() {
        uniqueOccasionList.add(OCCASION_ONE);
        uniqueOccasionList.setOccasion(OCCASION_ONE, OCCASION_ONE);
        UniqueOccasionList expectedUniqueOccasionList = new UniqueOccasionList(new ArrayList<>());
        expectedUniqueOccasionList.add(OCCASION_ONE);
        assertEquals(expectedUniqueOccasionList, uniqueOccasionList);
    }

    @Test
    public void setOccasion_editedOccasionHasSameIdentity_success() {
        uniqueOccasionList.add(OCCASION_ONE);
        Occasion editedOccasionOne = new OccasionBuilder(OCCASION_ONE).withOccasionLocation(VALID_OCCASIONLOCATION_TWO)
                .withTags(VALID_TAG_SLEEP)
                .build();
        uniqueOccasionList.setOccasion(OCCASION_ONE, editedOccasionOne);
        UniqueOccasionList expectedUniqueOccasionList = new UniqueOccasionList(new ArrayList<>());
        expectedUniqueOccasionList.add(editedOccasionOne);
        assertEquals(expectedUniqueOccasionList, uniqueOccasionList);
    }

    @Test
    public void setOccasion_editedOccasionHasDifferentIdentity_success() {
        uniqueOccasionList.add(OCCASION_ONE);
        uniqueOccasionList.setOccasion(OCCASION_ONE, OCCASION_TWO);
        UniqueOccasionList expectedUniqueOccasionList = new UniqueOccasionList(new ArrayList<>());
        expectedUniqueOccasionList.add(OCCASION_TWO);
        assertEquals(expectedUniqueOccasionList, uniqueOccasionList);
    }

    @Test
    public void setOccasion_editedOccasionHasNonUniqueIdentity_throwsDuplicateOccasionException() {
        uniqueOccasionList.add(OCCASION_ONE);
        uniqueOccasionList.add(OCCASION_TWO);
        thrown.expect(DuplicateOccasionException.class);
        uniqueOccasionList.setOccasion(OCCASION_ONE, OCCASION_TWO);
    }

    @Test
    public void remove_nullOccasion_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOccasionList.remove(null);
    }

    @Test
    public void remove_occasionDoesNotExist_throwsOccasionNotFoundException() {
        thrown.expect(OccasionNotFoundException.class);
        uniqueOccasionList.remove(OCCASION_ONE);
    }

    @Test
    public void remove_existingOccasion_removesOccasion() {
        uniqueOccasionList.add(OCCASION_ONE);
        uniqueOccasionList.remove(OCCASION_ONE);
        UniqueOccasionList expectedUniqueOccasionList = new UniqueOccasionList(new ArrayList<>());
        assertEquals(expectedUniqueOccasionList, uniqueOccasionList);
    }

    @Test
    public void setOccasions_nullUniqueOccasionList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOccasionList.setOccasions((UniqueOccasionList) null);
    }

    @Test
    public void setOccasions_uniqueOccasionList_replacesOwnListWithProvidedUniqueOccasionList() {
        uniqueOccasionList.add(OCCASION_ONE);
        UniqueOccasionList expectedUniqueOccasionList = new UniqueOccasionList(new ArrayList<>());
        expectedUniqueOccasionList.add(OCCASION_TWO);
        uniqueOccasionList.setOccasions(expectedUniqueOccasionList);
        assertEquals(expectedUniqueOccasionList, uniqueOccasionList);
    }

    @Test
    public void setOccasions_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueOccasionList.setOccasions((List<Occasion>) null);
    }

    @Test
    public void setOccasions_list_replacesOwnListWithProvidedList() {
        uniqueOccasionList.add(OCCASION_ONE);
        List<Occasion> occasionList = Collections.singletonList(OCCASION_TWO);
        uniqueOccasionList.setOccasions(occasionList);
        UniqueOccasionList expectedUniqueOccasionList = new UniqueOccasionList(new ArrayList<>());
        expectedUniqueOccasionList.add(OCCASION_TWO);
        assertEquals(expectedUniqueOccasionList, uniqueOccasionList);
    }

    @Test
    public void setOccasions_listWithDuplicateOccasions_throwsDuplicateOccasionException() {
        List<Occasion> listWithDuplicateOccasions = Arrays.asList(OCCASION_ONE, OCCASION_ONE);
        thrown.expect(DuplicateOccasionException.class);
        uniqueOccasionList.setOccasions(listWithDuplicateOccasions);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueOccasionList.asUnmodifiableObservableList().remove(0);
    }
}

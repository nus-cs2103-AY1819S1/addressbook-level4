package seedu.address.model.tag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_APPOINTMENT;
import static seedu.address.testutil.TypicalTags.APPOINTMENT_TAG;
import static seedu.address.testutil.TypicalTags.MEETING_TAG;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.tag.exceptions.DuplicateTagException;

public class UniqueTagListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueTagList uniqueTagList = new UniqueTagList();

    @Test
    public void contains_nullTag_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTagList.contains(null);
    }

    @Test
    public void contains_tagNotInList_returnsFalse() {
        assertFalse(uniqueTagList.contains(APPOINTMENT_TAG));
    }

    @Test
    public void contains_tagInList_returnsTrue() {
        uniqueTagList.add(APPOINTMENT_TAG);
        assertTrue(uniqueTagList.contains(APPOINTMENT_TAG));
    }

    @Test
    public void contains_tagWithSameIdentityInList_returnsTrue() {
        uniqueTagList.add(APPOINTMENT_TAG);
        Tag sameIdentityTag = new Tag(VALID_TAG_APPOINTMENT.toUpperCase());
        assertTrue(uniqueTagList.contains(sameIdentityTag));
    }

    @Test
    public void add_nullTag_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTagList.add(null);
    }

    @Test
    public void add_duplicateTag_throwsDuplicateTagException() {
        uniqueTagList.add(APPOINTMENT_TAG);
        thrown.expect(DuplicateTagException.class);
        uniqueTagList.add(APPOINTMENT_TAG);
    }

    @Test
    public void setTags_nullUniqueTagList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTagList.setTags((UniqueTagList) null);
    }

    @Test
    public void setTags_uniqueTagList_replacesOwnListWithProvidedUniqueTagList() {
        uniqueTagList.add(APPOINTMENT_TAG);
        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.add(MEETING_TAG);
        uniqueTagList.setTags(expectedUniqueTagList);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTags_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueTagList.setTags((List<Tag>) null);
    }

    @Test
    public void setTags_list_replacesOwnListWithProvidedList() {
        uniqueTagList.add(APPOINTMENT_TAG);
        List<Tag> tagList = Collections.singletonList(MEETING_TAG);
        uniqueTagList.setTags(tagList);
        UniqueTagList expectedUniqueTagList = new UniqueTagList();
        expectedUniqueTagList.add(MEETING_TAG);
        assertEquals(expectedUniqueTagList, uniqueTagList);
    }

    @Test
    public void setTags_listWithDuplicateTags_throwsDuplicateTagException() {
        List<Tag> listWithDuplicateTags = Arrays.asList(APPOINTMENT_TAG, APPOINTMENT_TAG);
        thrown.expect(DuplicateTagException.class);
        uniqueTagList.setTags(listWithDuplicateTags);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueTagList.asUnmodifiableObservableList().add(APPOINTMENT_TAG);
    }
}

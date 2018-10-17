package seedu.learnvocabulary.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.learnvocabulary.testutil.TypicalWords.AMY;
import static seedu.learnvocabulary.testutil.TypicalWords.BOB;
import static seedu.learnvocabulary.testutil.TypicalWords.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.learnvocabulary.testutil.LearnVocabularyBuilder;

public class VersionedLearnVocabularyTest {

    private final ReadOnlyLearnVocabulary learnVocabularyWithAmy = new LearnVocabularyBuilder().withWord(AMY).build();
    private final ReadOnlyLearnVocabulary learnVocabularyWithBob = new LearnVocabularyBuilder().withWord(BOB).build();
    private final ReadOnlyLearnVocabulary learnVocabularyWithCarl = new LearnVocabularyBuilder().withWord(CARL).build();
    private final ReadOnlyLearnVocabulary emptyLearnVocabulary = new LearnVocabularyBuilder().build();

    @Test
    public void commit_singleLearnVocabulary_noStatesRemovedCurrentStateSaved() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(emptyLearnVocabulary);

        versionedLearnVocabulary.commit();
        assertLearnVocabularyListStatus(versionedLearnVocabulary,
                Collections.singletonList(emptyLearnVocabulary),
                emptyLearnVocabulary,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleLearnVocabularyPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(
                emptyLearnVocabulary, learnVocabularyWithAmy, learnVocabularyWithBob);

        versionedLearnVocabulary.commit();
        assertLearnVocabularyListStatus(versionedLearnVocabulary,
                Arrays.asList(emptyLearnVocabulary, learnVocabularyWithAmy, learnVocabularyWithBob),
                learnVocabularyWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleLearnVocabularyPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(
                emptyLearnVocabulary, learnVocabularyWithAmy, learnVocabularyWithBob);
        shiftCurrentStatePointerLeftwards(versionedLearnVocabulary, 2);

        versionedLearnVocabulary.commit();
        assertLearnVocabularyListStatus(versionedLearnVocabulary,
                Collections.singletonList(emptyLearnVocabulary),
                emptyLearnVocabulary,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleLearnVocabularyPointerAtEndOfStateList_returnsTrue() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(
                emptyLearnVocabulary, learnVocabularyWithAmy, learnVocabularyWithBob);

        assertTrue(versionedLearnVocabulary.canUndo());
    }

    @Test
    public void canUndo_multipleLearnVocabularyPointerAtStartOfStateList_returnsTrue() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(
                emptyLearnVocabulary, learnVocabularyWithAmy, learnVocabularyWithBob);
        shiftCurrentStatePointerLeftwards(versionedLearnVocabulary, 1);

        assertTrue(versionedLearnVocabulary.canUndo());
    }

    @Test
    public void canUndo_singleLearnVocabulary_returnsFalse() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(emptyLearnVocabulary);

        assertFalse(versionedLearnVocabulary.canUndo());
    }

    @Test
    public void canUndo_multipleLearnVocabularyPointerAtStartOfStateList_returnsFalse() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(
                emptyLearnVocabulary, learnVocabularyWithAmy, learnVocabularyWithBob);
        shiftCurrentStatePointerLeftwards(versionedLearnVocabulary, 2);

        assertFalse(versionedLearnVocabulary.canUndo());
    }

    @Test
    public void canRedo_multipleLearnVocabularyPointerNotAtEndOfStateList_returnsTrue() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(
                emptyLearnVocabulary, learnVocabularyWithAmy, learnVocabularyWithBob);
        shiftCurrentStatePointerLeftwards(versionedLearnVocabulary, 1);

        assertTrue(versionedLearnVocabulary.canRedo());
    }

    @Test
    public void canRedo_multipleLearnVocabularyPointerAtStartOfStateList_returnsTrue() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(
                emptyLearnVocabulary, learnVocabularyWithAmy, learnVocabularyWithBob);
        shiftCurrentStatePointerLeftwards(versionedLearnVocabulary, 2);

        assertTrue(versionedLearnVocabulary.canRedo());
    }

    @Test
    public void canRedo_singleLearnVocabulary_returnsFalse() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(emptyLearnVocabulary);

        assertFalse(versionedLearnVocabulary.canRedo());
    }

    @Test
    public void canRedo_multipleLearnVocabularyPointerAtEndOfStateList_returnsFalse() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(
                emptyLearnVocabulary, learnVocabularyWithAmy, learnVocabularyWithBob);

        assertFalse(versionedLearnVocabulary.canRedo());
    }

    @Test
    public void undo_multipleLearnVocabularyPointerAtEndOfStateList_success() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(
                emptyLearnVocabulary, learnVocabularyWithAmy, learnVocabularyWithBob);

        versionedLearnVocabulary.undo();
        assertLearnVocabularyListStatus(versionedLearnVocabulary,
                Collections.singletonList(emptyLearnVocabulary),
                learnVocabularyWithAmy,
                Collections.singletonList(learnVocabularyWithBob));
    }

    @Test
    public void undo_multipleLearnVocabularyPointerNotAtStartOfStateList_success() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(
                emptyLearnVocabulary, learnVocabularyWithAmy, learnVocabularyWithBob);
        shiftCurrentStatePointerLeftwards(versionedLearnVocabulary, 1);

        versionedLearnVocabulary.undo();
        assertLearnVocabularyListStatus(versionedLearnVocabulary,
                Collections.emptyList(),
                emptyLearnVocabulary,
                Arrays.asList(learnVocabularyWithAmy, learnVocabularyWithBob));
    }

    @Test
    public void undo_singleLearnVocabulary_throwsNoUndoableStateException() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(emptyLearnVocabulary);

        assertThrows(VersionedLearnVocabulary.NoUndoableStateException.class, versionedLearnVocabulary::undo);
    }

    @Test
    public void undo_multipleLearnVocabularyPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(
                emptyLearnVocabulary, learnVocabularyWithAmy, learnVocabularyWithBob);
        shiftCurrentStatePointerLeftwards(versionedLearnVocabulary, 2);

        assertThrows(VersionedLearnVocabulary.NoUndoableStateException.class, versionedLearnVocabulary::undo);
    }

    @Test
    public void redo_multipleLearnVocabularyPointerNotAtEndOfStateList_success() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(
                emptyLearnVocabulary, learnVocabularyWithAmy, learnVocabularyWithBob);
        shiftCurrentStatePointerLeftwards(versionedLearnVocabulary, 1);

        versionedLearnVocabulary.redo();
        assertLearnVocabularyListStatus(versionedLearnVocabulary,
                Arrays.asList(emptyLearnVocabulary, learnVocabularyWithAmy),
                learnVocabularyWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleLearnVocabularyPointerAtStartOfStateList_success() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(
                emptyLearnVocabulary, learnVocabularyWithAmy, learnVocabularyWithBob);
        shiftCurrentStatePointerLeftwards(versionedLearnVocabulary, 2);

        versionedLearnVocabulary.redo();
        assertLearnVocabularyListStatus(versionedLearnVocabulary,
                Collections.singletonList(emptyLearnVocabulary),
                learnVocabularyWithAmy,
                Collections.singletonList(learnVocabularyWithBob));
    }

    @Test
    public void redo_singleLearnVocabulary_throwsNoRedoableStateException() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(emptyLearnVocabulary);

        assertThrows(VersionedLearnVocabulary.NoRedoableStateException.class, versionedLearnVocabulary::redo);
    }

    @Test
    public void redo_multipleLearnVocabularyPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedLearnVocabulary versionedLearnVocabulary = prepareLearnVocabularyList(
                emptyLearnVocabulary, learnVocabularyWithAmy, learnVocabularyWithBob);

        assertThrows(VersionedLearnVocabulary.NoRedoableStateException.class, versionedLearnVocabulary::redo);
    }

    @Test
    public void equals() {
        VersionedLearnVocabulary versionedLearnVocabulary =
                prepareLearnVocabularyList(learnVocabularyWithAmy, learnVocabularyWithBob);

        // same values -> returns true
        VersionedLearnVocabulary copy = prepareLearnVocabularyList(learnVocabularyWithAmy, learnVocabularyWithBob);
        assertTrue(versionedLearnVocabulary.equals(copy));

        // same object -> returns true
        assertTrue(versionedLearnVocabulary.equals(versionedLearnVocabulary));

        // null -> returns false
        assertFalse(versionedLearnVocabulary.equals(null));

        // different types -> returns false
        assertFalse(versionedLearnVocabulary.equals(1));

        // different state list -> returns false
        VersionedLearnVocabulary differentLearnVocabularyList =
                prepareLearnVocabularyList(learnVocabularyWithBob, learnVocabularyWithCarl);
        assertFalse(versionedLearnVocabulary.equals(differentLearnVocabularyList));

        // different current pointer index -> returns false
        VersionedLearnVocabulary differentCurrentStatePointer = prepareLearnVocabularyList(
                learnVocabularyWithAmy, learnVocabularyWithBob);
        shiftCurrentStatePointerLeftwards(versionedLearnVocabulary, 1);
        assertFalse(versionedLearnVocabulary.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedLearnVocabulary} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedLearnVocabulary#currentStatePointer} is equal
     * to {@code expectedStatesBeforePointer}, and states after
     * {@code versionedLearnVocabulary#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertLearnVocabularyListStatus(VersionedLearnVocabulary versionedLearnVocabulary,
                                             List<ReadOnlyLearnVocabulary> expectedStatesBeforePointer,
                                             ReadOnlyLearnVocabulary expectedCurrentState,
                                             List<ReadOnlyLearnVocabulary> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new LearnVocabulary(versionedLearnVocabulary), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedLearnVocabulary.canUndo()) {
            versionedLearnVocabulary.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyLearnVocabulary expectedLearnVocabulary : expectedStatesBeforePointer) {
            assertEquals(expectedLearnVocabulary, new LearnVocabulary(versionedLearnVocabulary));
            versionedLearnVocabulary.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyLearnVocabulary expectedLearnVocabulary : expectedStatesAfterPointer) {
            versionedLearnVocabulary.redo();
            assertEquals(expectedLearnVocabulary, new LearnVocabulary(versionedLearnVocabulary));
        }

        // check that there are no more states after pointer
        assertFalse(versionedLearnVocabulary.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedLearnVocabulary.undo());
    }

    /**
     * Creates and returns a {@code VersionedLearnVocabulary} with the {@code learnVocabularyStates}
     * added into it, and the {@code VersionedLearnVocabulary#currentStatePointer} at the end of list.
     */
    private VersionedLearnVocabulary prepareLearnVocabularyList(ReadOnlyLearnVocabulary... learnVocabularyStates) {
        assertFalse(learnVocabularyStates.length == 0);

        VersionedLearnVocabulary versionedLearnVocabulary = new VersionedLearnVocabulary(learnVocabularyStates[0]);
        for (int i = 1; i < learnVocabularyStates.length; i++) {
            versionedLearnVocabulary.resetData(learnVocabularyStates[i]);
            versionedLearnVocabulary.commit();
        }

        return versionedLearnVocabulary;
    }

    /**
     * Shifts the {@code versionedLearnVocabulary#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedLearnVocabulary versionedLearnVocabulary, int count) {
        for (int i = 0; i < count; i++) {
            versionedLearnVocabulary.undo();
        }
    }
}

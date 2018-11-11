package seedu.souschef.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.souschef.testutil.TypicalRecipes.AMY;
import static seedu.souschef.testutil.TypicalRecipes.BOB;
import static seedu.souschef.testutil.TypicalRecipes.CHINESE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.souschef.testutil.AppContentBuilder;

public class VersionedAppContentTest {

    private final ReadOnlyAppContent appContentWithAmy = new AppContentBuilder().withRecipe(AMY).build();
    private final ReadOnlyAppContent appContentWithBob = new AppContentBuilder().withRecipe(BOB).build();
    private final ReadOnlyAppContent appContentWithCarl = new AppContentBuilder().withRecipe(CHINESE).build();
    private final ReadOnlyAppContent emptyAppContent = new AppContentBuilder().build();

    @Test
    public void commit_singleAppContent_noStatesRemovedCurrentStateSaved() {
        VersionedAppContent versionedAppContent = prepareAppContentList(emptyAppContent);

        versionedAppContent.commit();
        assertAppContentListStatus(versionedAppContent,
                Collections.singletonList(emptyAppContent),
                emptyAppContent,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAppContentPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedAppContent versionedAppContent = prepareAppContentList(
                emptyAppContent, appContentWithAmy, appContentWithBob);

        versionedAppContent.commit();
        assertAppContentListStatus(versionedAppContent,
                Arrays.asList(emptyAppContent, appContentWithAmy, appContentWithBob),
                appContentWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAppContentPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedAppContent versionedversionedAppContent = prepareAppContentList(
                emptyAppContent, appContentWithAmy, appContentWithBob);
        shiftCurrentStatePointerLeftwards(versionedversionedAppContent, 2);

        versionedversionedAppContent.commit();
        assertAppContentListStatus(versionedversionedAppContent,
                Collections.singletonList(emptyAppContent),
                emptyAppContent,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleAppContentPointerAtEndOfStateList_returnsTrue() {
        VersionedAppContent versionedversionedAppContent = prepareAppContentList(
                emptyAppContent, appContentWithAmy, appContentWithBob);

        assertTrue(versionedversionedAppContent.canUndo());
    }

    @Test
    public void canUndo_multipleAppContentPointerAtStartOfStateList_returnsTrue() {
        VersionedAppContent versionedversionedAppContent = prepareAppContentList(
                emptyAppContent, appContentWithAmy, appContentWithBob);
        shiftCurrentStatePointerLeftwards(versionedversionedAppContent, 1);

        assertTrue(versionedversionedAppContent.canUndo());
    }

    @Test
    public void canUndo_singleAppContent_returnsFalse() {
        VersionedAppContent versionedAppContent = prepareAppContentList(emptyAppContent);

        assertFalse(versionedAppContent.canUndo());
    }

    @Test
    public void canUndo_multipleAppContentPointerAtStartOfStateList_returnsFalse() {
        VersionedAppContent versionedversionedAppContent = prepareAppContentList(
                emptyAppContent, appContentWithAmy, appContentWithBob);
        shiftCurrentStatePointerLeftwards(versionedversionedAppContent, 2);

        assertFalse(versionedversionedAppContent.canUndo());
    }

    @Test
    public void canRedo_multipleAppContentPointerNotAtEndOfStateList_returnsTrue() {
        VersionedAppContent versionedversionedAppContent = prepareAppContentList(
                emptyAppContent, appContentWithAmy, appContentWithBob);
        shiftCurrentStatePointerLeftwards(versionedversionedAppContent, 1);

        assertTrue(versionedversionedAppContent.canRedo());
    }

    @Test
    public void canRedo_multipleAppContentPointerAtStartOfStateList_returnsTrue() {
        VersionedAppContent versionedAppContent = prepareAppContentList(
                emptyAppContent, appContentWithAmy, appContentWithBob);
        shiftCurrentStatePointerLeftwards(versionedAppContent, 2);

        assertTrue(versionedAppContent.canRedo());
    }

    @Test
    public void canRedo_singleAppContent_returnsFalse() {
        VersionedAppContent versionedAppContent = prepareAppContentList(emptyAppContent);

        assertFalse(versionedAppContent.canRedo());
    }

    @Test
    public void canRedo_multipleAppContentPointerAtEndOfStateList_returnsFalse() {
        VersionedAppContent versionedversionedAppContent = prepareAppContentList(
                emptyAppContent, appContentWithAmy, appContentWithBob);

        assertFalse(versionedversionedAppContent.canRedo());
    }

    @Test
    public void undo_multipleAppContentPointerAtEndOfStateList_success() {
        VersionedAppContent versionedAppContent = prepareAppContentList(
                emptyAppContent, appContentWithAmy, appContentWithBob);

        versionedAppContent.undo();
        assertAppContentListStatus(versionedAppContent,
                Collections.singletonList(emptyAppContent),
                appContentWithAmy,
                Collections.singletonList(appContentWithBob));
    }

    @Test
    public void undo_multipleAppContentPointerNotAtStartOfStateList_success() {
        VersionedAppContent versionedAppContent = prepareAppContentList(
                emptyAppContent, appContentWithAmy, appContentWithBob);
        shiftCurrentStatePointerLeftwards(versionedAppContent, 1);

        versionedAppContent.undo();
        assertAppContentListStatus(versionedAppContent,
                Collections.emptyList(),
                emptyAppContent,
                Arrays.asList(appContentWithAmy, appContentWithBob));
    }

    @Test
    public void undo_singleAppContent_throwsNoUndoableStateException() {
        VersionedAppContent versionedAppContent = prepareAppContentList(emptyAppContent);

        assertThrows(VersionedAppContent.NoUndoableStateException.class, versionedAppContent::undo);
    }

    @Test
    public void undo_multipleAppContentPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedAppContent versionedAppContent = prepareAppContentList(
                emptyAppContent, appContentWithAmy, appContentWithBob);
        shiftCurrentStatePointerLeftwards(versionedAppContent, 2);

        assertThrows(VersionedAppContent.NoUndoableStateException.class, versionedAppContent::undo);
    }

    @Test
    public void redo_multipleAppContentPointerNotAtEndOfStateList_success() {
        VersionedAppContent versionedAppContent = prepareAppContentList(
                emptyAppContent, appContentWithAmy, appContentWithBob);
        shiftCurrentStatePointerLeftwards(versionedAppContent, 1);

        versionedAppContent.redo();
        assertAppContentListStatus(versionedAppContent,
                Arrays.asList(emptyAppContent, appContentWithAmy),
                appContentWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleAppContentPointerAtStartOfStateList_success() {
        VersionedAppContent versionedAppContent = prepareAppContentList(
                emptyAppContent, appContentWithAmy, appContentWithBob);
        shiftCurrentStatePointerLeftwards(versionedAppContent, 2);

        versionedAppContent.redo();
        assertAppContentListStatus(versionedAppContent,
                Collections.singletonList(emptyAppContent),
                appContentWithAmy,
                Collections.singletonList(appContentWithBob));
    }

    @Test
    public void redo_singleAppContent_throwsNoRedoableStateException() {
        VersionedAppContent versionedversionedAppContent = prepareAppContentList(emptyAppContent);

        assertThrows(VersionedAppContent.NoRedoableStateException.class, versionedversionedAppContent::redo);
    }

    @Test
    public void redo_multipleAppContentPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedAppContent versionedversionedAppContent = prepareAppContentList(
                emptyAppContent, appContentWithAmy, appContentWithBob);

        assertThrows(VersionedAppContent.NoRedoableStateException.class, versionedversionedAppContent::redo);
    }

    @Test
    public void equals() {
        VersionedAppContent versionedversionedAppContent = prepareAppContentList(appContentWithAmy, appContentWithBob);

        // same values -> returns true
        VersionedAppContent copy = prepareAppContentList(appContentWithAmy, appContentWithBob);
        assertTrue(versionedversionedAppContent.equals(copy));

        // same object -> returns true
        assertTrue(versionedversionedAppContent.equals(versionedversionedAppContent));

        // null -> returns false
        assertFalse(versionedversionedAppContent.equals(null));

        // different types -> returns false
        assertFalse(versionedversionedAppContent.equals(1));

        // different state list -> returns false
        VersionedAppContent differentAppContentList = prepareAppContentList(appContentWithBob, appContentWithCarl);
        assertFalse(versionedversionedAppContent.equals(differentAppContentList));

        // different current pointer index -> returns false
        VersionedAppContent differentCurrentStatePointer = prepareAppContentList(
                appContentWithAmy, appContentWithBob);
        shiftCurrentStatePointerLeftwards(versionedversionedAppContent, 1);
        assertFalse(versionedversionedAppContent.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedAppContent} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedAppContent#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedAppContent#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertAppContentListStatus(VersionedAppContent versionedAppContent,
                                            List<ReadOnlyAppContent> expectedStatesBeforePointer,
                                            ReadOnlyAppContent expectedCurrentState,
                                            List<ReadOnlyAppContent> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new AppContent(versionedAppContent), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedAppContent.canUndo()) {
            versionedAppContent.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyAppContent expectedAppContent : expectedStatesBeforePointer) {
            assertEquals(expectedAppContent, new AppContent(versionedAppContent));
            versionedAppContent.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyAppContent expectedAppContent : expectedStatesAfterPointer) {
            versionedAppContent.redo();
            assertEquals(expectedAppContent, new AppContent(versionedAppContent));
        }

        // check that there are no more states after pointer
        assertFalse(versionedAppContent.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedAppContent.undo());
    }

    /**
     * Creates and returns a {@code VersionedAppContent} with the {@code appContentStates} added into it, and the
     * {@code VersionedAppContent#currentStatePointer} at the end of list.
     */
    private VersionedAppContent prepareAppContentList(ReadOnlyAppContent... appContentStates) {
        assertFalse(appContentStates.length == 0);

        VersionedAppContent versionedAppContent = new VersionedAppContent(appContentStates[0]);
        for (int i = 1; i < appContentStates.length; i++) {
            versionedAppContent.resetData(appContentStates[i]);
            versionedAppContent.commit();
        }

        return versionedAppContent;
    }

    /**
     * Shifts the {@code versionedAppContent#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedAppContent versionedAppContent, int count) {
        for (int i = 0; i < count; i++) {
            versionedAppContent.undo();
        }
    }
}

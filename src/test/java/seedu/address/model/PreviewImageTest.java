package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.UndoRedoCommandTestUtil.clearCache;

import org.junit.After;
import org.junit.Test;

import seedu.address.testutil.PreviewImageGenerator;

public class PreviewImageTest {

    @Test
    public void commit_defaultPreviewImageState() {
        PreviewImage previewImage = PreviewImageGenerator.getDefaultPreviewImage();
        assertPreviewImageState(previewImage, 0, 1);
    }

    @Test
    public void commit_multipleImagesPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        PreviewImage previewImage = PreviewImageGenerator.getDefaultPreviewImage();
        previewImage.commit(PreviewImageGenerator.getABufferedImage());
        previewImage.commit(PreviewImageGenerator.getABufferedImage());
        previewImage.commit(PreviewImageGenerator.getABufferedImage());

        assertPreviewImageState(previewImage, 3, 4);
    }

    @Test
    public void commit_imagePointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithUndoneStates();
        previewImage.commit(PreviewImageGenerator.getABufferedImage());

        assertPreviewImageState(previewImage, 1, 2);
    }

    @Test
    public void canUndo_multipleImagesPointerAtEndOfStateList_returnsTrue() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();
        assertTrue(previewImage.canUndo());
    }

    @Test
    public void canUndo_multipleImagesPointerAtMidOfStateList_returnsTrue() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();
        shiftCurrentStatePointerLeftwards(previewImage, 1);
        assertTrue(previewImage.canUndo());
    }

    @Test
    public void canUndo_defaultPreviewImage_returnsFalse() {
        PreviewImage previewImage = PreviewImageGenerator.getDefaultPreviewImage();
        assertFalse(previewImage.canUndo());
    }

    @Test
    public void canUndo_multipleImagesPointerAtStartOfStateList_returnsFalse() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();
        shiftCurrentStatePointerLeftwards(previewImage, 3);

        assertFalse(previewImage.canUndo());
    }

    @Test
    public void canRedo_multipleImagesPointerNotAtEndOfStateList_returnsTrue() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();
        shiftCurrentStatePointerLeftwards(previewImage, 1);
        assertTrue(previewImage.canRedo());
    }

    @Test
    public void canRedo_multipleImagesPointerAtStartOfStateList_returnsTrue() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithUndoneStates();
        assertTrue(previewImage.canRedo());
    }

    @Test
    public void canRedo_defaultPreviewImage_returnsFalse() {
        PreviewImage previewImage = PreviewImageGenerator.getDefaultPreviewImage();
        assertFalse(previewImage.canRedo());
    }

    @Test
    public void canRedo_multipleImagePointerAtEndOfStateList_returnsFalse() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();
        assertFalse(previewImage.canRedo());
    }

    @Test
    public void undo_multipleImagesPointerAtEndOfStateList_success() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();

        previewImage.undo();
        assertPreviewImageState(previewImage, 2, 4);
    }

    @Test
    public void undo_multipleImagesPointerAtMidOfStateList_success() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();
        shiftCurrentStatePointerLeftwards(previewImage, 1);

        previewImage.undo();
        assertPreviewImageState(previewImage, 1, 4);
    }

    @Test
    public void undo_defaultPreviewImage_throwsNoUndoableStateException() {
        PreviewImage previewImage = PreviewImageGenerator.getDefaultPreviewImage();
        assertThrows(PreviewImage.NoUndoableStateException.class, previewImage::undo);
    }

    @Test
    public void undo_multipleImagesPointerAtStartOfStateList_throwsNoUndoableStateException() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();
        shiftCurrentStatePointerLeftwards(previewImage, 3);
        assertThrows(PreviewImage.NoUndoableStateException.class, previewImage::undo);
    }

    @Test
    public void redo_multipleAddressBookPointerNotAtEndOfStateList_success() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();
        shiftCurrentStatePointerLeftwards(previewImage, 1);

        previewImage.redo();
        assertPreviewImageState(previewImage, 3, 4);
    }

    @Test
    public void redo_multipleImagesPointerAtStartOfStateList_success() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();
        shiftCurrentStatePointerLeftwards(previewImage, 3);

        previewImage.redo();
        assertPreviewImageState(previewImage, 1, 4);

    }

    @Test
    public void redo_defaultPreviewImage_throwsNoRedoableStateException() {
        PreviewImage previewImage = PreviewImageGenerator.getDefaultPreviewImage();
        assertThrows(PreviewImage.NoRedoableStateException.class, previewImage::redo);
    }

    @Test
    public void redo_multipleImagesPointerAtEndOfStateList_throwsNoRedoableStateException() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();

        assertThrows(PreviewImage.NoRedoableStateException.class, previewImage::redo);
    }

    /**
     * Asserts that {@code previewImage} has the correct state by checking
     * that {@code previewImage#currentIndex} is equal to {@code expectedIndex},
     * and {@code previewImage#currentSize} is equal to {@code expectedSize}.
     */
    private void assertPreviewImageState(PreviewImage previewImage, int expectedIndex, int expectedSize) {
        // check state currently pointing at is correct
        assertEquals(previewImage.getCurrentIndex(), expectedIndex);
        assertEquals(previewImage.getCurrentSize(), expectedSize);
    }

    /**
     * Shifts the {@code previewImage#currentIndex} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(PreviewImage previewImage, int count) {
        for (int i = 0; i < count; i++) {
            previewImage.undo();
        }
    }

    @After
    public void cleanUp() {
        clearCache();
    }
}

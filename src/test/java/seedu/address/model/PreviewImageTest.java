package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.UndoRedoCommandTestUtil.clearCache;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Test;

import seedu.address.model.transformation.Transformation;
import seedu.address.testutil.PreviewImageGenerator;

//@@author ihwk1996
public class PreviewImageTest {

    private static final int ORIGINAL_IMAGE_HEIGHT = 354;
    private static final int ORIGINAL_IMAGE_WIDTH = 458;

    @Test
    public void get_heightAndWidth() {
        PreviewImage previewImage = PreviewImageGenerator.getDefaultPreviewImage();
        assertEquals(previewImage.getHeight(), ORIGINAL_IMAGE_HEIGHT);
        assertEquals(previewImage.getWidth(), ORIGINAL_IMAGE_WIDTH);
    }

    @Test
    public void getImageTest() {
        PreviewImage previewImage = PreviewImageGenerator.getDefaultPreviewImage();
        BufferedImage image = previewImage.getImage();
        assertNotNull(image);
    }

    @Test
    public void getPathTest() {
        PreviewImage previewImage = PreviewImageGenerator.getDefaultPreviewImage();
        Path path = previewImage.getCurrentPath();
        assertNotNull(path);
    }

    @Test
    public void addTransformationTest() {
        PreviewImage previewImage = PreviewImageGenerator.getDefaultPreviewImage();
        Transformation t = PreviewImageGenerator.getATransformation();
        previewImage.addTransformation(t);

        String blurTransformationString = "blur 0x8";
        Transformation blurTransformation = previewImage.getTransformationSet().getTransformations().get(0);
        assertEquals(blurTransformationString, blurTransformation.toString());
    }

    @Test
    public void commit_defaultPreviewImageState() {
        PreviewImage previewImage = PreviewImageGenerator.getDefaultPreviewImage();
        assertPreviewImageState(previewImage, 0, 1);
    }

    @Test
    public void commit_defaultPreviewImageStateWithSecondaryConstructor() {
        PreviewImage previewImage = PreviewImageGenerator.getDefaultPreviewImageWithSecondaryConstructor();
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
    public void canUndo_multipleImagesPointerAtMidOfStateList_returnsTrue() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();
        shiftCurrentStatePointerLeftwards(previewImage, 1);
        assertTrue(previewImage.canUndo());
    }

    @Test
    public void canUndo_multipleImagesPointerAtEndOfStateList_returnsTrue() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();
        assertTrue(previewImage.canUndo());
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
    public void undo_multipleImagesPointerAtMidOfStateList_success() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();
        shiftCurrentStatePointerLeftwards(previewImage, 1);

        previewImage.undo();
        assertPreviewImageState(previewImage, 1, 4);
    }

    @Test
    public void undo_multipleImagesPointerAtEndOfStateList_success() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();

        previewImage.undo();
        assertPreviewImageState(previewImage, 2, 4);
    }

    @Test
    public void canRedo_defaultPreviewImage_returnsFalse() {
        PreviewImage previewImage = PreviewImageGenerator.getDefaultPreviewImage();
        assertFalse(previewImage.canRedo());
    }

    @Test
    public void canRedo_multipleImagesPointerAtStartOfStateList_returnsTrue() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithUndoneStates();
        assertTrue(previewImage.canRedo());
    }

    @Test
    public void canRedo_multipleImagesPointerAtMidOfStateList_returnsTrue() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();
        shiftCurrentStatePointerLeftwards(previewImage, 1);
        assertTrue(previewImage.canRedo());
    }

    @Test
    public void canRedo_multipleImagePointerAtEndOfStateList_returnsFalse() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();
        assertFalse(previewImage.canRedo());
    }

    @Test
    public void redo_defaultPreviewImage_throwsNoRedoableStateException() {
        PreviewImage previewImage = PreviewImageGenerator.getDefaultPreviewImage();
        assertThrows(PreviewImage.NoRedoableStateException.class, previewImage::redo);
    }

    @Test
    public void redo_multipleImagesPointerAtStartOfStateList_success() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();
        shiftCurrentStatePointerLeftwards(previewImage, 3);

        previewImage.redo();
        assertPreviewImageState(previewImage, 1, 4);

    }

    @Test
    public void redo_multipleAddressBookPointerAtMidOfStateList_success() {
        PreviewImage previewImage = PreviewImageGenerator.getPreviewImageWithThreeTransformations();
        shiftCurrentStatePointerLeftwards(previewImage, 1);

        previewImage.redo();
        assertPreviewImageState(previewImage, 3, 4);
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
        // Check for correct size and correct pointer index
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

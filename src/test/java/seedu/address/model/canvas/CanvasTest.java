package seedu.address.model.canvas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalOperationException;
import seedu.address.model.ModelManager;
import seedu.address.testutil.CanvasGenerator;
import seedu.address.testutil.PreviewImageGenerator;
import seedu.address.testutil.TypicalImages;

//@author j-lum

class CanvasTest {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    @Test
    public void getLayers() {
        Canvas canvas = CanvasGenerator.getInitialCanvas();
        assertNotNull(canvas.getLayers());
        assertEquals(canvas.getLayers().size(), 1);
    }

    @Test
    public void addLayer() {
        Canvas canvas = CanvasGenerator.getInitialCanvas();
        canvas.addLayer(PreviewImageGenerator.getPreviewImage(TypicalImages.IMAGE_LIST[1]));
        assertEquals(canvas.getLayers().size(), 2);
    }

    @Test
    public void getCurrentLayer() {
        Canvas canvas = CanvasGenerator.getInitialCanvas();
        assertNotNull(canvas.getCurrentLayer());
    }

    @Test
    public void setCurrentLayer() {
        Canvas canvas = CanvasGenerator.getCanvasWithTwoLayers();
        canvas.setCurrentLayer(Index.fromOneBased(2));
        assertEquals(canvas.getCurrentLayer().getName(), "Layer 2");
    }

    @Test
    public void removeLayer() {
        Canvas canvas = CanvasGenerator.getCanvasWithTwoLayers();
        try {
            canvas.removeLayer(Index.fromOneBased(2));
        } catch (IllegalOperationException e) {
            logger.severe("This should not happen!");
            assertNull(e);
        }
        assertEquals(canvas.getLayers().size(), 1);
    }

    @Test
    public void swapLayer() {
        Canvas canvas = CanvasGenerator.getCanvasWithTwoLayers();
        Index to = Index.fromOneBased(1);
        Index from = Index.fromOneBased(2);
        try {
            canvas.swapLayer(to, from);
        } catch (IllegalOperationException e) {
            assertNull(e);
        }
        canvas.setCurrentLayer(to);
        assertEquals(canvas.getCurrentLayer().getName(), "Layer 2");
        assertThrows(IllegalOperationException.class, ()->canvas.swapLayer(to, to));
    }

    @Test
    public void getHeight() {
        Canvas canvas = CanvasGenerator.getInitialCanvas();
        assertEquals(canvas.getCurrentLayer().getHeight(), canvas.getHeight());
    }

    @Test
    public void setHeight() {
        Canvas canvas = CanvasGenerator.getInitialCanvas();
        int newHeight = 100;
        canvas.setHeight(newHeight);
        assertEquals(canvas.getHeight(), newHeight);
    }

    @Test
    public void getWidth() {
        Canvas canvas = CanvasGenerator.getInitialCanvas();
        assertEquals(canvas.getCurrentLayer().getWidth(), canvas.getWidth());
    }

    @Test
    public void setWidth() {
        Canvas canvas = CanvasGenerator.getInitialCanvas();
        int newHeight = 100;
        canvas.setHeight(newHeight);
        assertEquals(canvas.getHeight(), newHeight);
    }

    @Test
    public void isCanvasAuto() {
        Canvas canvas = CanvasGenerator.getInitialCanvas();
        assertFalse(canvas.isCanvasAuto());
    }

    @Test
    public void setCanvasAuto() {
        Canvas canvas = CanvasGenerator.getInitialCanvas();
        canvas.setCanvasAuto(true);
        assertTrue(canvas.isCanvasAuto());
    }

    @Test
    public void getBackgroundColor() {
        Canvas canvas = CanvasGenerator.getInitialCanvas();
        assertEquals(canvas.getBackgroundColor(), "none");
    }

    @Test
    public void setBackgroundColor() {
        Canvas canvas = CanvasGenerator.getInitialCanvas();
        String newColor = "rgba(0,0,0,0.0)";
        canvas.setBackgroundColor(newColor);
        assertEquals(canvas.getBackgroundColor(), newColor);
    }
}

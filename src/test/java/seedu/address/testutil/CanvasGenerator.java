package seedu.address.testutil;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.PreviewImage;
import seedu.address.model.canvas.Canvas;

//@@author j-lum

/**
 * A utility class to generate Canvases for testing.
 */
public class CanvasGenerator {

    private CanvasGenerator() {
        // prevents instantiation
    }

   public static Canvas getInitialCanvas(){
       PreviewImage initialImage = PreviewImageGenerator.getDefaultPreviewImage();
       return new Canvas(initialImage);
   }

   public static Canvas getCanvasWithTwoLayers(){
        PreviewImage secondImage = PreviewImageGenerator.getPreviewImage(TypicalImages.IMAGE_LIST[1]);
        Canvas twoLayers = getInitialCanvas();
        twoLayers.addLayer(secondImage, "Layer 2");

        return twoLayers;
   }
}

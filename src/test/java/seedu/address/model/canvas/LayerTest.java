package seedu.address.model.canvas;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.PreviewImage;
import seedu.address.model.transformation.Transformation;
import seedu.address.testutil.PreviewImageGenerator;


//@author j-lum

class LayerTest {

    @Test
    void addTransformation() {
        Layer layer = new Layer(PreviewImageGenerator.getDefaultPreviewImage(), "Layer 1");
        Transformation t = PreviewImageGenerator.getATransformation();
        layer.addTransformation(t);
        assertTrue(layer.getImage().getTransformationSet().getTransformations().get(0).equals(t));
    }

    @Test
    void getX() {
        Layer layer = new Layer(PreviewImageGenerator.getDefaultPreviewImage(), "Layer 1");
        assertTrue(layer.getX() == 0);
    }

    @Test
    void setX() {
        Layer layer = new Layer(PreviewImageGenerator.getDefaultPreviewImage(), "Layer 1");
        int newX = 100;
        layer.setX(newX);
        assertTrue(layer.getX() == newX);
    }

    @Test
    void getY() {
        Layer layer = new Layer(PreviewImageGenerator.getDefaultPreviewImage(), "Layer 1");
        assertTrue(layer.getY() == 0);
    }

    @Test
    void setY() {
        Layer layer = new Layer(PreviewImageGenerator.getDefaultPreviewImage(), "Layer 1");
        int newY = 100;
        layer.setY(newY);
        assertTrue(layer.getY() == newY);
    }

    @Test
    void getHeight() {
        Layer layer = new Layer(PreviewImageGenerator.getDefaultPreviewImage(), "Layer 1");
        assertTrue(layer.getHeight() == layer.getImage().getHeight());
    }

    @Test
    void setHeight() {
        Layer layer = new Layer(PreviewImageGenerator.getDefaultPreviewImage(), "Layer 1");
        int newHeight = 100;
        layer.setHeight(newHeight);
        assertTrue(layer.getHeight() == newHeight);
    }

    @Test
    void getWidth() {
        Layer layer = new Layer(PreviewImageGenerator.getDefaultPreviewImage(), "Layer 1");
        assertTrue(layer.getWidth() == layer.getImage().getWidth());
    }

    @Test
    void setWidth() {
        Layer layer = new Layer(PreviewImageGenerator.getDefaultPreviewImage(), "Layer 1");
        int newWidth = 100;
        layer.setWidth(newWidth);
        assertTrue(layer.getWidth() == newWidth);
    }

    @Test
    void getImage() {
        Layer layer = new Layer(PreviewImageGenerator.getDefaultPreviewImage(), "Layer 1");
        assertTrue(layer.getImage() instanceof PreviewImage);
    }
}

package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.HistoryUpdateEvent;
import seedu.address.commons.events.ui.LayerUpdateEvent;
import seedu.address.commons.exceptions.IllegalOperationException;
import seedu.address.model.transformation.Transformation;
import seedu.address.testutil.ModelGenerator;
import seedu.address.testutil.PreviewImageGenerator;
import seedu.address.testutil.TypicalImages;
import seedu.address.ui.testutil.EventsCollectorRule;

//TODO: Update tests
public class ModelManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
    }

    @Test
    public void canvasAccessors() {
        Model model = ModelGenerator.getDefaultModel();
        assertNotNull(model.getCanvasHeight());
        assertNotNull(model.getCanvasWidth());
        assertNotNull(model.getCanvas());
        int newHeight = 1234;
        int newWidth = 4567;
        String color = "#00ff00";
        boolean auto = true;
        model.setCanvasSize(newHeight, newWidth);
        model.setCanvasAuto(auto);
        model.setBackgroundColor(color);
        assertEquals(model.getCanvasHeight(), newHeight);
        assertEquals(model.getCanvasWidth(), newWidth);
        assertEquals(model.getCanvas().getBackgroundColor(), color);
        assertEquals(model.getCanvas().isCanvasAuto(), auto);
    }

    @Test
    public void layerAccessors() {
        Model model = ModelGenerator.getDefaultModel();
        PreviewImage second = PreviewImageGenerator.getPreviewImage(TypicalImages.IMAGE_LIST[1]);
        Transformation transformation = PreviewImageGenerator.getATransformation();
        Index one = Index.fromOneBased(1);
        Index two = Index.fromOneBased(2);
        int newX = 123;
        int newY = 456;

        //Testing addLayer
        assertEquals(model.getCanvas().getLayers().size(), 1);
        model.addLayer(second);
        assertEquals(model.getCanvas().getLayers().size(), 2);

        //Testing removeLayer
        try {
            model.removeLayer(Index.fromOneBased(2));
        } catch (IllegalOperationException e) {
            assertNull(e);
        }

        //Testing overloaded addLayer
        assertEquals(model.getCanvas().getLayers().size(), 1);
        model.addLayer(second, "name");
        assertEquals(model.getCanvas().getLayers().size(), 2);

        //Testing swapLayer
        try {
            model.swapLayer(one, two);
        } catch (IllegalOperationException e) {
            assertNull(e);
        }

        //Testing setCurrentLayer
        model.setCurrentLayer(one);
        assertEquals(model.getCanvas().getCurrentLayer().getName(), "name");

        //Testing setCurrentLayerPosition
        model.setCurrentLayerPosition(newX, newY);
        assertEquals(model.getCanvas().getCurrentLayer().getX(), newX);
        assertEquals(model.getCanvas().getCurrentLayer().getY(), newY);

        //Testing misc functions
        model.addTransformation(transformation);
        assertEquals(eventsCollectorRule.eventsCollector.getMostRecent().getClass(), HistoryUpdateEvent.class);
        model.addLayer(second);
        assertEquals(eventsCollectorRule.eventsCollector.getMostRecent().getClass(), LayerUpdateEvent.class);
    }
}

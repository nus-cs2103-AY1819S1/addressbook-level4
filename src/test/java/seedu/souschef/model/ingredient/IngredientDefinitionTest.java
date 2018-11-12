package seedu.souschef.model.ingredient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IngredientDefinitionTest {
    private IngredientDefinition onion = new IngredientDefinition(new IngredientName("onion"));
    private IngredientDefinition otherOnion = new IngredientDefinition("onion");
    private IngredientDefinition carrot = new IngredientDefinition("carrot");

    @Test
    public void getNameTest() {
        assertEquals(onion.getName(), otherOnion.getName());
        assertNotEquals(onion.getName(), carrot.getName());
    }

    @Test
    public void isSameTest() {
        assertTrue(onion.isSame(onion));
        assertTrue(onion.isSame(otherOnion));
        assertFalse(onion.isSame(carrot));
    }

    @Test
    public void equalsTest() {
        assertEquals(onion, onion);
        assertEquals(onion, otherOnion);
        assertNotEquals(onion, carrot);
    }
}

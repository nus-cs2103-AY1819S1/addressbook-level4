package seedu.souschef.model.ingredient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class IngredientPortionTest {
    private IngredientPortion onion = new IngredientPortion(new IngredientName("onion"),
            new IngredientServingUnit("gram"), new IngredientAmount(200.0));
    private IngredientPortion otherOnion = new IngredientPortion("onion", "gram", 100.0);
    private IngredientPortion carrot = new IngredientPortion("carrot", "kg", 1.0);

    @Test
    public void getNameTest() {
        assertEquals(onion.getName(), otherOnion.getName());
        assertEquals(onion.getName(), carrot.getName());
    }

    @Test
    public void getUnitTest() {
        assertEquals(onion.getUnit(), otherOnion.getUnit());
        assertNotEquals(otherOnion.getUnit(), carrot.getUnit());
    }

    @Test
    public void getAmountTest() {
        assertNotEquals(onion.getAmount(), otherOnion.getAmount());
        assertEquals(otherOnion.getAmount(), carrot.getAmount());
    }

    @Test
    public void addAmountTest() {
        assertEquals(400.0, onion.addAmount(onion).getAmount().getValue(), 0.0);
        assertEquals(300.0, onion.addAmount(otherOnion).getAmount().getValue(), 0.0);
    }

    @Test
    public void subtractAmountTest() {
        assertEquals(0.0, onion.subtractAmount(onion).getAmount().getValue(), 0.0);
        assertEquals(100.0, onion.subtractAmount(otherOnion).getAmount().getValue(), 0.0);
        assertEquals(0.0, otherOnion.subtractAmount(onion).getAmount().getValue(), 0.0);
    }

    @Test
    public void multiplyAmountTest() {
        assertEquals(200.0, onion.multiplyAmount(1).getAmount().getValue(), 0.0);
        assertEquals(400.0, onion.multiplyAmount(2).getAmount().getValue(), 0.0);
        assertEquals(2000.0, onion.multiplyAmount(10).getAmount().getValue(), 0.0);
    }

    @Test
    public void convertToCommonUnitTest() {
        IngredientPortion convertedOnion = onion.convertToCommonUnit();
        IngredientPortion convertedCarrot = carrot.convertToCommonUnit();
        assertEquals(convertedOnion.getUnit(), new IngredientServingUnit("gram"));
        assertEquals(convertedCarrot.getUnit(), new IngredientServingUnit("gram"));
        assertEquals(200.0, convertedOnion.getAmount().getValue(), 0.0);
        assertEquals(1000.0, convertedCarrot.getAmount().getValue(), 0.0);
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
        assertNotEquals(onion, otherOnion);
        assertNotEquals(onion, carrot);
    }
}

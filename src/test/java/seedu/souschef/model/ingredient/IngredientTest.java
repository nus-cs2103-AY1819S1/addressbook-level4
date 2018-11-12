package seedu.souschef.model.ingredient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;

public class IngredientTest {
    private Ingredient onion;
    private Ingredient otherOnion;
    private Ingredient carrot;
    {
        try {
            onion = new Ingredient(new IngredientName("onion"),
                    new IngredientAmount(200.0), new IngredientServingUnit("gram"),
                    new IngredientDate(new SimpleDateFormat("MM-dd-yyyy").parse("11-12-2018")));
            otherOnion = new Ingredient("onion", "gram", 100.0,
                    new SimpleDateFormat("MM-dd-yyyy").parse("11-12-2018"));
            carrot = new Ingredient("carrot", "kg", 1.0,
                    new SimpleDateFormat("MM-dd-yyyy").parse("11-13-2018"));
        } catch (ParseException e) {
            System.out.println("Wrong Date Format!");
        }
    }


    @Test
    public void getNameTest() {
        assertEquals(onion.getName(), otherOnion.getName());
        assertNotEquals(onion.getName(), carrot.getName());
    }

    @Test
    public void getUnitTest() {
        assertEquals(onion.getUnit(), otherOnion.getUnit());
        assertNotEquals(otherOnion.getUnit(), carrot.getUnit());
    }

    @Test
    public void getAmountTest() {
        assertEquals(200.0, onion.getAmount().getValue(), 0.0);
        assertEquals(100.0, otherOnion.getAmount().getValue(), 0.0);
        assertEquals(1.0, carrot.getAmount().getValue(), 0.0);
    }

    @Test
    public void getDateTest() {
        assertEquals(onion.getDate(), otherOnion.getDate());
        assertNotEquals(otherOnion.getDate(), carrot.getDate());
    }

    @Test
    public void convertToCommonUnitTest() {
        Ingredient convertedOnion = onion.convertToCommonUnit();
        Ingredient convertedCarrot = carrot.convertToCommonUnit();
        assertEquals(convertedOnion.getUnit(), new IngredientServingUnit("gram"));
        assertEquals(convertedCarrot.getUnit(), new IngredientServingUnit("gram"));
        assertEquals(200.0, convertedOnion.getAmount().getValue(), 0.0);
        assertEquals(1000.0, convertedCarrot.getAmount().getValue(), 0.0);
    }

    @Test
    public void isSameTest() {
        assertTrue(onion.isSame(onion));
        assertFalse(onion.isSame(otherOnion));
        assertFalse(onion.isSame(carrot));
    }

    @Test
    public void equalsTest() {
        assertEquals(onion, onion);
        assertNotEquals(onion, otherOnion);
        assertNotEquals(onion, carrot);
    }
}

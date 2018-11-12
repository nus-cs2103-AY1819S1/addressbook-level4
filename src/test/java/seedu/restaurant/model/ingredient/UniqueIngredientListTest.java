package seedu.restaurant.model.ingredient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.AVOCADO;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.BROCCOLI;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.restaurant.model.ingredient.exceptions.DuplicateIngredientException;
import seedu.restaurant.model.ingredient.exceptions.IngredientNotEnoughException;
import seedu.restaurant.model.ingredient.exceptions.IngredientNotFoundException;
import seedu.restaurant.testutil.ingredient.IngredientBuilder;

//@@author rebstan97
public class UniqueIngredientListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueIngredientList uniqueIngredientList = new UniqueIngredientList();

    @Test
    public void contains_nullIngredient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueIngredientList.contains(null);
    }

    @Test
    public void contains_ingredientNotInList_returnsFalse() {
        assertFalse(uniqueIngredientList.contains(AVOCADO));
    }

    @Test
    public void contains_ingredientInList_returnsTrue() {
        uniqueIngredientList.add(AVOCADO);
        assertTrue(uniqueIngredientList.contains(AVOCADO));
    }

    @Test
    public void add_nullIngredient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueIngredientList.add(null);
    }

    @Test
    public void add_duplicateIngredient_throwsDuplicateIngredientException() {
        uniqueIngredientList.add(AVOCADO);
        thrown.expect(DuplicateIngredientException.class);
        uniqueIngredientList.add(AVOCADO);
    }

    @Test
    public void setIngredient_nullTargetIngredient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueIngredientList.setIngredient(null, AVOCADO);
    }

    @Test
    public void setIngredient_nullEditedIngredient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueIngredientList.setIngredient(AVOCADO, null);
    }

    @Test
    public void setIngredient_targetIngredientNotInList_throwsIngredientNotFoundException() {
        thrown.expect(IngredientNotFoundException.class);
        uniqueIngredientList.setIngredient(AVOCADO, AVOCADO);
    }

    @Test
    public void setIngredient_editedIngredientIsSameIngredient_success() {
        uniqueIngredientList.add(AVOCADO);
        uniqueIngredientList.setIngredient(AVOCADO, AVOCADO);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(AVOCADO);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredient_editedIngredientHasNonUniqueIdentity_throwsDuplicateIngredientException() {
        uniqueIngredientList.add(AVOCADO);
        uniqueIngredientList.add(BROCCOLI);
        thrown.expect(DuplicateIngredientException.class);
        uniqueIngredientList.setIngredient(AVOCADO, BROCCOLI);
    }

    @Test
    public void remove_nullIngredient_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueIngredientList.remove(null);
    }

    @Test
    public void remove_ingredientDoesNotExist_throwsIngredientNotFoundException() {
        thrown.expect(IngredientNotFoundException.class);
        uniqueIngredientList.remove(AVOCADO);
    }

    @Test
    public void remove_existingIngredient_removesIngredient() {
        uniqueIngredientList.add(AVOCADO);
        uniqueIngredientList.remove(AVOCADO);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredients_nullUniqueIngredientList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueIngredientList.setIngredients((UniqueIngredientList) null);
    }

    @Test
    public void setIngredients_uniqueIngredientList_replacesOwnListWithProvidedUniqueIngredientList() {
        uniqueIngredientList.add(AVOCADO);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(BROCCOLI);
        uniqueIngredientList.setIngredients(expectedUniqueIngredientList);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredients_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueIngredientList.setIngredients((List<Ingredient>) null);
    }

    @Test
    public void setIngredients_list_replacesOwnListWithProvidedList() {
        uniqueIngredientList.add(AVOCADO);
        List<Ingredient> ingredientList = Collections.singletonList(BROCCOLI);
        uniqueIngredientList.setIngredients(ingredientList);
        UniqueIngredientList expectedUniqueIngredientList = new UniqueIngredientList();
        expectedUniqueIngredientList.add(BROCCOLI);
        assertEquals(expectedUniqueIngredientList, uniqueIngredientList);
    }

    @Test
    public void setIngredients_listWithDuplicateIngredients_throwsDuplicateIngredientException() {
        List<Ingredient> listWithDuplicateIngredients = Arrays.asList(AVOCADO, AVOCADO);
        thrown.expect(DuplicateIngredientException.class);
        uniqueIngredientList.setIngredients(listWithDuplicateIngredients);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueIngredientList.asUnmodifiableObservableList().remove(0);
    }

    @Test
    public void find_nullIngredientName_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueIngredientList.find(null);
    }

    @Test
    public void find_ingredientNotInList_throwsIngredientNotFoundException() {
        thrown.expect(IngredientNotFoundException.class);
        uniqueIngredientList.find(AVOCADO.getName());
    }

    @Test
    public void find_ingredientInList_assertEquals() {
        uniqueIngredientList.add(AVOCADO);
        assertEquals(AVOCADO, uniqueIngredientList.find(AVOCADO.getName()));
    }

    @Test
    public void stockUp_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueIngredientList.stockUp(null);
    }

    @Test
    public void stockUp_ingredientNotInList_throwsIngredientNotFoundException() {
        HashMap<IngredientName, Integer> requiredIngredients = new HashMap<>();
        requiredIngredients.put(AVOCADO.getName(), 2);
        thrown.expect(IngredientNotFoundException.class);
        uniqueIngredientList.stockUp(requiredIngredients);
    }

    @Test
    public void stockUp_secondIngredientNotInList_throwsIngredientNotEnoughException() {
        HashMap<IngredientName, Integer> requiredIngredients = new HashMap<>();
        Ingredient avocado = new IngredientBuilder(AVOCADO).withNumUnits(15).build();
        Ingredient broccoli = new IngredientBuilder(BROCCOLI).withNumUnits(1).build();
        requiredIngredients.put(avocado.getName(), 10);
        requiredIngredients.put(broccoli.getName(), 2);
        uniqueIngredientList.add(avocado);
        thrown.expect(IngredientNotFoundException.class);
        uniqueIngredientList.stockUp(requiredIngredients);
        Ingredient updatedIngredient = uniqueIngredientList.find(avocado.getName());
        assertEquals(new NumUnits(15), updatedIngredient.getNumUnits());
    }

    @Test
    public void stockUp_ingredientInListWithOneIngredient_assertEquals() {
        HashMap<IngredientName, Integer> requiredIngredients = new HashMap<>();
        Ingredient ingredient = new IngredientBuilder(AVOCADO).withNumUnits(10).build();
        requiredIngredients.put(ingredient.getName(), 2);
        uniqueIngredientList.add(ingredient);
        uniqueIngredientList.stockUp(requiredIngredients);
        Ingredient updatedIngredient = uniqueIngredientList.find(ingredient.getName());
        assertEquals(new NumUnits(12), updatedIngredient.getNumUnits());
    }

    @Test
    public void stockUp_ingredientInListWithMultipleIngredients_assertEquals() {
        HashMap<IngredientName, Integer> requiredIngredients = new HashMap<>();
        requiredIngredients.put(AVOCADO.getName(), 2);
        requiredIngredients.put(BROCCOLI.getName(), 10);
        uniqueIngredientList.add(AVOCADO);
        uniqueIngredientList.add(BROCCOLI);
        uniqueIngredientList.stockUp(requiredIngredients);
        Ingredient updatedIngredient = uniqueIngredientList.find(AVOCADO.getName());
        assertEquals(new NumUnits(2), updatedIngredient.getNumUnits());
        updatedIngredient = uniqueIngredientList.find(BROCCOLI.getName());
        assertEquals(new NumUnits(10), updatedIngredient.getNumUnits());
    }

    @Test
    public void consume_nullIngredientName_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueIngredientList.consume(null);
    }

    @Test
    public void consume_ingredientNotInList_throwsIngredientNotFoundException() {
        HashMap<IngredientName, Integer> requiredIngredients = new HashMap<>();
        requiredIngredients.put(AVOCADO.getName(), 2);
        thrown.expect(IngredientNotFoundException.class);
        uniqueIngredientList.consume(requiredIngredients);
    }

    @Test
    public void consume_secondIngredientNotInList_throwsIngredientNotEnoughException() {
        HashMap<IngredientName, Integer> requiredIngredients = new HashMap<>();
        Ingredient avocado = new IngredientBuilder(AVOCADO).withNumUnits(15).build();
        Ingredient broccoli = new IngredientBuilder(BROCCOLI).withNumUnits(1).build();
        requiredIngredients.put(avocado.getName(), 10);
        requiredIngredients.put(broccoli.getName(), 2);
        uniqueIngredientList.add(avocado);
        thrown.expect(IngredientNotFoundException.class);
        uniqueIngredientList.consume(requiredIngredients);
        Ingredient updatedIngredient = uniqueIngredientList.find(avocado.getName());
        assertEquals(new NumUnits(15), updatedIngredient.getNumUnits());
    }

    @Test
    public void consume_ingredientNotEnough_throwsIngredientNotEnoughException() {
        HashMap<IngredientName, Integer> requiredIngredients = new HashMap<>();
        Ingredient ingredient = new IngredientBuilder(AVOCADO).withNumUnits(1).build();
        requiredIngredients.put(ingredient.getName(), 10);
        uniqueIngredientList.add(ingredient);
        thrown.expect(IngredientNotEnoughException.class);
        uniqueIngredientList.consume(requiredIngredients);
    }

    @Test
    public void consume_secondIngredientNotEnough_throwsIngredientNotEnoughException() {
        HashMap<IngredientName, Integer> requiredIngredients = new HashMap<>();
        Ingredient avocado = new IngredientBuilder(AVOCADO).withNumUnits(15).build();
        Ingredient broccoli = new IngredientBuilder(BROCCOLI).withNumUnits(1).build();
        requiredIngredients.put(avocado.getName(), 10);
        requiredIngredients.put(broccoli.getName(), 2);
        uniqueIngredientList.add(avocado);
        uniqueIngredientList.add(broccoli);
        thrown.expect(IngredientNotEnoughException.class);
        uniqueIngredientList.consume(requiredIngredients);
        Ingredient updatedIngredient = uniqueIngredientList.find(AVOCADO.getName());
        assertEquals(new NumUnits(15), updatedIngredient.getNumUnits());
        updatedIngredient = uniqueIngredientList.find(AVOCADO.getName());
        assertEquals(new NumUnits(1), updatedIngredient.getNumUnits());
    }

    @Test
    public void consume_ingredientInListWithOneIngredient_assertEquals() {
        HashMap<IngredientName, Integer> requiredIngredients = new HashMap<>();
        Ingredient ingredient = new IngredientBuilder(AVOCADO).withNumUnits(12).build();
        requiredIngredients.put(ingredient.getName(), 2);
        uniqueIngredientList.add(ingredient);
        uniqueIngredientList.consume(requiredIngredients);
        Ingredient updatedIngredient = uniqueIngredientList.find(ingredient.getName());
        assertEquals(new NumUnits(10), updatedIngredient.getNumUnits());
    }

    @Test
    public void consume_ingredientInListWithMultipleIngredients_assertEquals() {
        HashMap<IngredientName, Integer> requiredIngredients = new HashMap<>();
        Ingredient avocado = new IngredientBuilder(AVOCADO).withNumUnits(15).build();
        Ingredient broccoli = new IngredientBuilder(BROCCOLI).withNumUnits(22).build();
        requiredIngredients.put(avocado.getName(), 10);
        requiredIngredients.put(broccoli.getName(), 2);
        uniqueIngredientList.add(avocado);
        uniqueIngredientList.add(broccoli);
        uniqueIngredientList.consume(requiredIngredients);
        Ingredient updatedIngredient = uniqueIngredientList.find(avocado.getName());
        assertEquals(new NumUnits(5), updatedIngredient.getNumUnits());
        updatedIngredient = uniqueIngredientList.find(broccoli.getName());
        assertEquals(new NumUnits(20), updatedIngredient.getNumUnits());
    }
}

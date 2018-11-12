package seedu.parking.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.parking.model.Model.PREDICATE_SHOW_ALL_CARPARK;
import static seedu.parking.testutil.TypicalCarparks.ALFA;
import static seedu.parking.testutil.TypicalCarparks.BRAVO;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.parking.model.carpark.CarparkContainsKeywordsPredicate;
import seedu.parking.testutil.CarparkFinderBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasCarpark_nullCarpark_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasCarpark(null);
    }

    @Test
    public void hasCarpark_carparkNotInCarparkFinder_returnsFalse() {
        assertFalse(modelManager.hasCarpark(ALFA));
    }

    @Test
    public void hasCarpark_carparkInCarparkFinder_returnsTrue() {
        modelManager.addCarpark(ALFA);
        assertTrue(modelManager.hasCarpark(ALFA));
    }

    @Test
    public void getFilteredCarparkList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredCarparkList().remove(0);
    }

    @Test
    public void equals() {
        CarparkFinder carparkFinder = new CarparkFinderBuilder().withCarpark(ALFA).withCarpark(BRAVO).build();
        CarparkFinder differentCarparkFinder = new CarparkFinder();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(carparkFinder, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(carparkFinder, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different carparkFinder -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentCarparkFinder, userPrefs)));

        // different filteredList -> returns false
        List<String> temp = new ArrayList<>();
        temp.add(ALFA.getCarparkNumber().toString());
        String[] keywords = temp.toArray(new String[0]);
        modelManager.updateFilteredCarparkList(new CarparkContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(carparkFinder, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCarparkList(PREDICATE_SHOW_ALL_CARPARK);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setCarparkFinderFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(carparkFinder, differentUserPrefs)));
    }
}

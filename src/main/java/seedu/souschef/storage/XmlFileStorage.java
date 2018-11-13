package seedu.souschef.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.souschef.commons.exceptions.DataConversionException;
import seedu.souschef.commons.util.XmlUtil;
import seedu.souschef.logic.parser.Context;
import seedu.souschef.storage.favourite.XmlSerializableFavourite;
import seedu.souschef.storage.healthplan.XmlSerializableHealthPlan;
import seedu.souschef.storage.ingredient.XmlSerializableIngredient;
import seedu.souschef.storage.mealplanner.XmlSerializableMealPlan;
import seedu.souschef.storage.recipe.XmlSerializableRecipe;

/**
 * Stores souschef data in an XML file
 */
public class XmlFileStorage {

    /**
     * Saves the given SousChef data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableGeneric xmlContent)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, xmlContent);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns SousChef in the file or an empty Souschef
     */
    public static XmlSerializableGeneric loadDataFromSaveFile(Path file, Context context)
            throws DataConversionException, FileNotFoundException {
        try {
            if (Context.RECIPE.equals(context)) {
                return XmlUtil.getDataFromFile(file, XmlSerializableRecipe.class);
            } else if (Context.INGREDIENT.equals(context)) {
                return XmlUtil.getDataFromFile(file, XmlSerializableIngredient.class);
            } else if (Context.HEALTH_PLAN.equals(context)) {
                return XmlUtil.getDataFromFile(file, XmlSerializableHealthPlan.class);
            } else if (Context.MEAL_PLAN.equals(context)) {
                return XmlUtil.getDataFromFile(file, XmlSerializableMealPlan.class);
            } else if (Context.FAVOURITES.equals(context)) {
                return XmlUtil.getDataFromFile(file, XmlSerializableFavourite.class);
            }

        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
        return null;
    }
}

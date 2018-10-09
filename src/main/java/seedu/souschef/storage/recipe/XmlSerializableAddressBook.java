package seedu.souschef.storage.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.souschef.commons.exceptions.IllegalValueException;
import seedu.souschef.model.AppContent;
import seedu.souschef.model.ReadOnlyAppContent;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.storage.XmlSerializableGeneric;

/**
 * An Immutable AppContent that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook extends XmlSerializableGeneric {

    public static final String MESSAGE_DUPLICATE_RECIPE = "Recipes list contains duplicate recipe(s).";

    @XmlElement
    private List<XmlAdaptedRecipe> recipes;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        recipes = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAppContent src) {
        this();
        recipes.addAll(src.getObservableRecipeList().stream().map(XmlAdaptedRecipe::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the recipeModel's {@code AppContent} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedRecipe}.
     */
    @Override
    public AppContent toModelType() throws IllegalValueException {
        AppContent addressBook = new AppContent();
        for (XmlAdaptedRecipe p : recipes) {
            Recipe recipe = p.toModelType();
            if (addressBook.getRecipes().contains(recipe)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RECIPE);
            }
            addressBook.getRecipes().add(recipe);
        }
        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }
        return recipes.equals(((XmlSerializableAddressBook) other).recipes);
    }
}

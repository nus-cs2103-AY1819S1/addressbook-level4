package seedu.souschef.storage.favourite;

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
import seedu.souschef.storage.recipe.XmlAdaptedRecipe;

/**
 * An Immutable AppContent that is serializable to XML format
 */
@XmlRootElement(name = "souschef")
public class XmlSerializableFavourite implements XmlSerializableGeneric {

    public static final String MESSAGE_DUPLICATE_RECIPE = "Favourites list contains duplicate recipe(s).";

    @XmlElement
    private List<XmlAdaptedFavourite> recipes;

    private AppContent appContent;

    /**
     * Creates an empty XmlSerializableRecipe.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableFavourite() {
        recipes = new ArrayList<>();
        appContent = new AppContent();
    }

    public XmlSerializableFavourite(seedu.souschef.storage.recipe.XmlSerializableRecipe ab) {
        recipes = ab.recipes;
        this.appContent = ab.appContent;
        recipes.addAll(ab.appContent.getObservableRecipeList().stream().map(XmlAdaptedRecipe::new)
                .collect(Collectors.toList()));
    }

    public XmlSerializableFavourite(AppContent appContent) {
        this();
        if (appContent != null) {
            this.appContent = appContent;
        } else {
            appContent = new AppContent();

        }
        recipes.addAll(appContent.getObservableFavouritesList().stream().map(XmlAdaptedFavourite::new)
                .collect(Collectors.toList()));
    }

    /**
     * Conversion
     */
    public XmlSerializableFavourite(ReadOnlyAppContent src) {
        this();
        if (appContent != null) {
            this.appContent = (AppContent) src;
        } else {
            appContent = new AppContent();
        }
        recipes.addAll(src.getObservableFavouritesList().stream().map(XmlAdaptedFavourite::new).collect(Collectors.toList()));
    }

    /**
     * Converts this souschef into the recipeModel's {@code AppContent} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedRecipe}.
     */
    @Override
    public AppContent toModelType() throws IllegalValueException {
        for (XmlAdaptedFavourite p : recipes) {
            Recipe recipe = p.toModelType();
            if (this.appContent.getRecipes().contains(recipe)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RECIPE);
            }
            appContent.getRecipes().add(recipe);
        }
        return appContent;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.souschef.storage.favourite.XmlSerializableFavourite)) {
            return false;
        }
        return recipes.equals(((seedu.souschef.storage.favourite.XmlSerializableFavourite) other).recipes);
    }
}

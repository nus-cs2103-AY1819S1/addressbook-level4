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
public class XmlSerializableFavourte implements XmlSerializableGeneric {

    public static final String MESSAGE_DUPLICATE_FAVOURITE_RECIPE = "Recipes list contains duplicate favourite recipe(s).";

    @XmlElement
    private List<XmlAdaptedFavourite> favourites;

    private AppContent appContent;

    /**
     * Creates an empty XmlSerializableRecipe.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableFavourte() {
        favourites = new ArrayList<>();
        appContent = new AppContent();
    }

    public XmlSerializableFavourte(seedu.souschef.storage.favourite.XmlSerializableFavourte ab) {
        favourites = ab.favourites;
        this.appContent = ab.appContent;
        favourites.addAll(ab.appContent.getObservableFavouritesList().stream().map(XmlAdaptedFavourite::new)
                .collect(Collectors.toList()));
    }

    public XmlSerializableFavourte(AppContent appContent) {
        this();
        if (appContent != null) {
            this.appContent = appContent;
        } else {
            appContent = new AppContent();

        }
        favourites.addAll(appContent.getObservableFavouritesList().stream().map(XmlAdaptedFavourite::new)
                .collect(Collectors.toList()));
    }

    /**
     * Conversion
     */
    public XmlSerializableFavourte(ReadOnlyAppContent src) {
        this();
        if (appContent != null) {
            this.appContent = (AppContent) src;
        } else {
            appContent = new AppContent();
        }
        favourites.addAll(src.getObservableFavouritesList().stream().map(XmlAdaptedFavourite::new).collect(Collectors.toList()));
    }

    /**
     * Converts this souschef into the recipeModel's {@code AppContent} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedRecipe}.
     */
    @Override
    public AppContent toModelType() throws IllegalValueException {
        for (XmlAdaptedFavourite p : favourites) {
            Recipe recipe = p.toModelType();
            if (this.appContent.getFavourites().contains(recipe)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FAVOURITE_RECIPE);
            }
            appContent.getFavourites().add(recipe);
        }
        return appContent;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.souschef.storage.recipe.XmlSerializableFavourite)) {
            return false;
        }
        return favourites.equals(((seedu.souschef.storage.favourite.XmlSerializableFavourite) other).favourites);
    }
}

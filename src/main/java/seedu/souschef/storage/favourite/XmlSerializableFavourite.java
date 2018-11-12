package seedu.souschef.storage.favourite;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.souschef.commons.exceptions.IllegalValueException;
import seedu.souschef.model.AppContent;
import seedu.souschef.model.ReadOnlyAppContent;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.storage.XmlSerializableGeneric;
import seedu.souschef.storage.recipe.XmlAdaptedLiteRecipe;

/**
 * An Immutable AppContent that is serializable to XML format
 */
@XmlRootElement(name = "souschef")
public class XmlSerializableFavourite implements XmlSerializableGeneric {

    public static final String MESSAGE_DUPLICATE_FAVOURITE_RECIPE = "Recipes list contains duplicate favourite recipe(s).";

    @XmlElement
    private XmlAdaptedFavourite favourites;

    private AppContent appContent;

    /**
     * Creates an empty XmlSerializableFavourite.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableFavourite() {
        favourites = new XmlAdaptedFavourite();
        appContent = new AppContent();
    }

    public XmlSerializableFavourite(seedu.souschef.storage.favourite.XmlSerializableFavourite ab) {
        favourites = ab.favourites;
        this.appContent = ab.appContent;
        //favourites.addAll(ab.appContent.getObservableFavouritesList().stream().map(XmlAdaptedFavourite::new)
          //      .collect(Collectors.toList()));
    }

    public XmlSerializableFavourite(AppContent appContent) {
        this();
        if (appContent != null) {
            this.appContent = appContent;
        } else {
            appContent = new AppContent();

        }
       // favourites.addAll(appContent.getObservableFavouritesList().stream().map(XmlAdaptedFavourite::new)
         //       .collect(Collectors.toList()));
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
        favourites = new XmlAdaptedFavourite(src.getObservableFavouritesList()
                .subList(0, src.getObservableFavouritesList().size() - 1));
    }

    /**
     * Converts this souschef into the favouriteModel's {@code AppContent} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedFavourite}.
     */
    @Override
    public AppContent toModelType() throws IllegalValueException {
    /*    for (XmlAdaptedFavourite p : favourites) {
            List<Recipe> recipe = p.toModelType();
            if (this.appContent.getFavourites().contains(recipe)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FAVOURITE_RECIPE);
            }
            appContent.getFavourites().add(recipe);
        }*/
        for (Recipe r : favourites.toModelType()) {
            appContent.getFavourites().add(r);
        }
        return appContent;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableFavourite)) {
            return false;
        }
        return favourites.equals(((XmlSerializableFavourite) other).favourites);
    }
}

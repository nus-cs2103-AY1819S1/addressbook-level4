package seedu.souschef.storage.favourite;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.souschef.commons.exceptions.IllegalValueException;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.storage.recipe.XmlAdaptedLiteRecipe;


/**
 * class for xml context to model
 * xml favourite
 */
public class XmlAdaptedFavourite {


    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Recipe's %s field is missing!";

    @XmlElement(required = true)
    private List<XmlAdaptedLiteRecipe> favourites;


    //base constructor
    public XmlAdaptedFavourite(){
        favourites = new ArrayList<>();
    }

    /*public XmlAdaptedFavourite(ArrayList<XmlAdaptedLiteRecipe> favourites) {
        this.favourites = favourites;
    }*/

    public XmlAdaptedFavourite(List<Recipe> source) {

        favourites = source.stream()
                .map(XmlAdaptedLiteRecipe::new)
                .collect(Collectors.toList());
    }

    /**

     *
     * Method to model

     * to model

     */
    public ArrayList<Recipe> toModelType() throws IllegalValueException {

        ArrayList<Recipe> list = new ArrayList<Recipe>();

        for (XmlAdaptedLiteRecipe r : favourites) {
            list.add(r.toModelType());
        }

        return list;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.souschef.storage.favourite.XmlAdaptedFavourite)) {
            return false;
        }

        seedu.souschef.storage.favourite.XmlAdaptedFavourite otherFavourite = (seedu.souschef.storage.favourite.XmlAdaptedFavourite) other;
        return Objects.equals(favourites, otherFavourite);
    }
}

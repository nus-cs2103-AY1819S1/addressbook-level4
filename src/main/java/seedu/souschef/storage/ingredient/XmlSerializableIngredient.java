package seedu.souschef.storage.ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.souschef.commons.core.LogsCenter;
import seedu.souschef.commons.exceptions.IllegalValueException;
import seedu.souschef.model.AppContent;
import seedu.souschef.model.ReadOnlyAppContent;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.storage.XmlSerializableGeneric;

/**
 *
 * class to parse xml to model from the provided file
 * xml health ingredient
 */
@XmlRootElement(name = "ingredients")
public class XmlSerializableIngredient implements XmlSerializableGeneric {


    public static final String MESSAGE_DUPLICATE_INGREDIENT = "ingredient list contains duplicate ingredient(s).";
    private static final Logger logger = LogsCenter.getLogger(XmlSerializableIngredient.class);

    @XmlElement
    private List<XmlAdaptedIngredient> ig;

    private AppContent appContent;

    public XmlSerializableIngredient() {

        ig = new ArrayList<>();
        appContent = new AppContent();
    }

    /**
     * construct
     */
    public XmlSerializableIngredient(ReadOnlyAppContent src) {
       this();

        ig.addAll(src.getObservableIngredientList().stream()
                .map(XmlAdaptedIngredient::new).collect(Collectors.toList()));
    }

    public XmlSerializableIngredient(XmlSerializableIngredient ab) {

        ig = ab.ig;
        this.appContent = ab.appContent;
        ig.addAll(ab.appContent.getObservableIngredientList().stream().map(XmlAdaptedIngredient::new)
                .collect(Collectors.toList()));
    }

    public XmlSerializableIngredient(AppContent appContent) {
        this();
        if (appContent != null) {
            this.appContent = appContent;
        } else {
            appContent = new AppContent();

        }
        ig.addAll(appContent.getObservableIngredientList().stream().map(XmlAdaptedIngredient::new)
                .collect(Collectors.toList()));
    }

    /**

     * Convert to AppContent Model
     * change to model
     */
    public AppContent toModelType() throws IllegalValueException {

        for (XmlAdaptedIngredient i : ig) {
            Ingredient ingredient = i.toModelType();
            if (this.appContent.getIngredients().contains(ingredient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INGREDIENT);
            }
            appContent.getIngredients().add(ingredient);
        }
        return appContent;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableIngredient)) {
            return false;
        }
        return ig.equals(((XmlSerializableIngredient) other).ig);
    }




}

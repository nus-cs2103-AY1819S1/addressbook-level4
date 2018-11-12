package seedu.souschef.storage.mealplanner;

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
import seedu.souschef.model.planner.Day;
import seedu.souschef.storage.XmlSerializableGeneric;

/**
 *
 * class to parse xml to model from the provided file
 * xml health plan
 */
@XmlRootElement(name = "mealplans")
public class XmlSerializableMealPlan implements XmlSerializableGeneric {


    public static final String MESSAGE_DUPLICATE_PLAN = "plan list contains duplicate plan(s).";
    private static final Logger logger = LogsCenter.getLogger(XmlSerializableMealPlan.class);

    @XmlElement
    private List<XmlAdaptedMealPlan> mp;

    private AppContent appContent;

    public XmlSerializableMealPlan() {

        mp = new ArrayList<>();
        appContent = new AppContent();
    }

    /**
     * construct
     */
    public XmlSerializableMealPlan(ReadOnlyAppContent src) {
        this();

        mp.addAll(src.getObservableMealPlanner().stream()
            .map(XmlAdaptedMealPlan::new).collect(Collectors.toList()));
    }

    public XmlSerializableMealPlan(XmlSerializableMealPlan ab) {

        mp = ab.mp;
        this.appContent = ab.appContent;
        mp.addAll(ab.appContent.getObservableMealPlanner().stream().map(XmlAdaptedMealPlan::new)
            .collect(Collectors.toList()));
    }

    public XmlSerializableMealPlan(AppContent appContent) {
        this();
        if (appContent != null) {
            this.appContent = appContent;
        } else {
            appContent = new AppContent();

        }
        mp.addAll(appContent.getObservableMealPlanner().stream().map(XmlAdaptedMealPlan::new)
            .collect(Collectors.toList()));
    }

    /**

     * Convert to AppContent Model
     * change to model
     */
    public AppContent toModelType() throws IllegalValueException {

        for (XmlAdaptedMealPlan p : mp) {
            Day plan = p.toModelType();
            if (this.appContent.getMealPlanner().contains(plan)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PLAN);
            }
            appContent.getMealPlanner().add(plan);
        }
        return appContent;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableMealPlan)) {
            return false;
        }
        return mp.equals(((XmlSerializableMealPlan) other).mp);
    }
}

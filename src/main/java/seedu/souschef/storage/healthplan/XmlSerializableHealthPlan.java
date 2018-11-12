package seedu.souschef.storage.healthplan;

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
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.storage.XmlSerializableGeneric;

/**
 *
 * class to parse xml to model from the provided file
 * xml health plan
 */
@XmlRootElement(name = "healthplans")
public class XmlSerializableHealthPlan implements XmlSerializableGeneric {


    public static final String MESSAGE_DUPLICATE_PLAN = "plan list contains duplicate plan(s).";
    private static final Logger logger = LogsCenter.getLogger(XmlSerializableHealthPlan.class);

    @XmlElement
    private List<XmlAdaptedHealthPlan> hp;

    private AppContent appContent;

    public XmlSerializableHealthPlan() {

        hp = new ArrayList<>();
        appContent = new AppContent();
    }

    /**
     * construct
     */
    public XmlSerializableHealthPlan(ReadOnlyAppContent src) {
       this();

        hp.addAll(src.getObservableHealthPlanList().stream()
                .map(XmlAdaptedHealthPlan::new).collect(Collectors.toList()));
    }

    public XmlSerializableHealthPlan(XmlSerializableHealthPlan ab) {

        hp = ab.hp;
        this.appContent = ab.appContent;
        hp.addAll(ab.appContent.getObservableHealthPlanList().stream().map(XmlAdaptedHealthPlan::new)
                .collect(Collectors.toList()));
    }

    public XmlSerializableHealthPlan(AppContent appContent) {
        this();
        if (appContent != null) {
            this.appContent = appContent;
        } else {
            appContent = new AppContent();

        }
        hp.addAll(appContent.getObservableHealthPlanList().stream().map(XmlAdaptedHealthPlan::new)
                .collect(Collectors.toList()));
    }

    /**

     * Convert to AppContent Model
     * change to model
     */
    public AppContent toModelType() throws IllegalValueException {

        for (XmlAdaptedHealthPlan p : hp) {
            HealthPlan plan = p.toModelType();
            if (this.appContent.getHealthPlans().contains(plan)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PLAN);
            }
            appContent.getHealthPlans().add(plan);
        }
        return appContent;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableHealthPlan)) {
            return false;
        }
        return hp.equals(((XmlSerializableHealthPlan) other).hp);
    }




}

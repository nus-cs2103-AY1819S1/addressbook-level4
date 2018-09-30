package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.AppContent;
import seedu.address.model.ReadOnlyAppContent;
import seedu.address.model.healthplan.HealthPlan;


@XmlRootElement(name = "healthplans")
public class XmlSerializableHealthPlan {


    public static final String MESSAGE_DUPLICATE_PLAN = "Persons list contains duplicate plan(s).";
    private static final Logger logger = LogsCenter.getLogger(XmlSerializableHealthPlan.class);



    @XmlElement
    private List<XmlAdaptedHealthPlan> hp;

    public XmlSerializableHealthPlan() {
        hp = new ArrayList<>();
    }


    public XmlSerializableHealthPlan(ReadOnlyAppContent src) {
        this();
        hp.addAll(src.getHealthPlanList().stream().map(XmlAdaptedHealthPlan::new).collect(Collectors.toList()));
    }

    public AppContent toModelType() throws IllegalValueException {
        AppContent hpb = new AppContent();
        int i=0;
        for (XmlAdaptedHealthPlan p : hp) {
            HealthPlan plan = p.toModelType();
            if (hpb.hasPlan(plan)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PLAN);
            }
            hpb.addPlan(plan);
            i++;
            logger.info(String.valueOf(i));
        }
        return hpb;
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

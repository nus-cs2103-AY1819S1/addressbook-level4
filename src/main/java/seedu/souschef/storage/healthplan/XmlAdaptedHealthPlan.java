package seedu.souschef.storage.healthplan;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.souschef.commons.exceptions.IllegalValueException;
import seedu.souschef.model.healthplan.Age;
import seedu.souschef.model.healthplan.CurrentHeight;
import seedu.souschef.model.healthplan.CurrentWeight;
import seedu.souschef.model.healthplan.Duration;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.healthplan.HealthPlanName;
import seedu.souschef.model.healthplan.Scheme;
import seedu.souschef.model.healthplan.TargetWeight;
import seedu.souschef.model.planner.Day;
import seedu.souschef.storage.mealplanner.XmlAdaptedMealPlan;


/**
 * class for xml context to model
 * xml health plan
 */
public class XmlAdaptedHealthPlan {


    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Plan's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String tweight;
    @XmlElement(required = true)
    private String cweight;
    @XmlElement(required = true)
    private String cheight;
    @XmlElement(required = true)
    private String age;
    @XmlElement(required = true)
    private String duration;
    @XmlElement(required = true)
    private String scheme;

    @XmlElement
    private List<XmlAdaptedMealPlan> days = new ArrayList<>();


    //base constructor
    public XmlAdaptedHealthPlan(){}

    public XmlAdaptedHealthPlan(String name, String tweight, String cweight, String cheight, String age,
                                String duration, String scheme, List<XmlAdaptedMealPlan> days) {

        this.name = name;
        this.tweight = tweight;
        this.cweight = cweight;
        this.cheight = cheight;
        this.age = age;
        this.duration = duration;
        this.scheme = scheme;

        if (days != null) {
            this.days = new ArrayList<>(days);
        }

    }

    public XmlAdaptedHealthPlan(HealthPlan source) {
        name = source.getHealthPlanName().planName;
        tweight = source.getTargetWeight().value;
        cweight = source.getCurrentWeight().value;
        cheight = source.getCurrentHeight().value;
        age = source.getAge().value;
        duration = source.getDuration().value;
        if (source.getScheme() == Scheme.GAIN) {
            scheme = Scheme.GAIN.toString();
        } else if (source.getScheme() == Scheme.LOSS) {
            scheme = Scheme.LOSS.toString();
        } else {
            scheme = Scheme.MAINTAIN.toString();
        }

        days = source.getMealPlans().stream()
                .map(XmlAdaptedMealPlan::new)
                .collect(Collectors.toList());

    }

    /**

     *
     * Method to model

     * to model

     */
    public HealthPlan toModelType() throws IllegalValueException {

        //handle the meals

        final List<Day> dayList = new ArrayList<>();
        for (XmlAdaptedMealPlan dayToAdd : days) {
            dayList.add(dayToAdd.toModelType());
        }


        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    HealthPlanName.class.getSimpleName()));
        }

        if (!HealthPlanName.isValidName(name)) {
            throw new IllegalValueException(HealthPlanName.MESSAGE_NAME_CONSTRAINTS);
        }
        final HealthPlanName modelName = new HealthPlanName(name);

        if (tweight == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TargetWeight.class.getSimpleName()));
        }

        if (!TargetWeight.isValidWeight(tweight)) {
            throw new IllegalValueException(TargetWeight.MESSAGE_WEIGHT_CONSTRAINTS);
        }
        final TargetWeight modelTWeight = new TargetWeight(tweight);

        if (cweight == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CurrentWeight.class.getSimpleName()));
        }

        if (!CurrentWeight.isValidWeight(cweight)) {
            throw new IllegalValueException(CurrentWeight.MESSAGE_WEIGHT_CONSTRAINTS);
        }
        final CurrentWeight modelCweight = new CurrentWeight(cweight);

        if (cheight == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CurrentHeight.class.getSimpleName()));
        }
        if (!CurrentHeight.isValidHeight(cheight)) {
            throw new IllegalValueException(CurrentHeight.MESSAGE_HEIGHT_CONSTRAINTS);
        }
        final CurrentHeight modelCHeight = new CurrentHeight(cheight);

        if (age == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName()));
        }

        if (!Age.isValidAge(age)) {
            throw new IllegalValueException(Age.MESSAGE_AGE_CONSTRAINTS);
        }
        final Age modelAge = new Age(age);


        if (duration == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Duration.class.getSimpleName()));
        }
        if (!Duration.isValidDuration(duration)) {
            throw new IllegalValueException(Duration.MESSAGE_DURATION_CONSTRAINTS);
        }
        final Duration modelDuration = new Duration(duration);

        Scheme modelScheme = Scheme.valueOf(scheme);

        final ArrayList<Day> modelDays = new ArrayList<>(dayList);

        return new HealthPlan(modelName, modelTWeight, modelCweight, modelCHeight,
                modelAge, modelDuration, modelScheme, modelDays);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedHealthPlan)) {
            return false;
        }

        XmlAdaptedHealthPlan otherPlan = (XmlAdaptedHealthPlan) other;
        return Objects.equals(name, otherPlan.name)
                && Objects.equals(tweight, otherPlan.tweight)
                && Objects.equals(cweight, otherPlan.cweight)
                && Objects.equals(cheight, otherPlan.cheight)
                && Objects.equals(age, otherPlan.age)
                && Objects.equals(duration, otherPlan.duration)
                && Objects.equals(scheme, otherPlan.scheme);

    }



}

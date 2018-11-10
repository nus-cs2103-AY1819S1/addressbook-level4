package seedu.souschef.storage.mealplanner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.souschef.commons.exceptions.IllegalValueException;
import seedu.souschef.model.planner.Breakfast;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.planner.Dinner;
import seedu.souschef.model.planner.Lunch;
import seedu.souschef.model.planner.Meal;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.storage.recipe.XmlAdaptedLiteRecipe;


/**
 * class for xml context to model
 * xml health plan
 */
public class XmlAdaptedMealPlan {


    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Plan's %s field is missing!";

    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private XmlAdaptedLiteRecipe breakfast;
    @XmlElement(required = true)
    private XmlAdaptedLiteRecipe lunch;
    @XmlElement(required = true)
    private XmlAdaptedLiteRecipe dinner;


    //base constructor
    public XmlAdaptedMealPlan(){}

    public XmlAdaptedMealPlan(String date, XmlAdaptedLiteRecipe breakfast,
            XmlAdaptedLiteRecipe lunch, XmlAdaptedLiteRecipe dinner) {

        this.date = date;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;

    }

    public XmlAdaptedMealPlan(Day source) {
        date = source.getDate().toString();
        if (!source.getMeal(Breakfast.SLOT).isEmpty()) {
            Recipe sourceRecipe = source.getMeal(Breakfast.SLOT).getRecipe();
            String sourceName = sourceRecipe.getName().fullName;
            String sourceDifficulty = sourceRecipe.getDifficulty().toString();
            String sourceCookTime = sourceRecipe.getCookTime().toString();
            breakfast = new XmlAdaptedLiteRecipe(sourceName, sourceDifficulty, sourceCookTime);
        } else {
            breakfast = null;
        }

        if (!source.getMeal(Lunch.SLOT).isEmpty()) {
            Recipe sourceRecipe = source.getMeal(Lunch.SLOT).getRecipe();
            String sourceName = sourceRecipe.getName().fullName;
            String sourceDifficulty = sourceRecipe.getDifficulty().toString();
            String sourceCookTime = sourceRecipe.getCookTime().toString();
            lunch = new XmlAdaptedLiteRecipe(sourceName, sourceDifficulty, sourceCookTime);
        } else {
            lunch = null;
        }

        if (!source.getMeal(Dinner.SLOT).isEmpty()) {
            Recipe sourceRecipe = source.getMeal(Dinner.SLOT).getRecipe();
            String sourceName = sourceRecipe.getName().fullName;
            String sourceDifficulty = sourceRecipe.getDifficulty().toString();
            String sourceCookTime = sourceRecipe.getCookTime().toString();
            lunch = new XmlAdaptedLiteRecipe(sourceName, sourceDifficulty, sourceCookTime);
        } else {
            dinner = null;
        }
    }

    /**

     *
     * Method to model

     * to model

     */
    public Day toModelType() throws IllegalValueException {

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalDate.class.getSimpleName()));
        }

        LocalDate modelDate = LocalDate.parse(date);
        ArrayList<Meal> modelMeals = new ArrayList<>();

        if (breakfast != null) {
            modelMeals.add(0, new Breakfast(breakfast.toModelType()));
        } else {
            modelMeals.add(0, new Breakfast());
        }

        if (lunch != null) {
            modelMeals.add(1, new Lunch(lunch.toModelType()));
        } else {
            modelMeals.add(1, new Lunch());
        }

        if (dinner != null) {
            modelMeals.add(2, new Dinner(dinner.toModelType()));
        } else {
            modelMeals.add(2, new Dinner());

        }

        return new Day(modelDate, modelMeals);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMealPlan)) {
            return false;
        }

        XmlAdaptedMealPlan otherPlan = (XmlAdaptedMealPlan) other;
        return Objects.equals(date, otherPlan.date)
            && Objects.equals(breakfast, otherPlan.breakfast)
            && Objects.equals(lunch, otherPlan.lunch)
            && Objects.equals(dinner, otherPlan.dinner);

    }
}

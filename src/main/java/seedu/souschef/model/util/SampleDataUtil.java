package seedu.souschef.model.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.souschef.model.AppContent;
import seedu.souschef.model.ReadOnlyAppContent;

import seedu.souschef.model.healthplan.Age;
import seedu.souschef.model.healthplan.CurrentHeight;
import seedu.souschef.model.healthplan.CurrentWeight;
import seedu.souschef.model.healthplan.Duration;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.healthplan.HealthPlanName;
import seedu.souschef.model.healthplan.Scheme;
import seedu.souschef.model.healthplan.TargetWeight;

import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.ingredient.IngredientAmount;
import seedu.souschef.model.ingredient.IngredientDate;
import seedu.souschef.model.ingredient.IngredientName;
import seedu.souschef.model.ingredient.IngredientPortion;
import seedu.souschef.model.ingredient.IngredientServingUnit;

import seedu.souschef.model.planner.Day;
import seedu.souschef.model.planner.Meal;
import seedu.souschef.model.recipe.CookTime;
import seedu.souschef.model.recipe.Difficulty;
import seedu.souschef.model.recipe.Instruction;
import seedu.souschef.model.recipe.Name;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AppContent} with sample data.
 */
public class SampleDataUtil {
    public static Recipe[] getSampleRecipes() {
        return new Recipe[] {
            new Recipe(new Name("Chicken Rice"), new Difficulty("3"), new CookTime("PT40M"),
                    getInstructionList(
                            new Instruction("Slice and clean up the inner of the chicken 1.2 kg.",
                                    getIngredientPortionSet(new IngredientPortion("Chicken", "kg", 1.2))),
                            new Instruction("Boil the chicken in water 700 ml.",
                                    getIngredientPortionSet(new IngredientPortion("water", "ml", 700.0))),
                            new Instruction("Remove 200 ml of water and put soy sauce 100 ml.",
                                    getIngredientPortionSet(new IngredientPortion("soy sauce", "ml", 100.0))),
                            new Instruction("Cook for 20 mins.", new CookTime("PT20M"), getIngredientPortionSet())
                    ), getTagSet("Asian", "Singapore", "Poultry")),
            new Recipe(new Name("Black pepper Chicken"), new Difficulty("2"), new CookTime("PT20M"),
                    getInstructionList(
                            new Instruction("Slice and clean up the inner of the chicken 1.2 kg.",
                                    getIngredientPortionSet(new IngredientPortion("Chicken", "kg", 1.2))),
                            new Instruction("Heat up the pan with old 2 tablespoon.",
                                    getIngredientPortionSet(new IngredientPortion("oil", "tablespoon", 2.0))),
                            new Instruction("Add oyster sauce 3 tablespoon, black pepper 20 g and water 50 ml.",
                                    getIngredientPortionSet(new IngredientPortion("oyster sauce", "tablespoon",
                                            3.0))),
                            new Instruction("Stir-fry for 10 mins.", new CookTime("PT10M"), getIngredientPortionSet())
                    ),
                    getTagSet("Asian", "Spicy", "Poultry")),
            new Recipe(new Name("Fried Chinese Noodles"), new Difficulty("1"), new CookTime("PT20M"),
                    getInstructionList(
                            new Instruction("Slice vegetables 70 gram of any kind.",
                                    getIngredientPortionSet(new IngredientPortion("vegetables", "gram", 70.0))),
                            new Instruction("Add water 200 ml and noodles 300 g into the pan.",
                                    getIngredientPortionSet(new IngredientPortion("water", "ml", 200.0),
                                            new IngredientPortion("noodles", "gram", 300.0))),
                            new Instruction("Remove the water and put soy sauce 50 ml.",
                                    getIngredientPortionSet(new IngredientPortion("soy sauce", "ml", 50.0))),
                            new Instruction("Fry for 7 mins.", new CookTime("PT7M"), getIngredientPortionSet())
                    ),
                    getTagSet("Asian", "Staple", "Vegetarian")),
            new Recipe(new Name("Claypot Rice"), new Difficulty("3"), new CookTime("PT50M"),
                    getInstructionList(
                            new Instruction("Slice and clean up the inner of the chicken 1.2 kg.",
                                    getIngredientPortionSet(new IngredientPortion("Chicken", "kg", 1.2))),
                            new Instruction("Boil the chicken in water 700 ml.",
                                    getIngredientPortionSet(new IngredientPortion("water", "ml", 700.0))),
                            new Instruction("Remove 200 ml of water and put soy sauce 100 ml.",
                                    getIngredientPortionSet(new IngredientPortion("soy sauce", "ml", 100.0))),
                            new Instruction("Cook for 20 mins.", new CookTime("PT20M"), getIngredientPortionSet())
                    ),
                    getTagSet("Staple", "Poultry")),
            new Recipe(new Name("Roti Prata"), new Difficulty("3"), new CookTime("PT40M"),
                    getInstructionList(
                            new Instruction("Sift flour, add salt and water to make dough.",
                                    getIngredientPortionSet(new IngredientPortion("flour", "g", 300.0),
                                            new IngredientPortion("ghee", "cup", 0.5),
                                            new IngredientPortion("water", "cup", 0.5))),
                            new Instruction("Knead thoroughly for 5 minutes.",
                                    new CookTime("PT5M"), getIngredientPortionSet()),
                            new Instruction("Heat one teaspoon of ghee or butter on a metal "
                                    + "griddle or heavy iron pan."),
                            new Instruction("Fry each prata dough until brown on both sides.")
                    ),
                    getTagSet("Staple", "Vegetarian", "Halal"))
        };
    }

    public static Ingredient[] getSampleIngredient() {

        Ingredient [] ingredients = new Ingredient[]{};
        try {
            ingredients = new Ingredient[]{
                new Ingredient(new IngredientName("Carrot"), new IngredientAmount(300.0),
                            new IngredientServingUnit("gram"),
                        new IngredientDate(new SimpleDateFormat("MM-dd-yyyy").parse("12-25-2018"))),
                new Ingredient(new IngredientName("Tomato"), new IngredientAmount(200.0),
                            new IngredientServingUnit("gram"),
                        new IngredientDate(new SimpleDateFormat("MM-dd-yyyy").parse("12-26-2018"))),
                new Ingredient(new IngredientName("Potato"), new IngredientAmount(100.0),
                            new IngredientServingUnit("gram"),
                        new IngredientDate(new SimpleDateFormat("MM-dd-yyyy").parse("12-24-2018")))
            };
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ingredients;

    }

    public static HealthPlan[] getSampleHealthPlan() {
        return new HealthPlan[] {
            new HealthPlan(new HealthPlanName("Lose weight"), new TargetWeight("70"), new CurrentWeight("80"),
                  new CurrentHeight("160"), new Age("25"), new Duration("10"), Scheme.LOSS, new ArrayList<Day>()),
            new HealthPlan(new HealthPlanName("gain weight"), new TargetWeight("60"), new CurrentWeight("50"),
                  new CurrentHeight("160"), new Age("27"), new Duration("10"), Scheme.GAIN, new ArrayList<Day>())
        };
    }

    public static Day[] getDay() {

        LocalDate modelDate = LocalDate.parse("2018-10-22");
        LocalDate modelDate2 = LocalDate.parse("2018-10-23");

        ArrayList<Meal> list = new ArrayList<>();
        ArrayList<Meal> list2 = new ArrayList<>();

        list.add(new Meal(Meal.Slot.BREAKFAST, getSampleRecipes()[0]));
        list.add(new Meal(Meal.Slot.LUNCH, getSampleRecipes()[1]));
        list.add(new Meal(Meal.Slot.DINNER, getSampleRecipes()[2]));

        list2.add(new Meal(Meal.Slot.BREAKFAST, getSampleRecipes()[2]));
        list2.add(new Meal(Meal.Slot.LUNCH, getSampleRecipes()[3]));
        list2.add(new Meal(Meal.Slot.DINNER, getSampleRecipes()[2]));

        return new Day[] {
            new Day (modelDate, list),
            new Day (modelDate2, list2)

        };
    }





    public static ReadOnlyAppContent getSampleAddressBook() {
        AppContent sampleAb = new AppContent();
        for (Recipe sampleRecipe : getSampleRecipes()) {
            sampleAb.getRecipes().add(sampleRecipe);
        }
        return sampleAb;
    }

    public static ReadOnlyAppContent getSampleIngredients() {
        AppContent sampleIngredients = new AppContent();
        for (Ingredient sampleIngredient : getSampleIngredient()) {
            sampleIngredients.getIngredients().add(sampleIngredient);
        }
        return sampleIngredients;
    }

    public static ReadOnlyAppContent getSampleHealthPlans() {
        AppContent sampleHealthPlans = new AppContent();
        for (HealthPlan sampleHealthPlan : getSampleHealthPlan()) {
            sampleHealthPlans.getHealthPlans().add(sampleHealthPlan);

        }
        return sampleHealthPlans;
    }

    public static ReadOnlyAppContent getSampleDays() {
        AppContent sampleDays = new AppContent();
        for (Day sampleDay : getDay()) {
            sampleDays.getMealPlanner().add(sampleDay);

        }
        return sampleDays;
    }



    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static List<Instruction> getInstructionList(String... strings) {
        return Arrays.stream(strings)
                .map(Instruction::new)
                .collect(Collectors.toList());
    }

    public static List<Instruction> getInstructionList(Instruction... instructions) {
        return Arrays.stream(instructions)
                .collect(Collectors.toList());
    }

    public static Set<IngredientPortion> getIngredientPortionSet(IngredientPortion... ingredientPortions) {
        return Arrays.stream(ingredientPortions)
                .collect(Collectors.toSet());
    }

}

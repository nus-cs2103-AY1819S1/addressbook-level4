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

import seedu.souschef.model.planner.Breakfast;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.planner.Dinner;
import seedu.souschef.model.planner.Lunch;
import seedu.souschef.model.planner.Meal;
import seedu.souschef.model.recipe.CookTime;
import seedu.souschef.model.recipe.Difficulty;
import seedu.souschef.model.recipe.Instruction;
import seedu.souschef.model.recipe.Name;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.recipe.Tag;

/**
 * Contains utility methods for populating {@code AppContent} with sample data.
 */
public class SampleDataUtil {
    public static Recipe[] getRecipes() {
        return new Recipe[] {
            new Recipe(new Name("Chicken Rice"), new Difficulty("3"), new CookTime("40M"),
                    getInstructionList(
                            new Instruction("Slice and clean up the inner of the chicken 1.2 kg.",
                                    getIngredientPortionSet(
                                            new IngredientPortion("Chicken", "kg", 1.2))),
                            new Instruction("Boil the chicken in water 700 ml.",
                                    getIngredientPortionSet(
                                            new IngredientPortion("water", "ml", 700.0))),
                            new Instruction("Remove 200 ml of water and put soy sauce 100 ml.",
                                    getIngredientPortionSet(
                                            new IngredientPortion("soy sauce", "ml", 100.0))),
                            new Instruction("Cook for 20 mins.", new CookTime("20M"),
                                    getIngredientPortionSet())
                    ), getTagSet("Asian", "Singapore", "Poultry")),
            new Recipe(new Name("Black pepper Chicken"), new Difficulty("2"), new CookTime("20M"),
                    getInstructionList(
                            new Instruction("Slice and clean up the inner of the chicken 1.2 kg.",
                                    getIngredientPortionSet(
                                            new IngredientPortion("Chicken", "kg", 1.2))),
                            new Instruction("Heat up the pan with old 2 tablespoon.",
                                    getIngredientPortionSet(
                                            new IngredientPortion("oil", "tablespoon", 2.0))),
                            new Instruction("Add oyster sauce 3 tablespoon, black pepper 20 g and water 50 ml.",

                                    getIngredientPortionSet(
                                            new IngredientPortion("oyster sauce", "tablespoon",
                                            3.0))),
                            new Instruction("Stir-fry for 10 mins.", new CookTime("10M"),
                                    getIngredientPortionSet())
                    ),
                    getTagSet("Asian", "Spicy", "Poultry")),
            new Recipe(new Name("Fried Chinese Noodles"), new Difficulty("1"), new CookTime("20M"),
                    getInstructionList(
                            new Instruction("Slice vegetables 70 gram of any kind.",
                                    getIngredientPortionSet(
                                            new IngredientPortion("vegetables", "gram", 70.0))),
                            new Instruction("Add water 200 ml and noodles 300 g into the pan.",
                                    getIngredientPortionSet(
                                            new IngredientPortion("water", "ml", 200.0),
                                            new IngredientPortion("noodles", "gram", 300.0))),
                            new Instruction("Remove the water and put soy sauce 50 ml.",
                                    getIngredientPortionSet(
                                            new IngredientPortion("soy sauce", "ml", 50.0))),
                            new Instruction("Fry for 7 mins.", new CookTime("7M"),
                                    getIngredientPortionSet())
                    ),
                    getTagSet("Asian", "Staple", "Vegetarian")),
            new Recipe(new Name("Claypot Rice"), new Difficulty("3"), new CookTime("50M"),
                    getInstructionList(
                            new Instruction("Slice and clean up the inner of the chicken 1.2 kg.",
                                    getIngredientPortionSet(
                                            new IngredientPortion("Chicken", "kg", 1.2))),
                            new Instruction("Boil the chicken in water 700 ml.",
                                    getIngredientPortionSet(
                                            new IngredientPortion("water", "ml", 700.0))),
                            new Instruction("Remove 200 ml of water and put soy sauce 100 ml.",
                                    getIngredientPortionSet(
                                            new IngredientPortion("soy sauce", "ml", 100.0))),
                            new Instruction("Cook for 20 mins.", new CookTime("20M"),
                                    getIngredientPortionSet())
                    ),
                    getTagSet("Staple", "Poultry")),
            new Recipe(new Name("Roti Prata"), new Difficulty("3"), new CookTime("40M"),
                    getInstructionList(
                            new Instruction("Sift flour, add salt and water to make dough.",
                                    getIngredientPortionSet(new IngredientPortion("flour", "g", 300.0),
                                            new IngredientPortion("ghee", "cup", 0.5),
                                            new IngredientPortion("water", "cup", 0.5))),
                            new Instruction("Knead thoroughly for 5 minutes.",
                                    new CookTime("5M"), getIngredientPortionSet()),
                            new Instruction("Heat one teaspoon of ghee or butter on a metal "
                                    + "griddle or heavy iron pan."),
                            new Instruction("Fry each prata dough until brown on both sides.")
                    ),
                    getTagSet("Staple", "Vegetarian", "Halal")),

            new Recipe(new Name("Cheese Fries"), new Difficulty("2"), new CookTime("40M"),
                getInstructionList(
                    new Instruction("Melt butter on low.",
                        getIngredientPortionSet(new IngredientPortion("butter", "cup", 0.25))),
                    new Instruction("Add milk. Add cheese slowly, while stirring.",
                        getIngredientPortionSet(new IngredientPortion("milk", "cup", 0.75),
                            new IngredientPortion("cheese", "g", 450.0))),
                    new Instruction("Simmer for 20 minutes, stirring often.",
                        new CookTime("20M"), getIngredientPortionSet()),
                    new Instruction("Add salt and cornstarch mix.",
                        getIngredientPortionSet(new IngredientPortion("salt", "teaspoon", 1.0),
                            new IngredientPortion("cornstarch", "teaspoon", 0.5))),
                    new Instruction("Simmer another 10 minutes, stirring often",
                        new CookTime("10M"), getIngredientPortionSet()),
                    new Instruction("Serve on top of fries")
                ),
                getTagSet("Western", "Vegetarian", "Halal")),
            new Recipe(new Name("Chicken Kebab"), new Difficulty("3"), new CookTime("30M"),
                getInstructionList(
                    new Instruction("Heat grill to medium-high. In a bowl, toss the chicken, "
                        + "garlic, thyme, oil and 1/2 tsp salt and pepper each. "
                        + "Thread the chicken on the skewers.",
                        getIngredientPortionSet(new IngredientPortion("chicken", "g", 680.0),
                            new IngredientPortion("garlic", "clove", 2.0),
                            new IngredientPortion("thyme", "tablespoon", 1.0),
                            new IngredientPortion("olive oil", "tablespoon", 1.0),
                            new IngredientPortion("salt", "teaspoon", 0.5),
                            new IngredientPortion("pepper", "teaspoon", 0.5))),
                    new Instruction("Grill the chicken, turning occasionally, "
                        + "until the chicken is cooked through 7 to 10 minutes.", new CookTime("10M"),
                    getIngredientPortionSet())
                ),
                getTagSet("Poultry", "Staple", "Halal")),
            new Recipe(new Name("Egg Fried Rice"), new Difficulty("3"), new CookTime("30M"),
                getInstructionList(
                    new Instruction("Heat the vegetable oil in a wok, add the onion and "
                        + "leek and fry gently until softened but not browned.",
                        getIngredientPortionSet(new IngredientPortion("vegetable oil", "teaspoon", 0.125),
                            new IngredientPortion("onion", "g", 5.0),
                            new IngredientPortion("leek", "g", 150.0))),
                    new Instruction("Add the eggs and whisk until they are beginning to scramble "
                        + "and cook. Add the soy sauce and whisk.",
                        getIngredientPortionSet(new IngredientPortion("eggs", "whole", 2.0),
                            new IngredientPortion("soy sauce", "tablespoon", 2.0))),
                    new Instruction("Add the cooked rice and stir well to coat in the eggs. Stir in "
                        + "the chopped chives, salt and freshly ground black pepper and spoon into a serving dish. "
                        + "Drizzle with soy sauce and serve.",
                        getIngredientPortionSet(new IngredientPortion("rice", "g", 200.0),
                            new IngredientPortion("chives", "tablespoon", 1.0),
                            new IngredientPortion("salt", "tablespoon", 0.5),
                            new IngredientPortion("black pepper", "tablespoon", 0.5)))
                ),
                getTagSet("Asian", "Staple")),

            new Recipe(new Name("Kimchi Jun"), new Difficulty("2"), new CookTime("10M"),
                        getInstructionList(
                                new Instruction("Stir together the kimchi, kimchi juice,"
                                        + " flour, eggs, and green onion in a bowl.",
                                        getIngredientPortionSet(new IngredientPortion("kimchi", "g", 150.0),
                                                new IngredientPortion("flour", "g", 300.0),
                                                new IngredientPortion("egg", "g", 100.0),
                                                new IngredientPortion("green onion", "g", 100.0))),
                                new Instruction("Using about 1/4 cup of batter for each pancake,"
                                        + " pour into skillet, spreading"
                                        + " as thin as possible. Cook pancakes until set and lightly browned, turning"
                                        + " once, 3 to 5 minutes per side.",
                                        new CookTime("5M"),
                                        getIngredientPortionSet())),
                        getTagSet("Snack", "Korean")),
            new Recipe(new Name("Kimchi Soup"), new Difficulty("2"), new CookTime("30M"),
                        getInstructionList(
                                new Instruction("Stir water, kimchi, pork shoulder, hot pepper paste,"
                                        + " sugar, and salt together in a pot.",
                                        getIngredientPortionSet(new IngredientPortion("kimchi", "g", 50.0),
                                                new IngredientPortion("pork shoulder", "g", 50.0),
                                                new IngredientPortion("hot pepper paste", "tablespoon", 2.0))),
                                new Instruction("bring to a boil, reduce heat to medium,"
                                        + " and simmer until meat is tender, about 20 minutes.",
                                        new CookTime("20M"),
                                        getIngredientPortionSet()),
                                new Instruction("Remove from heat and stir in green onions.",
                                        getIngredientPortionSet(new IngredientPortion("green onion", "g", 20.0)))),
                        getTagSet("Staple", "Korean")),
            new Recipe(new Name("Vegan Korean Kimchi Fried Rice"), new Difficulty("3"), new CookTime("20M"),
                    getInstructionList(
                            new Instruction("Add red onion, garlic, and ginger. Cook, stirring occasionally,"
                                    + "until onion softens, about 3 minutes.",
                                    new CookTime("3M"),
                                    getIngredientPortionSet(new IngredientPortion("red onion", "g", 20.0),
                                            new IngredientPortion("garlic", "g", 10.0),
                                            new IngredientPortion("ginger", "g", 10.0))),
                            new Instruction("Stir in cooked rice, soy sauce, sugar, kimchi,"
                                    + " Cook and stir until heated through, about 5 minutes",
                                    new CookTime("5M"),
                                    getIngredientPortionSet(
                                            new IngredientPortion("soy sauce", "tablespoon", 1.0),
                                            new IngredientPortion("sugar", "tablespoon", 1.0),
                                            new IngredientPortion("kimchi", "g", 20.0))),
                            new Instruction("scrape the bottom of the skillet to prevent sticking.",
                                    getIngredientPortionSet())),
                    getTagSet("Staple", "Vegan", "Korean")),

            new Recipe(new Name("American Breakfast"), new Difficulty("2"), new CookTime("15M"),
                    getInstructionList(
                            new Instruction("Crack 2 whole eggs and beat the eggs with 3/4 cup milk, a"
                                    + "pinch of salt and black pepper",
                                    getIngredientPortionSet(
                                            new IngredientPortion("egg", "whole", 2.0),
                                            new IngredientPortion("milk", "cup", 0.75),
                                            new IngredientPortion("salt", "pinch", 1.0),
                                            new IngredientPortion("blackPepper", "pinch", 1.0))),
                            new Instruction("Put 2 sausages and 2 pieces of bread into a toaster for "
                                    + "15mins",
                                    new CookTime("15M"),
                                    getIngredientPortionSet(
                                            new IngredientPortion("sausage", "whole", 2.0),
                                            new IngredientPortion("bread", "whole", 2.0)
                                    )),
                            new Instruction("Heat one teaspoon butter on a pan.",
                                    getIngredientPortionSet(
                                            new IngredientPortion("butter", "teaspoon", 1.0))),
                            new Instruction("Pour the mixture into the pan and heat on slow fire.",
                                    new CookTime("10M"), getIngredientPortionSet()),
                            new Instruction("Serve and enjoy!")
                    ),
                    getTagSet("Western", "American")),
            new Recipe(new Name("HK Style French Toast"), new Difficulty("2"), new CookTime("15M"),
                    getInstructionList(
                            new Instruction("Crack 2 whole eggs and beat the eggs with 3/4 cup milk, a"
                                    + "teaspoon of sugar",
                                    getIngredientPortionSet(
                                            new IngredientPortion("egg", "whole", 2.0),
                                            new IngredientPortion("milk", "cup", 0.75),
                                            new IngredientPortion("sugar", "teaspoon", 1.0)
                                    )),
                            new Instruction("Spread 1 tablespoon of peanut butter onto 2 slices of bread",
                                    getIngredientPortionSet(
                                            new IngredientPortion("bread", "whole", 2.0),
                                            new IngredientPortion("peanutButter", "tablespoon", 1.0)
                                    )),
                            new Instruction("Heat one teaspoon butter on a pan.",
                                    getIngredientPortionSet(
                                            new IngredientPortion("butter", "teaspoon", 1.0)
                                    )),
                            new Instruction("Batter the sandwich into the mixture and cook on high heat",
                                    new CookTime("10M"), getIngredientPortionSet()
                            ),
                            new Instruction("Serve dish, topping with 1 tablespoon of condense milk and "
                                    + "1 teaspoon of butter", getIngredientPortionSet(
                                            new IngredientPortion("butter", "teaspoon", 1.0),
                                    new IngredientPortion("condenseMilk", "tablespoon", 1.0)
                            )
                            )),
                    getTagSet("Asian", "HK")),
            new Recipe(new Name("Beef steak with butter rice"), new Difficulty("4"), new CookTime("30M"),
                    getInstructionList(
                            new Instruction("Leave beef exposed to room temperature for 15M",
                                    new CookTime("15M"), getIngredientPortionSet(
                                            new IngredientPortion("beef", "piece", 1.0)
                            )),
                            new Instruction("Season beef lightly with pinch of salt and pepper",
                                    getIngredientPortionSet(
                                            new IngredientPortion("salt", "pinch", 1.0),
                                            new IngredientPortion("blackPepper", "pinch", 1.0))),
                            new Instruction("Heat one teaspoon butter on a pan.",
                                    getIngredientPortionSet(
                                            new IngredientPortion("butter", "teaspoon", 1.0)
                                    )),
                            new Instruction("Sear the beef on medium heat, starting from the fattier parts"
                                    + ", then cook until browned.",
                                    new CookTime("10M"), getIngredientPortionSet()),
                            new Instruction("Lightly glaze wok with 1 teaspoon of olive oil",
                                    getIngredientPortionSet(
                                            new IngredientPortion("oliveOil", "teaspoon", 1.0)
                                    )),
                            new Instruction("Fry 1 clove of garlic in wok until golden brown",
                                    new CookTime("5M"),
                                    getIngredientPortionSet(
                                            new IngredientPortion("garlic", "clove", 1.0))),
                            new Instruction("Ease in 1 cup of leftover rice and 2 tablespoon of butter",
                                    new CookTime("10M"),
                                    getIngredientPortionSet(
                                            new IngredientPortion("butter", "tablespoon", 2.0),
                                            new IngredientPortion("rice", "cup", 1.0)
                                    )),
                            new Instruction("Stir fry until everything is evenly mixed",
                                    new CookTime("5M"), getIngredientPortionSet()),
                            new Instruction("Plate beef and rice and serve!")
                    ),
                    getTagSet("Western", "American")),
            new Recipe(new Name("Hot Ham and Cheese Sandwich"), new Difficulty("1"), new CookTime("15M"),
                getInstructionList(
                    new Instruction("Preheat a skillet over medium-high heat."),
                    new Instruction("Spread one side of each slice of bread with 1 teaspoon butter.",
                        getIngredientPortionSet(new IngredientPortion("bread", "whole", 2.0),
                            new IngredientPortion("butter", "teaspoon", 1.0))),
                    new Instruction("Place one slice, butter-side down in the hot skillet. "
                        + "Top with Swiss cheese and ham.",
                        getIngredientPortionSet(new IngredientPortion("swiss cheese", "whole", 2.0),
                            new IngredientPortion("ham", "whole", 2.0))),
                    new Instruction("Spread the unbuttered side of the second slice of bread with mayonnaise "
                        + "and mustard; place it, butter-side up on top of the sandwich. ",
                        getIngredientPortionSet(new IngredientPortion("mayonnaise", "teaspoon", 1.0),
                            new IngredientPortion("mustard", "teaspoon", 1.0))),
                    new Instruction("Cook until the sandwich is golden brown and the cheese is melted, about "
                        + "3 minutes per side.", new CookTime("6M"), getIngredientPortionSet())
                ),
                getTagSet("Light")),
            new Recipe(new Name("Mac and Cheese"), new Difficulty("2"), new CookTime("30M"),
                getInstructionList(
                    new Instruction("In a pot, bring the milk to a boil.",
                        getIngredientPortionSet(new IngredientPortion("milk", "l", 0.4))),
                    new Instruction("Add the pasta and stir constantly until the pasta is cooked, about 10 minutes.",
                        getIngredientPortionSet(new IngredientPortion("macaroni", "g", 150.0))),
                    new Instruction("Turn off the heat, then add the cheddar. Stir until the cheese is melted"
                        + " and the pasta is evenly coated.",
                        getIngredientPortionSet(new IngredientPortion("cheddar cheese", "cup", 2.0)))
                ),
                getTagSet("Light")),
            new Recipe(new Name("French Omelette"), new Difficulty("1"), new CookTime("10M"),
                getInstructionList(
                    new Instruction("Beat eggs, water, salt and pepper in small bowl until blended.",
                        getIngredientPortionSet(new IngredientPortion("egg", "whole", 2.0),
                            new IngredientPortion("water", "tablespoon", 2.0),
                            new IngredientPortion("salt", "tablespoon", 0.125),
                            new IngredientPortion("pepper", "pinch", 1.0))),
                    new Instruction("Heat butter in 6 to 8-inch nonstick omelet pan or skillet over medium-high "
                        + "heat until hot. TILT pan to coat bottom. Pour in egg mixture. Mixture should set "
                        + "immediately at edges."),
                    new Instruction("Gently push cooked portions from edges toward the center with inverted turner "
                        + "so that uncooked eggs can reach the hot pan surface. Continue cooking, tilting pan and "
                        + "gently moving cooked portions as needed."),
                    new Instruction("When top surface of eggs is thickened and no visible liquid egg remains,"
                        + " Place filling on one side of the omelet. Fold omelet in half with turner. With a quick"
                        + " flip of the wrist, turn pan and invert or slide omelet onto plate. Serve immediately.")
                ),
                getTagSet("Light", "French")),
            new Recipe(new Name("Basil Pork Rice"), new Difficulty("3"), new CookTime("10M"),
                getInstructionList(
                    new Instruction("In a hot pan, add the oil and stir fry garlic and chilli until fragrant.",
                        getIngredientPortionSet(new IngredientPortion("oil", "tablespoon", 2.0),
                            new IngredientPortion("garlic", "clove", 2.0),
                            new IngredientPortion("chilli", "tablespoon", 2.0))),
                    new Instruction("Add pork and continue to stir fry on medium heat until almost ready.",
                        getIngredientPortionSet(new IngredientPortion("pork", "g", 200.0))),
                    new Instruction("Keep stir frying and add pork stock as needed."),
                    new Instruction("Add oyster sauce, fish sauce, sugar.",
                        getIngredientPortionSet(new IngredientPortion("oyster sauce", "tablespoon", 1.0),
                            new IngredientPortion("fish sauce", "tablespoon", 1.5),
                            new IngredientPortion("sugar", "teaspoon", 1.0))),
                    new Instruction("Increase heat to high and add fresh thai basil. Keep stir frying for few seconds.",
                        getIngredientPortionSet(new IngredientPortion("thai basil", "cup", 0.5))),
                    new Instruction("Serve with white basmati rice and fried eggs.")
                ),
                getTagSet("Thai", "Staple", "Spicy")),
            new Recipe(new Name("Creamy Garlic Penne Pasta"), new Difficulty("3"), new CookTime("15M"),
                getInstructionList(
                    new Instruction("Melt butter and add garlic in a medium sauce pan.",
                        getIngredientPortionSet(new IngredientPortion("butter", "tablespoon", 2.0),
                            new IngredientPortion("garlic", "clove", 2.0))),
                    new Instruction("Cook over medium for 1 minute.", new CookTime("1M"),
                        getIngredientPortionSet()),
                    new Instruction("Add flour and cook 1 minute, stirring constantly.", new CookTime("1M"),
                        getIngredientPortionSet(new IngredientPortion("flour", "tablespoon", 2.0))),
                    new Instruction("Stir in broth and milk and cook, stirring frequently, until "
                        + "sauce boils and thickens.",
                        getIngredientPortionSet(new IngredientPortion("chicken broth", "cup", 0.75),
                            new IngredientPortion("milk", "cup", 0.75))),
                    new Instruction("Add parsley, salt, pepper and cheese.",
                        getIngredientPortionSet(new IngredientPortion("parsley", "teaspoon", 2.0),
                            new IngredientPortion("salt", "pinch", 1.0),
                            new IngredientPortion("pepper", "pinch", 1.0),
                            new IngredientPortion("cheese", "cup", 0.33))),
                    new Instruction("Stir until cheese is melted."),
                    new Instruction("Toss hot pasta with sauce and serve immediately.",
                        getIngredientPortionSet(new IngredientPortion("penne", "g", 150.0)))
                ),
                getTagSet("Italian")),
            new Recipe(new Name("Baked Salmon Fillets Dijon"), new Difficulty("2"), new CookTime("25M"),
                getInstructionList(
                    new Instruction("Preheat oven to 400 degrees F (200 degrees C). "
                        + "Line a shallow baking pan with aluminum foil."),
                    new Instruction("Place salmon skin-side down on foil. Spread a thin layer of mustard on "
                        + "the top of each fillet, and season with salt and pepper.",
                        getIngredientPortionSet(new IngredientPortion("salmon", "g", 450.0),
                            new IngredientPortion("Dijon mustard", "tablespoon", 3.0),
                            new IngredientPortion("salt", "pinch", 1.0),
                            new IngredientPortion("pepper", "pinch", 1.0))),
                    new Instruction("Top with bread crumbs, then drizzle with melted butter.",
                        getIngredientPortionSet(new IngredientPortion("bread crumbs", "cup", 0.25),
                            new IngredientPortion("butter", "cup", 0.25))),
                    new Instruction("Bake in a preheated oven for 15 minutes, or until salmon flakes "
                        + "easily with a fork.", new CookTime("15M"), getIngredientPortionSet())
                ),
                getTagSet("Light", "Fish"))
        };
    }

    public static Ingredient[] getSampleIngredient() {

        Ingredient [] ingredients = new Ingredient[]{};
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("MM-dd-yyyy");
        sdf.setLenient(false);
        try {
            ingredients = new Ingredient[]{
                new Ingredient(new IngredientName("carrot"), new IngredientAmount(300.0),
                            new IngredientServingUnit("gram"),
                        new IngredientDate(sdf.parse("10-10-2018"))),
                new Ingredient(new IngredientName("tomato"), new IngredientAmount(200.0),
                            new IngredientServingUnit("gram"),
                        new IngredientDate(sdf.parse("10-11-2018"))),
                new Ingredient(new IngredientName("potato"), new IngredientAmount(100.0),
                            new IngredientServingUnit("gram"),
                        new IngredientDate(sdf.parse("10-12-2018"))),
                new Ingredient(new IngredientName("flour"), new IngredientAmount(300.0),
                        new IngredientServingUnit("gram"),
                        new IngredientDate(sdf.parse("10-16-2018"))),
                new Ingredient(new IngredientName("chicken"), new IngredientAmount(540.0),
                        new IngredientServingUnit("gram"),
                        new IngredientDate(sdf.parse("10-21-2018"))),
                new Ingredient(new IngredientName("beef"), new IngredientAmount(300.0),
                        new IngredientServingUnit("gram"),
                        new IngredientDate(sdf.parse("10-25-2018"))),
                new Ingredient(new IngredientName("potato"), new IngredientAmount(100.0),
                        new IngredientServingUnit("gram"),
                        new IngredientDate(sdf.parse("10-30-2018"))),
                new Ingredient(new IngredientName("apple"), new IngredientAmount(120.0),
                        new IngredientServingUnit("gram"),
                        new IngredientDate(sdf.parse("10-30-2018"))),
                new Ingredient(new IngredientName("kimchi"), new IngredientAmount(200.0),
                        new IngredientServingUnit("gram"),
                        new IngredientDate(sdf.parse("11-17-2018")))
            };
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ingredients;

    }

    public static Recipe[] getFavourites() {
        Recipe[] recipes = new Recipe[3];
        recipes[0] = getRecipes()[1];
        recipes[1] = getRecipes()[2];
        recipes[2] = getRecipes()[3];
        return recipes;
    }

    public static HealthPlan[] getSampleHealthPlan() {
        return new HealthPlan[] {
            new HealthPlan(new HealthPlanName("Lose weight"), new TargetWeight("70"), new CurrentWeight("80"),
                  new CurrentHeight("160"), new Age("25"), new Duration("10"), Scheme.LOSS, new ArrayList<Day>()),
            new HealthPlan(new HealthPlanName("gain weight"), new TargetWeight("60"), new CurrentWeight("50"),
                  new CurrentHeight("160"), new Age("27"), new Duration("10"), Scheme.GAIN, new ArrayList<Day>()),
            new HealthPlan(new HealthPlanName("maintain weight"), new TargetWeight("60"), new CurrentWeight("60"),
                  new CurrentHeight("150"), new Age("23"), new Duration("8"), Scheme.MAINTAIN, new ArrayList<Day>())
        };
    }

    public static Day[] getDay() {

        LocalDate modelDate1 = LocalDate.parse("2018-11-28");
        LocalDate modelDate2 = LocalDate.parse("2018-11-29");
        LocalDate modelDate3 = LocalDate.parse("2018-11-30");
        LocalDate modelDate4 = LocalDate.parse("2018-12-01");
        LocalDate modelDate5 = LocalDate.parse("2018-12-02");
        LocalDate modelDate6 = LocalDate.parse("2018-12-03");
        LocalDate modelDate7 = LocalDate.parse("2018-12-04");
        LocalDate modelDate8 = LocalDate.parse("2018-12-05");
        LocalDate modelDate9 = LocalDate.parse("2018-12-06");
        LocalDate modelDate10 = LocalDate.parse("2018-12-07");
        LocalDate modelDate11 = LocalDate.parse("2018-12-08");
        LocalDate modelDate12 = LocalDate.parse("2018-12-09");
        LocalDate modelDate13 = LocalDate.parse("2018-12-10");
        LocalDate modelDate14 = LocalDate.parse("2018-12-11");
        LocalDate modelDate15 = LocalDate.parse("2018-12-12");
        LocalDate modelDate16 = LocalDate.parse("2018-12-13");
        LocalDate modelDate17 = LocalDate.parse("2018-12-14");
        LocalDate modelDate18 = LocalDate.parse("2018-12-15");
        LocalDate modelDate19 = LocalDate.parse("2018-12-16");
        LocalDate modelDate20 = LocalDate.parse("2018-12-17");

        ArrayList<Meal> list1 = new ArrayList<>();
        ArrayList<Meal> list2 = new ArrayList<>();
        ArrayList<Meal> list3 = new ArrayList<>();
        ArrayList<Meal> list4 = new ArrayList<>();
        ArrayList<Meal> list5 = new ArrayList<>();
        ArrayList<Meal> list6 = new ArrayList<>();
        ArrayList<Meal> list7 = new ArrayList<>();
        ArrayList<Meal> list8 = new ArrayList<>();
        ArrayList<Meal> list9 = new ArrayList<>();
        ArrayList<Meal> list10 = new ArrayList<>();
        ArrayList<Meal> list11 = new ArrayList<>();
        ArrayList<Meal> list12 = new ArrayList<>();
        ArrayList<Meal> list13 = new ArrayList<>();
        ArrayList<Meal> list14 = new ArrayList<>();
        ArrayList<Meal> list15 = new ArrayList<>();
        ArrayList<Meal> list16 = new ArrayList<>();
        ArrayList<Meal> list17 = new ArrayList<>();
        ArrayList<Meal> list18 = new ArrayList<>();
        ArrayList<Meal> list19 = new ArrayList<>();
        ArrayList<Meal> list20 = new ArrayList<>();

        List<ArrayList<Meal>> listOfLists = new ArrayList<>();
        listOfLists.add(list1);
        listOfLists.add(list2);
        listOfLists.add(list3);
        listOfLists.add(list4);
        listOfLists.add(list5);
        listOfLists.add(list6);
        listOfLists.add(list7);
        listOfLists.add(list8);
        listOfLists.add(list9);
        listOfLists.add(list10);
        listOfLists.add(list11);
        listOfLists.add(list12);
        listOfLists.add(list13);
        listOfLists.add(list14);
        listOfLists.add(list15);
        listOfLists.add(list16);
        listOfLists.add(list17);
        listOfLists.add(list18);
        listOfLists.add(list19);
        listOfLists.add(list20);

        int index = 0;
        int numRecipes = getRecipes().length;
        for (ArrayList<Meal> l : listOfLists) {
            l.add(new Breakfast(getRecipes()[index]));
            index = (index + 1) % numRecipes;
            l.add(new Lunch(getRecipes()[index]));
            index = (index + 1) % numRecipes;
            l.add(new Dinner(getRecipes()[index]));
            index = (index + 1) % numRecipes;
        }

        return new Day[] {
            new Day (modelDate1, list1),
            new Day (modelDate2, list2),
            new Day (modelDate3, list3),
            new Day (modelDate4, list4),
            new Day (modelDate5, list5),
            new Day (modelDate6, list6),
            new Day (modelDate7, list7),
            new Day (modelDate8, list8),
            new Day (modelDate9, list9),
            new Day (modelDate10, list10),
            new Day (modelDate11, list11),
            new Day (modelDate12, list12),
            new Day (modelDate13, list13),
            new Day (modelDate14, list14),
            new Day (modelDate15, list15),
            new Day (modelDate16, list16),
            new Day (modelDate17, list17),
            new Day (modelDate18, list18),
            new Day (modelDate19, list19),
            new Day (modelDate20, list20)
        };
    }

    public static ReadOnlyAppContent getSampleRecipes() {
        AppContent sampleAb = new AppContent();
        for (Recipe sampleRecipe : getRecipes()) {
            sampleAb.getRecipes().add(sampleRecipe);
        }
        return sampleAb;
    }

    public static ReadOnlyAppContent getSampleFavourites() {
        AppContent sampleAb = new AppContent();
        for (Recipe sampleRecipe : getFavourites()) {
            sampleAb.getFavourites().add(sampleRecipe);
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

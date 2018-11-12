package seedu.restaurant.model.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.restaurant.model.ReadOnlyRestaurantBook;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.account.Name;
import seedu.restaurant.model.account.Password;
import seedu.restaurant.model.account.Username;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.ingredient.IngredientPrice;
import seedu.restaurant.model.ingredient.IngredientUnit;
import seedu.restaurant.model.ingredient.MinimumUnit;
import seedu.restaurant.model.ingredient.NumUnits;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.Price;
import seedu.restaurant.model.menu.Recipe;
import seedu.restaurant.model.reservation.Pax;
import seedu.restaurant.model.reservation.Remark;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.reservation.Time;
import seedu.restaurant.model.sales.ItemName;
import seedu.restaurant.model.sales.QuantitySold;
import seedu.restaurant.model.sales.SalesRecord;
import seedu.restaurant.model.tag.Tag;

/**
 * Contains utility methods for populating {@code RestaurantBook} with sample data.
 */
public class SampleDataUtil {

    //@@author AZhiKai
    public static Account[] getSampleAccounts() {
        return new Account[]{
                new Account(new Username("root"), new Password("1122qq"), new Name("Administrator")),
        };
    }

    //@@author rebstan97
    private static Ingredient[] getSampleIngredients() {
        return new Ingredient[]{
                new Ingredient(new IngredientName("Apple"), new IngredientUnit("carton"),
                        new IngredientPrice("2.00"), new MinimumUnit(10), new NumUnits(98)),
                new Ingredient(new IngredientName("Beef"), new IngredientUnit("pounds"),
                        new IngredientPrice("46.00"), new MinimumUnit(50), new NumUnits(20)),
                new Ingredient(new IngredientName("Bread"), new IngredientUnit("loaf"),
                        new IngredientPrice("2.00"), new MinimumUnit(45), new NumUnits(10)),
                new Ingredient(new IngredientName("Sugar"), new IngredientUnit("packet"),
                        new IngredientPrice("3.20"), new MinimumUnit(20), new NumUnits(22)),
                new Ingredient(new IngredientName("Chicken"), new IngredientUnit("kg"),
                        new IngredientPrice("16.00"), new MinimumUnit(90), new NumUnits(123)),
                new Ingredient(new IngredientName("Bacon"), new IngredientUnit("kg"),
                        new IngredientPrice("14.00"), new MinimumUnit(50), new NumUnits(34)),
                new Ingredient(new IngredientName("Cheese"), new IngredientUnit("kg"),
                        new IngredientPrice("20.00"), new MinimumUnit(70), new NumUnits(0)),
                new Ingredient(new IngredientName("Flour"), new IngredientUnit("packet"),
                        new IngredientPrice("4.20"), new MinimumUnit(10), new NumUnits(0)),
                new Ingredient(new IngredientName("Salt"), new IngredientUnit("packet"),
                        new IngredientPrice("2.00"), new MinimumUnit(5), new NumUnits(10)),
                new Ingredient(new IngredientName("Pasta"), new IngredientUnit("packet"),
                        new IngredientPrice("2.20"), new MinimumUnit(80), new NumUnits(33))
        };
    }

    //@@author hyperionnkJ
    private static SalesRecord[] getSampleSalesRecords() {
        return new SalesRecord[] {
                new SalesRecord(new seedu.restaurant.model.sales.Date("14-11-2018"), new ItemName("Apple Juice"),
                        new QuantitySold("31"), new seedu.restaurant.model.sales.Price("2"))
                        .setIngredientsUsed(getRequiredIngredients(Map.of("Apple", "93"))),
                new SalesRecord(new seedu.restaurant.model.sales.Date("14-11-2018"), new ItemName("Beef Burger"),
                        new QuantitySold("23"), new seedu.restaurant.model.sales.Price("3"))
                        .setIngredientsUsed(getRequiredIngredients(Map.of("Beef", "23", "Bread", "46"))),
                new SalesRecord(new seedu.restaurant.model.sales.Date("14-11-2018"), new ItemName("Cheese Cake"),
                        new QuantitySold("33"), new seedu.restaurant.model.sales.Price("2.50"))
                        .setIngredientsUsed(getRequiredIngredients(Map.of("Cheese", "99"))),
                new SalesRecord(new seedu.restaurant.model.sales.Date("15-11-2018"), new ItemName("Apple Juice"),
                        new QuantitySold("16"), new seedu.restaurant.model.sales.Price("2"))
                        .setIngredientsUsed(getRequiredIngredients(Map.of("Apple", "48"))),
                new SalesRecord(new seedu.restaurant.model.sales.Date("15-11-2018"), new ItemName("Beef Burger"),
                        new QuantitySold("20"), new seedu.restaurant.model.sales.Price("3"))
                        .setIngredientsUsed(getRequiredIngredients(Map.of("Beef", "20", "Bread", "40"))),
                new SalesRecord(new seedu.restaurant.model.sales.Date("15-11-2018"), new ItemName("Fruit Cake"),
                        new QuantitySold("12"), new seedu.restaurant.model.sales.Price("2.50")),
                new SalesRecord(new seedu.restaurant.model.sales.Date("16-11-2018"), new ItemName("Apple Juice"),
                        new QuantitySold("45"), new seedu.restaurant.model.sales.Price("2"))
                        .setIngredientsUsed(getRequiredIngredients(Map.of("Apple", "135"))),
                new SalesRecord(new seedu.restaurant.model.sales.Date("16-11-2018"), new ItemName("Beef Burger"),
                        new QuantitySold("33"), new seedu.restaurant.model.sales.Price("3"))
                        .setIngredientsUsed(getRequiredIngredients(Map.of("Beef", "33", "Bread", "66"))),
                new SalesRecord(new seedu.restaurant.model.sales.Date("16-11-2018"), new ItemName("Cheese Cake"),
                        new QuantitySold("39"), new seedu.restaurant.model.sales.Price("2.50"))
                        .setIngredientsUsed(getRequiredIngredients(Map.of("Cheese", "117")))
        };
    }

    //@@author yican95
    private static Item[] getSampleItems() {
        return new Item[]{
                new Item(new seedu.restaurant.model.menu.Name("Apple Juice"), new Price("2"),
                        new Recipe("some recipe for apple juice"),
                        getTagSet("drinks", "friday"), getRequiredIngredients(Map.of("Apple", "3"))),
                new Item(new seedu.restaurant.model.menu.Name("Beef Burger"), new Price("3"), new Recipe(""),
                        getTagSet("burger", "monday"), getRequiredIngredients(Map.of("Beef", "1", "Bread", "2"))),
                new Item(new seedu.restaurant.model.menu.Name("Cheese Cake"), new Price("2.50"), new Recipe(""),
                        getTagSet("cake", "tuesday"), getRequiredIngredients(Map.of("Cheese", "3"))),
                new Item(new seedu.restaurant.model.menu.Name("Fruit Cake"), new Price("2.50"), new Recipe(""),
                        getTagSet("cake", "thursday"), new HashMap<>()),
                new Item(new seedu.restaurant.model.menu.Name("Bacon Carbonara"), new Price("12"), new Recipe(""),
                        getTagSet("bacon", "friday"), new HashMap<>())
        };
    }

    /**
     * Returns a requiredIngredients map containing the map of strings given.
     */
    public static Map<IngredientName, Integer> getRequiredIngredients(Map<String, String> stringMap) {
        Map<IngredientName, Integer> requiredIngredients = new HashMap<>();
        for (Map.Entry<String, String> entry : stringMap.entrySet()) {
            requiredIngredients.put(new IngredientName(entry.getKey()), Integer.parseInt(entry.getValue()));
        }
        return requiredIngredients;
    }

    //@@author m4dkip
    private static Reservation[] getSampleReservations() {
        return new Reservation[]{
                new Reservation(new seedu.restaurant.model.reservation.Name("Ong Ming Xian"), new Pax("4"),
                        new seedu.restaurant.model.reservation.Date("30-12-2019"), new Time("18:00"),
                        new Remark("Require baby stool"), getTagSet("Dinner")),
                new Reservation(new seedu.restaurant.model.reservation.Name("Lee Yi Can"), new Pax("2"),
                        new seedu.restaurant.model.reservation.Date("31-12-2019"), new Time("12:00"),
                        new Remark("Request for sofa seats"), getTagSet("Lunch")),
                new Reservation(new seedu.restaurant.model.reservation.Name("Ang Zhi Kai"), new Pax("16"),
                        new seedu.restaurant.model.reservation.Date("02-01-2020"), new Time("13:00"),
                        new Remark("More chili sauce"), getTagSet("Lunch")),
                new Reservation(new seedu.restaurant.model.reservation.Name("Rebecca Tan"), new Pax("2"),
                        new seedu.restaurant.model.reservation.Date("04-01-2020"), new Time("19:00"),
                        new Remark("Extra napkins"), getTagSet("Dinner")),
                new Reservation(new seedu.restaurant.model.reservation.Name("Neo Kai Jun"), new Pax("3"),
                        new seedu.restaurant.model.reservation.Date("05-01-2020"), new Time("10:00"),
                        new Remark("Takeaway only"), getTagSet("Breakfast"))
        };
    }

    //@@author AZhiKai

    /**
     * Returns the default restaurant book, otherwise known as the clean state when the product is delivered where it
     * only contains a root account. This is different from {@link #getSampleRestaurantBook()} which populates data in
     * the storage for testing or demonstration purposes.
     */
    public static ReadOnlyRestaurantBook getDefaultRestaurantBook() {
        RestaurantBook defaultRestaurantBook = new RestaurantBook();
        defaultRestaurantBook.addAccount(getSampleAccounts()[0]);
        return defaultRestaurantBook;
    }

    public static ReadOnlyRestaurantBook getSampleRestaurantBook() {
        RestaurantBook defaultRestaurantBook = new RestaurantBook();
        for (Account sampleAccount : getSampleAccounts()) {
            defaultRestaurantBook.addAccount(sampleAccount);
        }
        for (Ingredient sampleIngredient : getSampleIngredients()) {
            defaultRestaurantBook.addIngredient(sampleIngredient);
        }
        for (Item sampleItem : getSampleItems()) {
            defaultRestaurantBook.addItem(sampleItem);
        }
        for (SalesRecord sampleSalesRecord : getSampleSalesRecords()) {
            defaultRestaurantBook.addRecord(sampleSalesRecord);
        }
        for (Reservation sampleReservation : getSampleReservations()) {
            defaultRestaurantBook.addReservation(sampleReservation);
        }

        return defaultRestaurantBook;
    }

    //@@author
    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}

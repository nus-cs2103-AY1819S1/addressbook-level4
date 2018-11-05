package seedu.restaurant.ui.testutil;

import static org.junit.Assert.assertEquals;
import static seedu.restaurant.ui.sales.RecordStackPanel.MESSAGE_REQUIRED_INGREDIENTS_NOT_FOUND;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.accounts.AccountCardHandle;
import guitests.guihandles.ingredient.IngredientCardHandle;
import guitests.guihandles.menu.IngredientStackPanelHandle;
import guitests.guihandles.menu.ItemCardHandle;
import guitests.guihandles.menu.ItemStackPanelHandle;
import guitests.guihandles.reservation.ReservationCardHandle;
import guitests.guihandles.sales.RecordCardHandle;
import guitests.guihandles.sales.RecordStackPanelHandle;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.ingredient.IngredientName;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.person.Person;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.salesrecord.SalesRecord;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {

    private static final String LABEL_DEFAULT_STYLE = "label";

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());

        expectedCard.getTags().forEach(tag ->
                assertEquals(expectedCard.getTagStyleClasses(tag), actualCard.getTagStyleClasses(tag)));
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().toString(), actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertTagsEqual(expectedPerson, actualCard);
        assertEquals(expectedPerson.getRemark().value, actualCard.getRemark());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedIngredient}.
     */
    public static void assertCardDisplaysIngredient(Ingredient expectedIngredient, IngredientCardHandle actualCard) {
        assertEquals(expectedIngredient.getName().toString(), actualCard.getName());
        String expectedPrice = "$" + expectedIngredient.getPrice().toString()
                + " / " + expectedIngredient.getUnit().toString();
        assertEquals(expectedPrice, actualCard.getPrice());
        String expectedNumUnits = expectedIngredient.getNumUnits().toString() + " "
                + expectedIngredient.getUnit().toString() + "(s) available";
        assertEquals(expectedNumUnits, actualCard.getNumUnits());
    }

    /**
     * Asserts that {@code actualStackPanel} displays the details of {@code expectedItem}.
     */
    public static void assertStackPanelDisplaysIngredient(Ingredient expectedIngredient,
            IngredientStackPanelHandle actualStackPanel) {
        assertEquals(expectedIngredient.getName().toString(), actualStackPanel.getName());
        assertEquals("Unit: " + expectedIngredient.getUnit(), actualStackPanel.getUnit());
        assertEquals("Price per unit: $" + expectedIngredient.getPrice().toString(),
                actualStackPanel.getPrice());
        assertEquals("Minimum stock: " + expectedIngredient.getMinimum().toString(),
                actualStackPanel.getMinimum());
        assertEquals("Number of units available: " + expectedIngredient.getNumUnits().toString(),
                actualStackPanel.getNumUnits());

    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertRecordCardEquals(RecordCardHandle expectedCard, RecordCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getDate(), actualCard.getDate());
        assertEquals(expectedCard.getItemName(), actualCard.getItemName());
        assertEquals(expectedCard.getQuantitySold(), actualCard.getQuantitySold());
        assertEquals(expectedCard.getPrice(), actualCard.getPrice());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedRecord}.
     */
    public static void assertCardDisplaysRecord(SalesRecord expectedRecord, RecordCardHandle actualCard) {
        assertEquals(expectedRecord.getDate().toString(), actualCard.getDate());
        assertEquals(expectedRecord.getName().toString(), actualCard.getItemName());
        assertEquals("Quantity Sold: " + expectedRecord.getQuantitySold().toString(), actualCard.getQuantitySold());
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        assertEquals("Item Price: "
                + currencyFormatter.format(expectedRecord.getPrice().getValue()), actualCard.getPrice());
    }

    /**
     * Asserts that {@code actualStackPanel} displays the details of {@code expectedRecord}.
     */
    public static void assertStackPanelDisplaysRecord(SalesRecord expectedRecord,
            RecordStackPanelHandle actualStackPanel) {
        assertEquals(expectedRecord.getDate().toString() + " (" + expectedRecord.getDate().getDayOfWeek() + ")",
                actualStackPanel.getDate());
        assertEquals(expectedRecord.getName().toString(), actualStackPanel.getItemName());
        assertEquals(expectedRecord.getQuantitySold().toString(), actualStackPanel.getQuantitySold());

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        assertEquals(currencyFormatter.format(expectedRecord.getPrice().getValue()), actualStackPanel.getPrice());
        assertEquals(currencyFormatter.format(expectedRecord.getRevenue()), actualStackPanel.getTotalRevenue());
        String expectedIngredientUsed = (expectedRecord.getIngredientsUsed().isEmpty())
                ? MESSAGE_REQUIRED_INGREDIENTS_NOT_FOUND : ingredientUsedToString(expectedRecord.getIngredientsUsed());
        assertEquals(expectedIngredientUsed, actualStackPanel.getIngredientUsed());
    }

    /**
     * Returns the string representation of the given {@code ingredientUsed}
     */
    private static String ingredientUsedToString(Map<IngredientName, Integer> ingredientUsed) {
        StringBuilder stringBuilder = new StringBuilder();
        int index = 1;
        for (Map.Entry<IngredientName, Integer> entry : ingredientUsed.entrySet()) {
            stringBuilder.append(index).append(") ").append(entry.getKey().toString())
                    .append(" - ").append(entry.getValue().toString()).append(" units").append("\n");
            index++;
        }
        return stringBuilder.toString();
    }

    /**
     * Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedPerson} with the correct
     * color.
     */
    private static void assertTagsEqual(Person expectedPerson, PersonCardHandle actualCard) {
        List<String> expectedTags = expectedPerson.getTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getTags());
        expectedTags.forEach(tag ->
                assertEquals(Arrays.asList(LABEL_DEFAULT_STYLE, getTagColorStyleFor(tag)),
                        actualCard.getTagStyleClasses(tag)));
    }

    /**
     * Returns the color style for {@code tagName}'s label. The tag's color is determined by looking up the color in
     * {@code PersonCard#TAG_COLOR_STYLES}, using an index generated by the hash code of the tag's content.
     */
    private static String getTagColorStyleFor(String tagName) {
        switch (tagName) {

        case "classmates":
        case "owesMoney":
            return "teal";

        case "colleagues":
        case "neighbours":
            return "yellow";

        case "family":
        case "friend":
            return "orange";

        case "friends":
            return "brown";

        case "husband":
            return "grey";

        default:
            throw new AssertionError(tagName + " does not have a color assigned.");
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and in
     * the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            personListPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and in
     * the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Person[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }

    // Menu Management

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertItemCardEquals(ItemCardHandle expectedCard, ItemCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPrice(), actualCard.getPrice());
        assertEquals(expectedCard.getTags(), actualCard.getTags());

        expectedCard.getTags().forEach(tag ->
                assertEquals(expectedCard.getTagStyleClasses(tag), actualCard.getTagStyleClasses(tag)));
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedItem}.
     */
    public static void assertCardDisplaysItem(Item expectedItem, ItemCardHandle actualCard) {
        assertEquals(expectedItem.getName().toString(), actualCard.getName());
        assertEquals("$" + expectedItem.getPrice().toString(), actualCard.getPrice());
        assertEquals("Price displayed with " + String.format("%.0f", expectedItem.getPercent())
                + "% discount", actualCard.getPercent());
        assertTagsEqualForItem(expectedItem, actualCard);
        assertEquals(expectedItem.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedItem} with the correct color.
     */
    private static void assertTagsEqualForItem(Item expectedItem, ItemCardHandle actualCard) {
        List<String> expectedTags = expectedItem.getTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getTags());
        expectedTags.forEach(tag ->
                assertEquals(Arrays.asList(LABEL_DEFAULT_STYLE, getTagColorStyleFor(tag)),
                        actualCard.getTagStyleClasses(tag)));
    }

    /**
     * Asserts that the tags in {@code actualStackPanel} matches all the tags in {@code expectedItem} with the correct
     * color.
     */
    private static void assertTagsEqualForItem(Item expectedItem, ItemStackPanelHandle actualStackPanelHandle) {
        List<String> expectedTags = expectedItem.getTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualStackPanelHandle.getTags());
        expectedTags.forEach(tag ->
                assertEquals(Arrays.asList(LABEL_DEFAULT_STYLE, getTagColorStyleFor(tag)),
                        actualStackPanelHandle.getTagStyleClasses(tag)));
    }

    /**
     * Asserts that {@code actualStackPanel} displays the details of {@code expectedItem}.
     */
    public static void assertStackPanelDisplaysItem(Item expectedItem, ItemStackPanelHandle actualStackPanel) {
        assertEquals(expectedItem.getName().toString(), actualStackPanel.getName());
        assertEquals("$" + expectedItem.getPrice().toString(), actualStackPanel.getPrice());
        assertEquals("Price displayed with " + String.format("%.0f", expectedItem.getPercent())
                + "% discount", actualStackPanel.getPercent());
        assertEquals("Recipe: " + expectedItem.getRecipe(), actualStackPanel.getRecipe());
        Map<IngredientName, Integer> map = expectedItem.getRequiredIngredients();
        StringBuilder str = new StringBuilder("Required ingredients:\n");
        for (Map.Entry<IngredientName, Integer> entry : map.entrySet()) {
            str.append("\u2022 " + entry.getValue().toString() + " unit of ");
            str.append(entry.getKey().toString() + "\n");
        }
        assertEquals(str.toString(), actualStackPanel.getRequiredIngredients());
        assertTagsEqualForItem(expectedItem, actualStackPanel);
        assertEquals(expectedItem.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualStackPanel.getTags());
    }

    // Reservation Management

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertReservationCardEquals(ReservationCardHandle expectedCard,
            ReservationCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPax(), actualCard.getPax());
        assertEquals(expectedCard.getDate(), actualCard.getDate());
        assertEquals(expectedCard.getTime(), actualCard.getTime());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
        expectedCard.getTags().forEach(tag ->
                assertEquals(expectedCard.getTagStyleClasses(tag), actualCard.getTagStyleClasses(tag)));
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedReservation}.
     */
    public static void assertCardDisplaysReservation(Reservation expectedReservation,
            ReservationCardHandle actualCard) {
        assertEquals(expectedReservation.getName().toString(), actualCard.getName());
        assertEquals("Pax: " + expectedReservation.getPax().toString(), actualCard.getPax());
        assertEquals("Date: " + expectedReservation.getDate().toString(), actualCard.getDate());
        assertEquals("Time: " + expectedReservation.getTime().toString(), actualCard.getTime());
        assertTagsEqualForReservation(expectedReservation, actualCard);
        assertEquals(expectedReservation.getRemark().value, actualCard.getRemark());
        assertEquals(expectedReservation.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedReservation} with the correct
     * color.
     */
    private static void assertTagsEqualForReservation(Reservation expectedReservation,
            ReservationCardHandle actualCard) {
        List<String> expectedTags = expectedReservation.getTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getTags());
        expectedTags.forEach(tag ->
                assertEquals(Arrays.asList(LABEL_DEFAULT_STYLE, getTagColorStyleFor(tag)),
                        actualCard.getTagStyleClasses(tag)));
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertAccountCardEquals(AccountCardHandle expectedCard, AccountCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getUsername(), actualCard.getUsername());
    }


    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedAccount}.
     */
    public static void assertCardDisplaysAccount(Account expectedAccount, AccountCardHandle actualCard) {
        assertEquals(expectedAccount.getUsername().toString(), actualCard.getUsername());
    }

}

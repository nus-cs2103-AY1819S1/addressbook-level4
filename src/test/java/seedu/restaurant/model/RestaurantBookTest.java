package seedu.restaurant.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_TAG_BURGER;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_TAG_CHEESE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_RESERVATION_PAX_BILLY;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBook;
import static seedu.restaurant.testutil.TypicalRestaurantBook.getTypicalRestaurantBookWithItemOnly;
import static seedu.restaurant.testutil.account.TypicalAccounts.DEMO_ADMIN;
import static seedu.restaurant.testutil.account.TypicalAccounts.DEMO_ONE;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.AVOCADO;
import static seedu.restaurant.testutil.ingredient.TypicalIngredients.BEANS;
import static seedu.restaurant.testutil.menu.TypicalItems.APPLE_JUICE;
import static seedu.restaurant.testutil.menu.TypicalItems.BEEF_BURGER;
import static seedu.restaurant.testutil.menu.TypicalItems.BURGER;
import static seedu.restaurant.testutil.menu.TypicalItems.CHEESE_BURGER;
import static seedu.restaurant.testutil.menu.TypicalItems.FRIES;
import static seedu.restaurant.testutil.reservation.TypicalReservations.ANDREW;
import static seedu.restaurant.testutil.salesrecords.TypicalRecords.RECORD_DEFAULT;
import static seedu.restaurant.testutil.salesrecords.TypicalRecords.RECORD_ONE;
import static seedu.restaurant.testutil.salesrecords.TypicalRecords.RECORD_THREE;
import static seedu.restaurant.testutil.salesrecords.TypicalRecords.RECORD_TWO;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.restaurant.logic.commands.menu.SortMenuCommand.SortMethod;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.account.exceptions.DuplicateAccountException;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.menu.exceptions.DuplicateItemException;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.reservation.exceptions.DuplicateReservationException;
import seedu.restaurant.model.salesrecord.SalesRecord;
import seedu.restaurant.model.salesrecord.exceptions.DuplicateRecordException;
import seedu.restaurant.model.tag.Tag;
import seedu.restaurant.testutil.RestaurantBookBuilder;
import seedu.restaurant.testutil.account.AccountBuilder;
import seedu.restaurant.testutil.menu.ItemBuilder;
import seedu.restaurant.testutil.reservation.ReservationBuilder;
import seedu.restaurant.testutil.salesrecords.RecordBuilder;

public class RestaurantBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RestaurantBook restaurantBook = new RestaurantBook();
    private RestaurantBook restaurantBookWithItems = null;

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), restaurantBook.getItemList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        restaurantBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyRestaurantBook_replacesData() {
        RestaurantBook newData = getTypicalRestaurantBook();
        restaurantBook.resetData(newData);
        assertEquals(newData, restaurantBook);
    }

    @Test
    public void resetData_withDuplicateRecords_throwsDuplicateRecordException() {
        // Two records with the same date and name
        SalesRecord record = new RecordBuilder(RECORD_ONE)
                .withDate(RECORD_DEFAULT.getDate().toString())
                .withName(RECORD_DEFAULT.getName().toString())
                .build();
        List<Account> newAccounts = Arrays.asList(DEMO_ADMIN, DEMO_ONE);
        List<Item> newItems = Arrays.asList(APPLE_JUICE);
        List<Reservation> newReservations = Arrays.asList(ANDREW);
        List<SalesRecord> newRecords = Arrays.asList(RECORD_DEFAULT, record);
        List<Ingredient> newIngredients = Arrays.asList(AVOCADO, BEANS);
        RestaurantBookStub newData = new RestaurantBookStub(newAccounts, newItems, newReservations,
                newRecords, newIngredients);

        thrown.expect(DuplicateRecordException.class);
        restaurantBook.resetData(newData);
    }

    @Test
    public void hasRecord_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        restaurantBook.hasRecord(null);
    }

    @Test
    public void hasRecord_recordNotInRestaurantBook_returnsFalse() {
        assertFalse(restaurantBook.hasRecord(RECORD_DEFAULT));
    }

    @Test
    public void hasRecord_recordInRestaurantBook_returnsTrue() {
        restaurantBook.addRecord(RECORD_DEFAULT);
        assertTrue(restaurantBook.hasRecord(RECORD_DEFAULT));
    }

    @Test
    public void hasRecord_recordWithSameDateDifferentNameInRestaurantBook_returnsTrue() {
        restaurantBook.addRecord(RECORD_DEFAULT);
        SalesRecord record = new RecordBuilder(RECORD_TWO)
                .withDate(RECORD_DEFAULT.getDate().toString())
                .build();
        restaurantBook.addRecord(record);
        assertTrue(restaurantBook.hasRecord(record));
    }

    @Test
    public void hasRecord_recordWithDifferentDateSameNameInRestaurantBook_returnsTrue() {
        restaurantBook.addRecord(RECORD_DEFAULT);
        SalesRecord record = new RecordBuilder(RECORD_ONE)
                .withName(RECORD_DEFAULT.getName().toString())
                .build();
        restaurantBook.addRecord(record);
        assertTrue(restaurantBook.hasRecord(record));
    }

    @Test
    public void hasRecord_recordWithSameQuantitySoldSamePriceInRestaurantBook_returnsTrue() {
        restaurantBook.addRecord(RECORD_DEFAULT);
        SalesRecord record = new RecordBuilder(RECORD_THREE)
                .withQuantitySold(RECORD_DEFAULT.getQuantitySold().toString())
                .withPrice(RECORD_DEFAULT.getPrice().toString())
                .build();
        restaurantBook.addRecord(record);
        assertTrue(restaurantBook.hasRecord(record));
    }

    @Test
    public void getRecordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        restaurantBook.getRecordList().remove(0);
    }

    @Test
    public void getSalesReport_nullDate_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        restaurantBook.getSalesReport(null);
    }

    @Test
    public void resetData_withDuplicateAccounts_throwsDuplicateAccountException() {
        // Two accounts with the same username
        Account account = new AccountBuilder(DEMO_ONE)
                .withUsername(DEMO_ADMIN.getUsername().toString())
                .build();
        List<Account> newAccounts = Arrays.asList(DEMO_ADMIN, account);
        List<Item> newItems = Arrays.asList(APPLE_JUICE);
        List<Reservation> newReservations = Arrays.asList(ANDREW);
        List<SalesRecord> newRecords = Arrays.asList(RECORD_DEFAULT, RECORD_ONE);
        List<Ingredient> newIngredients = Arrays.asList(AVOCADO, BEANS);
        RestaurantBookStub newData = new RestaurantBookStub(newAccounts, newItems, newReservations,
                newRecords, newIngredients);

        thrown.expect(DuplicateAccountException.class);
        restaurantBook.resetData(newData);
    }

    @Test
    public void hasAccount_nullAccount_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        restaurantBook.hasAccount(null);
    }

    @Test
    public void hasAccount_accountNotInRestaurantBook_returnsFalse() {
        assertFalse(restaurantBook.hasAccount(DEMO_ADMIN));
    }

    @Test
    public void hasAccount_accountInRestaurantBook_returnsTrue() {
        restaurantBook.addAccount(DEMO_ADMIN);
        assertTrue(restaurantBook.hasAccount(DEMO_ADMIN));
    }

    @Test
    public void hasAccount_accountWithSamePasswordInRestaurantBook_returnsTrue() {
        // same raw password, but with different username.
        restaurantBook.addAccount(DEMO_ADMIN);
        Account account = new AccountBuilder(DEMO_ONE)
                .withPassword(DEMO_ADMIN.getPassword().toString())
                .build();
        restaurantBook.addAccount(account);
        assertTrue(restaurantBook.hasAccount(account));
    }

    @Test
    public void getAccountList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        restaurantBook.getAccountList().remove(0);
    }

    //@@author yican95
    // Menu Management
    @Test
    public void resetData_withDuplicateItems_throwsDuplicateItemException() {
        // Two items with the same identity fields
        Item editedApple = new ItemBuilder(APPLE_JUICE).withTags(VALID_ITEM_TAG_CHEESE).build();
        List<Account> newAccounts = Arrays.asList(DEMO_ADMIN);
        List<Item> newItems = Arrays.asList(APPLE_JUICE, editedApple);
        List<Reservation> newReservations = Arrays.asList(ANDREW);
        List<SalesRecord> newRecords = Arrays.asList(RECORD_DEFAULT, RECORD_ONE);
        List<Ingredient> newIngredients = Arrays.asList(AVOCADO, BEANS);
        RestaurantBookStub newData = new RestaurantBookStub(newAccounts, newItems, newReservations,
                newRecords, newIngredients);

        thrown.expect(DuplicateItemException.class);
        restaurantBook.resetData(newData);
    }

    @Test
    public void hasItem_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        restaurantBook.hasItem(null);
    }

    @Test
    public void hasItem_itemNotInRestaurantBook_returnsFalse() {
        assertFalse(restaurantBook.hasItem(APPLE_JUICE));
    }

    @Test
    public void hasItem_itemInRestaurantBook_returnsTrue() {
        restaurantBook.addItem(APPLE_JUICE);
        assertTrue(restaurantBook.hasItem(APPLE_JUICE));
    }

    @Test
    public void hasItem_itemWithSameIdentityFieldsInRestaurantBook_returnsTrue() {
        restaurantBook.addItem(APPLE_JUICE);
        Item editedApple = new ItemBuilder(APPLE_JUICE).withTags(VALID_ITEM_TAG_CHEESE).build();
        assertTrue(restaurantBook.hasItem(editedApple));
    }

    @Test
    public void getItemList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        restaurantBook.getItemList().remove(0);
    }

    @Test
    public void removeTagForMenu_noSuchTag_restaurantBookUnmodified() {
        restaurantBookWithItems = new RestaurantBookBuilder().withItem(BEEF_BURGER).withItem(BURGER).build();

        restaurantBookWithItems.removeTagForMenu(new Tag(VALID_ITEM_TAG_CHEESE));

        RestaurantBook expectedRestaurantBook = new RestaurantBookBuilder().withItem(BEEF_BURGER).withItem(BURGER)
                .build();

        assertEquals(restaurantBookWithItems, expectedRestaurantBook);
    }

    @Test
    public void removeTagForMenu_fromAllItems_restaurantBookModified() {
        restaurantBookWithItems = new RestaurantBookBuilder().withItem(CHEESE_BURGER).withItem(FRIES).build();

        restaurantBookWithItems.removeTagForMenu(new Tag(VALID_ITEM_TAG_CHEESE));

        Item cheeseWithoutCheeseTags = new ItemBuilder(CHEESE_BURGER).withTags(VALID_ITEM_TAG_BURGER).build();
        Item friesWithoutTags = new ItemBuilder(FRIES).withTags().build();

        RestaurantBook expectedRestaurantBook = new RestaurantBookBuilder().withItem(cheeseWithoutCheeseTags)
                .withItem(friesWithoutTags)
                .build();

        assertEquals(restaurantBookWithItems, expectedRestaurantBook);
    }

    @Test
    public void removeTagForMenu_fromOneItem_restaurantBookModified() {
        restaurantBookWithItems = new RestaurantBookBuilder().withItem(FRIES).withItem(BURGER).build();

        restaurantBookWithItems.removeTagForMenu(new Tag(VALID_ITEM_TAG_CHEESE));

        Item friesWithoutTags = new ItemBuilder(FRIES).withTags().build();

        RestaurantBook expectedRestaurantBook = new RestaurantBookBuilder().withItem(friesWithoutTags)
                .withItem(BURGER)
                .build();

        assertEquals(restaurantBookWithItems, expectedRestaurantBook);
    }

    // Reservation Management
    @Test
    public void resetData_withDuplicateReservations_throwsDuplicateReservationException() {
        // Two reservations with the same identity fields
        Reservation editedAndrew = new ReservationBuilder(ANDREW)
                .withPax(VALID_RESERVATION_PAX_BILLY)
                .build();
        List<Account> newAccounts = Arrays.asList(DEMO_ADMIN, DEMO_ONE);
        List<Item> newItems = Arrays.asList(APPLE_JUICE);
        List<Reservation> newReservations = Arrays.asList(ANDREW, editedAndrew);
        List<SalesRecord> newRecords = Arrays.asList(RECORD_DEFAULT, RECORD_ONE);
        List<Ingredient> newIngredients = Arrays.asList(AVOCADO, BEANS);
        RestaurantBookStub newData = new RestaurantBookStub(newAccounts, newItems, newReservations,
                newRecords, newIngredients);

        thrown.expect(DuplicateReservationException.class);
        restaurantBook.resetData(newData);
    }

    @Test
    public void hasReservation_nullReservation_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        restaurantBook.hasReservation(null);
    }

    @Test
    public void hasReservation_reservationNotInRestaurantBook_returnsFalse() {
        assertFalse(restaurantBook.hasReservation(ANDREW));
    }

    @Test
    public void hasReservation_reservationInRestaurantBook_returnsTrue() {
        restaurantBook.addReservation(ANDREW);
        assertTrue(restaurantBook.hasReservation(ANDREW));
    }

    @Test
    public void hasReservation_reservationWithSameIdentityFieldsInRestaurantBook_returnsTrue() {
        restaurantBook.addReservation(ANDREW);
        Reservation editedAndrew = new ReservationBuilder(ANDREW)
                .withPax(VALID_RESERVATION_PAX_BILLY)
                .build();
        assertTrue(restaurantBook.hasReservation(editedAndrew));
    }

    @Test
    public void getReservationList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        restaurantBook.getReservationList().remove(0);
    }

    @Test
    public void resetMenuData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        restaurantBook.resetMenuData(null);
    }

    @Test
    public void resetMenuData_withValidReadOnlyRestaurantBook_replacesMenuDataOnly() {
        RestaurantBook newData = getTypicalRestaurantBookWithItemOnly();
        restaurantBook.resetMenuData(newData);
        assertEquals(newData, restaurantBook);
    }

    @Test
    public void sortMenuByName_restaurantBookModified() {
        RestaurantBook sortedByName = new RestaurantBookBuilder().withItem(BEEF_BURGER).withItem(APPLE_JUICE).build();
        sortedByName.sortMenu(SortMethod.NAME);
        restaurantBookWithItems = new RestaurantBookBuilder().withItem(APPLE_JUICE).withItem(BEEF_BURGER).build();
        assertEquals(sortedByName, restaurantBookWithItems);
    }

    @Test
    public void sortMenuByPrice_restaurantBookModified() {
        RestaurantBook sortedByPrice = new RestaurantBookBuilder().withItem(BEEF_BURGER).withItem(APPLE_JUICE).build();
        sortedByPrice.sortMenu(SortMethod.PRICE);
        restaurantBookWithItems = new RestaurantBookBuilder().withItem(APPLE_JUICE).withItem(BEEF_BURGER).build();
        assertEquals(sortedByPrice, restaurantBookWithItems);
    }

    @Test
    public void equals() {
        restaurantBookWithItems = new RestaurantBookBuilder().withItem(BURGER).withItem(FRIES).build();

        // same object
        assertTrue(restaurantBookWithItems.equals(restaurantBookWithItems));
        restaurantBookWithItems.removeTagForMenu(new Tag(VALID_ITEM_TAG_CHEESE));

        Item friesWithoutTags = new ItemBuilder(FRIES).withTags().build();
        RestaurantBook expectedRestaurantBook = new RestaurantBookBuilder().withItem(BURGER)
                .withItem(friesWithoutTags)
                .build();
        assertTrue(restaurantBookWithItems.equals(expectedRestaurantBook));

        // different type
        assertFalse(restaurantBookWithItems.equals(null));
        assertFalse(restaurantBookWithItems.equals(0));
    }

    /**
     * A stub ReadOnlyRestaurantBook whose lists can violate interface constraints.
     */
    private static class RestaurantBookStub implements ReadOnlyRestaurantBook {

        private final ObservableList<Account> accounts = FXCollections.observableArrayList();
        private final ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
        private final ObservableList<Item> items = FXCollections.observableArrayList();
        private final ObservableList<Reservation> reservations = FXCollections.observableArrayList();
        private final ObservableList<SalesRecord> records = FXCollections.observableArrayList();

        RestaurantBookStub(Collection<Account> accounts, Collection<Item> items,
                Collection<Reservation> reservations, Collection<SalesRecord> records,
                Collection<Ingredient> ingredients) {
            this.accounts.setAll(accounts);
            this.ingredients.setAll(ingredients);
            this.items.setAll(items);
            this.reservations.setAll(reservations);
            this.records.setAll(records);
        }

        @Override
        public ObservableList<Account> getAccountList() {
            return accounts;
        }

        @Override
        public ObservableList<Reservation> getReservationList() {
            return reservations;
        }

        @Override
        public ObservableList<SalesRecord> getRecordList() {
            return records;
        }

        @Override
        public ObservableList<Ingredient> getIngredientList() {
            return ingredients;
        }

        @Override
        public ObservableList<Item> getItemList() {
            return items;
        }
    }
}

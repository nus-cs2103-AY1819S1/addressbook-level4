package seedu.restaurant.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_TAG_BURGER;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_ITEM_TAG_CHEESE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_RESERVATION_PAX_BILLY;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_TAG_TEST;
import static seedu.restaurant.testutil.TypicalPersons.ALICE;
import static seedu.restaurant.testutil.TypicalPersons.AMY;
import static seedu.restaurant.testutil.TypicalPersons.BOB;
import static seedu.restaurant.testutil.TypicalPersons.DYLAN;
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
import seedu.restaurant.model.person.Person;
import seedu.restaurant.model.person.exceptions.DuplicatePersonException;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.reservation.exceptions.DuplicateReservationException;
import seedu.restaurant.model.salesrecord.SalesRecord;
import seedu.restaurant.model.salesrecord.exceptions.DuplicateRecordException;
import seedu.restaurant.model.tag.Tag;
import seedu.restaurant.testutil.PersonBuilder;
import seedu.restaurant.testutil.RestaurantBookBuilder;
import seedu.restaurant.testutil.account.AccountBuilder;
import seedu.restaurant.testutil.menu.ItemBuilder;
import seedu.restaurant.testutil.reservation.ReservationBuilder;
import seedu.restaurant.testutil.salesrecords.RecordBuilder;

public class RestaurantBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RestaurantBook restaurantBook = new RestaurantBook();
    private RestaurantBook restaurantBookWithPersons = null;

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), restaurantBook.getPersonList());
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
    public void resetData_withDuplicatePersonsWithRecordsAndAccounts_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE)
                .withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        List<Account> newAccounts = Arrays.asList(DEMO_ADMIN, DEMO_ONE);
        List<Item> newItems = Arrays.asList(APPLE_JUICE);
        List<Reservation> newReservations = Arrays.asList(ANDREW);
        List<SalesRecord> newRecords = Arrays.asList(RECORD_DEFAULT, RECORD_ONE);
        List<Ingredient> newIngredients = Arrays.asList(AVOCADO, BEANS);
        RestaurantBookStub newData = new RestaurantBookStub(newPersons, newAccounts, newItems, newReservations,
                newRecords, newIngredients);

        thrown.expect(DuplicatePersonException.class);
        restaurantBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        restaurantBook.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInRestaurantBook_returnsFalse() {
        assertFalse(restaurantBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInRestaurantBook_returnsTrue() {
        restaurantBook.addPerson(ALICE);
        assertTrue(restaurantBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInRestaurantBook_returnsTrue() {
        restaurantBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE)
                .withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(restaurantBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        restaurantBook.getPersonList().remove(0);
    }

    @Test
    public void removeTag_noSuchTag_restaurantBookUnmodified() {
        restaurantBookWithPersons = new RestaurantBookBuilder().withPerson(AMY).withPerson(BOB).build();

        restaurantBookWithPersons.removeTag(new Tag(VALID_TAG_TEST));

        RestaurantBook expectedRestaurantBook = new RestaurantBookBuilder().withPerson(AMY).withPerson(BOB).build();

        assertEquals(restaurantBookWithPersons, expectedRestaurantBook);
    }

    @Test
    public void removeTag_fromAllPersons_restaurantBookModified() {
        restaurantBookWithPersons = new RestaurantBookBuilder().withPerson(AMY).withPerson(BOB).build();

        restaurantBookWithPersons.removeTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutTags = new PersonBuilder(AMY).withTags().build();
        Person bobWithoutFriendTag = new PersonBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();

        RestaurantBook expectedRestaurantBook = new RestaurantBookBuilder().withPerson(amyWithoutTags)
                .withPerson(bobWithoutFriendTag)
                .build();

        assertEquals(restaurantBookWithPersons, expectedRestaurantBook);
    }

    @Test
    public void removeTag_fromOnePerson_restaurantBookModified() {
        restaurantBookWithPersons = new RestaurantBookBuilder()
                .withPerson(AMY)
                .withPerson(DYLAN)
                .build();

        restaurantBookWithPersons.removeTag(new Tag(VALID_TAG_FRIEND));

        Person amyWithoutTags = new PersonBuilder(AMY).withTags().build();

        RestaurantBook expectedRestaurantBook = new RestaurantBookBuilder().withPerson(amyWithoutTags)
                .withPerson(DYLAN)
                .build();

        assertEquals(restaurantBookWithPersons, expectedRestaurantBook);
    }

    @Test
    public void resetData_withDuplicateRecordsWithPersonsAndAccounts_throwsDuplicateRecordException() {
        // Two records with the same date and name
        SalesRecord record = new RecordBuilder(RECORD_ONE)
                .withDate(RECORD_DEFAULT.getDate().toString())
                .withName(RECORD_DEFAULT.getName().toString())
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, BOB);
        List<Account> newAccounts = Arrays.asList(DEMO_ADMIN, DEMO_ONE);
        List<Item> newItems = Arrays.asList(APPLE_JUICE);
        List<Reservation> newReservations = Arrays.asList(ANDREW);
        List<SalesRecord> newRecords = Arrays.asList(RECORD_DEFAULT, record);
        List<Ingredient> newIngredients = Arrays.asList(AVOCADO, BEANS);
        RestaurantBookStub newData = new RestaurantBookStub(newPersons, newAccounts, newItems, newReservations,
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

    //@@author AZhiKai
    @Test
    public void resetData_withDuplicateAccountsWithPersonsAndRecords_throwsDuplicateAccountException() {
        // Two accounts with the same username
        Account account = new AccountBuilder(DEMO_ONE)
                .withUsername(DEMO_ADMIN.getUsername().toString())
                .build();
        List<Account> newAccounts = Arrays.asList(DEMO_ADMIN, account);
        List<Person> newPersons = Arrays.asList(ALICE, BOB);
        List<Item> newItems = Arrays.asList(APPLE_JUICE);
        List<Reservation> newReservations = Arrays.asList(ANDREW);
        List<SalesRecord> newRecords = Arrays.asList(RECORD_DEFAULT, RECORD_ONE);
        List<Ingredient> newIngredients = Arrays.asList(AVOCADO, BEANS);
        RestaurantBookStub newData = new RestaurantBookStub(newPersons, newAccounts, newItems, newReservations,
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
        List<Person> newPersons = Arrays.asList(ALICE);
        List<Account> newAccounts = Arrays.asList(DEMO_ADMIN);
        List<Item> newItems = Arrays.asList(APPLE_JUICE, editedApple);
        List<Reservation> newReservations = Arrays.asList(ANDREW);
        List<SalesRecord> newRecords = Arrays.asList(RECORD_DEFAULT, RECORD_ONE);
        List<Ingredient> newIngredients = Arrays.asList(AVOCADO, BEANS);
        RestaurantBookStub newData = new RestaurantBookStub(newPersons, newAccounts, newItems, newReservations,
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
        restaurantBookWithPersons = new RestaurantBookBuilder().withItem(BEEF_BURGER).withItem(BURGER).build();

        restaurantBookWithPersons.removeTagForMenu(new Tag(VALID_ITEM_TAG_CHEESE));

        RestaurantBook expectedRestaurantBook = new RestaurantBookBuilder().withItem(BEEF_BURGER).withItem(BURGER)
                .build();

        assertEquals(restaurantBookWithPersons, expectedRestaurantBook);
    }

    @Test
    public void removeTagForMenu_fromAllItems_restaurantBookModified() {
        restaurantBookWithPersons = new RestaurantBookBuilder().withItem(CHEESE_BURGER).withItem(FRIES).build();

        restaurantBookWithPersons.removeTagForMenu(new Tag(VALID_ITEM_TAG_CHEESE));

        Item cheeseWithoutCheeseTags = new ItemBuilder(CHEESE_BURGER).withTags(VALID_ITEM_TAG_BURGER).build();
        Item friesWithoutTags = new ItemBuilder(FRIES).withTags().build();

        RestaurantBook expectedRestaurantBook = new RestaurantBookBuilder().withItem(cheeseWithoutCheeseTags)
                .withItem(friesWithoutTags)
                .build();

        assertEquals(restaurantBookWithPersons, expectedRestaurantBook);
    }

    @Test
    public void removeTagForMenu_fromOneItem_restaurantBookModified() {
        restaurantBookWithPersons = new RestaurantBookBuilder().withItem(FRIES).withItem(BURGER).build();

        restaurantBookWithPersons.removeTagForMenu(new Tag(VALID_ITEM_TAG_CHEESE));

        Item friesWithoutTags = new ItemBuilder(FRIES).withTags().build();

        RestaurantBook expectedRestaurantBook = new RestaurantBookBuilder().withItem(friesWithoutTags)
                .withItem(BURGER)
                .build();

        assertEquals(restaurantBookWithPersons, expectedRestaurantBook);
    }

    // Reservation Management
    @Test
    public void resetData_withDuplicatePersonsWithRecordsAndAccounts_throwsDuplicateReservationException() {
        // Two persons with the same identity fields
        Reservation editedAndrew = new ReservationBuilder(ANDREW)
                .withPax(VALID_RESERVATION_PAX_BILLY)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE);
        List<Account> newAccounts = Arrays.asList(DEMO_ADMIN, DEMO_ONE);
        List<Item> newItems = Arrays.asList(APPLE_JUICE);
        List<Reservation> newReservations = Arrays.asList(ANDREW, editedAndrew);
        List<SalesRecord> newRecords = Arrays.asList(RECORD_DEFAULT, RECORD_ONE);
        List<Ingredient> newIngredients = Arrays.asList(AVOCADO, BEANS);
        RestaurantBookStub newData = new RestaurantBookStub(newPersons, newAccounts, newItems, newReservations,
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
        restaurantBookWithPersons = new RestaurantBookBuilder().withItem(APPLE_JUICE).withItem(BEEF_BURGER).build();
        assertEquals(sortedByName, restaurantBookWithPersons);
    }

    @Test
    public void sortMenuByPrice_restaurantBookModified() {
        RestaurantBook sortedByPrice = new RestaurantBookBuilder().withItem(BEEF_BURGER).withItem(APPLE_JUICE).build();
        sortedByPrice.sortMenu(SortMethod.PRICE);
        restaurantBookWithPersons = new RestaurantBookBuilder().withItem(APPLE_JUICE).withItem(BEEF_BURGER).build();
        assertEquals(sortedByPrice, restaurantBookWithPersons);
    }

    @Test
    public void equals() {
        restaurantBookWithPersons = new RestaurantBookBuilder().withPerson(AMY).withPerson(DYLAN).build();
        // same object
        assertTrue(restaurantBookWithPersons.equals(restaurantBookWithPersons));
        restaurantBookWithPersons.removeTag(new Tag(VALID_TAG_FRIEND));
        Person amyWithoutTags = new PersonBuilder(AMY).withTags().build();
        RestaurantBook expectedRestaurantBook = new RestaurantBookBuilder().withPerson(amyWithoutTags)
                .withPerson(DYLAN)
                .build();
        assertTrue(restaurantBookWithPersons.equals(expectedRestaurantBook));
        // different type
        assertFalse(restaurantBookWithPersons.equals(null));
        assertFalse(restaurantBookWithPersons.equals(0));
    }

    /**
     * A stub ReadOnlyRestaurantBook whose persons list can violate interface constraints.
     */
    private static class RestaurantBookStub implements ReadOnlyRestaurantBook {

        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Account> accounts = FXCollections.observableArrayList();
        private final ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
        private final ObservableList<Item> items = FXCollections.observableArrayList();
        private final ObservableList<Reservation> reservations = FXCollections.observableArrayList();
        private final ObservableList<SalesRecord> records = FXCollections.observableArrayList();

        RestaurantBookStub(Collection<Person> persons, Collection<Account> accounts, Collection<Item> items,
                Collection<Reservation> reservations, Collection<SalesRecord> records,
                Collection<Ingredient> ingredients) {
            this.persons.setAll(persons);
            this.accounts.setAll(accounts);
            this.ingredients.setAll(ingredients);
            this.items.setAll(items);
            this.reservations.setAll(reservations);
            this.records.setAll(records);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
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

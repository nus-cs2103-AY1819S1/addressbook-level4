package seedu.restaurant.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.restaurant.commons.exceptions.IllegalValueException;
import seedu.restaurant.model.ReadOnlyRestaurantBook;
import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.ingredient.Ingredient;
import seedu.restaurant.model.menu.Item;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.sales.SalesRecord;
import seedu.restaurant.storage.elements.XmlAdaptedAccount;
import seedu.restaurant.storage.elements.XmlAdaptedIngredient;
import seedu.restaurant.storage.elements.XmlAdaptedItem;
import seedu.restaurant.storage.elements.XmlAdaptedRecord;
import seedu.restaurant.storage.elements.XmlAdaptedReservation;

/**
 * An Immutable RestaurantBook that is serializable to XML format
 */
@XmlRootElement(name = "restaurantbook")
public class XmlSerializableRestaurantBook {

    public static final String MESSAGE_DUPLICATE_RECORD = "Records list contains duplicate record(s).";
    public static final String MESSAGE_DUPLICATE_ACCOUNT = "Account list contains duplicate account(s).";
    public static final String MESSAGE_DUPLICATE_INGREDIENT = "Ingredient list contains duplicate ingredient(s).";
    public static final String MESSAGE_DUPLICATE_ITEM = "Items list contains duplicate item(s).";
    public static final String MESSAGE_DUPLICATE_RESERVATION = "Reservation list contains duplicate reservation(s).";

    private RestaurantBook restaurantBook;

    @XmlElement
    private List<XmlAdaptedRecord> records;

    @XmlElement
    private List<XmlAdaptedAccount> accounts;

    @XmlElement
    private List<XmlAdaptedIngredient> ingredients;

    @XmlElement
    private List<XmlAdaptedItem> items;

    @XmlElement
    private List<XmlAdaptedReservation> reservations;

    /**
     * Creates an empty XmlSerializableRestaurantBook. This empty constructor is required for marshalling.
     */
    public XmlSerializableRestaurantBook() {
        restaurantBook = new RestaurantBook();
        records = new ArrayList<>();
        accounts = new ArrayList<>();
        ingredients = new ArrayList<>();
        items = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableRestaurantBook(ReadOnlyRestaurantBook src) {
        this();
        records.addAll(src.getRecordList().stream().map(XmlAdaptedRecord::new).collect(Collectors.toList()));
        accounts.addAll(src.getAccountList().stream().map(XmlAdaptedAccount::new).collect(Collectors.toList()));
        ingredients.addAll(src.getIngredientList().stream().map(XmlAdaptedIngredient::new)
                .collect(Collectors.toList()));
        items.addAll(src.getItemList().stream().map(XmlAdaptedItem::new).collect(Collectors.toList()));
        reservations.addAll(src.getReservationList().stream()
                .map(XmlAdaptedReservation::new).collect(Collectors.toList()));
    }

    /**
     * Returns the converted model {@code RestaurantBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates record when not
     *         allowed.
     */
    public RestaurantBook toModelType() throws IllegalValueException {
        processAccounts();
        processIngredients();
        processRecords();
        processItems();
        processReservations();
        return restaurantBook;
    }

    /**
     * Converts this restaurantbook's record list into the model's {@code RestaurantBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the {@code
     *         XmlAdaptedRecord}.
     */
    private void processRecords() throws IllegalValueException {
        for (XmlAdaptedRecord r : records) {
            SalesRecord record = r.toModelType();
            if (restaurantBook.hasRecord(record)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RECORD);
            }
            restaurantBook.addRecord(record);
        }
    }

    //@@author AZhiKai
    /**
     * Converts this account record into the model's {@code Account} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the {@code
     *         XmlAdaptedAccount}.
     */
    private void processAccounts() throws IllegalValueException {
        for (XmlAdaptedAccount acc : accounts) {
            Account account = acc.toModelType();
            if (restaurantBook.hasAccount(account)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ACCOUNT);
            }
            restaurantBook.addAccount(account);
        }
    }

    //@@author m4dkip
    /**
     * Converts this reservation record into the model's {@code Reservation} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the {@code
     *         XmlAdaptedReservation}.
     */
    public void processReservations() throws IllegalValueException {
        for (XmlAdaptedReservation res : reservations) {
            Reservation reservation = res.toModelType();
            if (restaurantBook.hasReservation(reservation)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_RESERVATION);
            }
            restaurantBook.addReservation(reservation);
        }
    }

    /**
     * Converts this ingredient record into the model's {@code Ingredient} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the {@code
     *         XmlAdaptedIngredient}.
     */
    public void processIngredients() throws IllegalValueException {
        for (XmlAdaptedIngredient i : ingredients) {
            Ingredient ingredient = i.toModelType();
            if (restaurantBook.hasIngredient(ingredient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INGREDIENT);
            }
            restaurantBook.addIngredient(ingredient);
        }
    }

    //@@author yican95
    /**
     * Converts this item record into the model's {@code Item} object.
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the {@code
     * XmlAdaptedItem}.
     */
    public void processItems() throws IllegalValueException {
        for (XmlAdaptedItem i : items) {
            Item item = i.toModelType();
            if (restaurantBook.hasItem(item)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ITEM);
            }
            restaurantBook.addItem(item);
        }
    }
    //@@author
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableRestaurantBook)) {
            return false;
        }
        return accounts.equals(((XmlSerializableRestaurantBook) other).accounts)
                && ingredients.equals(((XmlSerializableRestaurantBook) other).ingredients)
                && items.equals(((XmlSerializableRestaurantBook) other).items)
                && reservations.equals(((XmlSerializableRestaurantBook) other).reservations)
                && records.equals(((XmlSerializableRestaurantBook) other).records);
    }
}

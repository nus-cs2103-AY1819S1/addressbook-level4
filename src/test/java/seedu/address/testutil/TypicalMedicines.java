package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_NAME_PANADOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_NAME_ZYRTEC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MINIMUM_STOCK_QUANTITY_PANADOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MINIMUM_STOCK_QUANTITY_ZYRTEC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_PER_UNIT_PANADOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_PER_UNIT_ZYRTEC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_PANADOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_ZYRTEC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STOCK_PANADOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STOCK_ZYRTEC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.medicine.Medicine;


/**
 * A utility class containing a list of {@code Medicine} objects to be used in tests.
 */
public class TypicalMedicines {

    public static final Medicine ACETAMINOPHEN = new MedicineBuilder().withMedicineName("Acetaminophen")
            .withMinimumStockQuantity(200)
            .withPricePerUnit("1")
            .withSerialNumber("1234522")
            .withStock(4000).build();
    public static final Medicine BACITRACIN = new MedicineBuilder().withMedicineName("Bacitracin")
            .withMinimumStockQuantity(132)
            .withPricePerUnit("6")
            .withSerialNumber("3444211")
            .withStock(983).build();
    public static final Medicine CALEX = new MedicineBuilder().withMedicineName("CALEX")
            .withMinimumStockQuantity(30)
            .withStock(93)
            .withSerialNumber("9374830")
            .withPricePerUnit("100").build();
    public static final Medicine DALMANE = new MedicineBuilder().withMedicineName("Dalmane")
            .withMinimumStockQuantity(450)
            .withStock(450)
            .withSerialNumber("39193232")
            .withPricePerUnit("2").build();
    public static final Medicine ECOTRIN = new MedicineBuilder().withMedicineName("ECOTRIN")
            .withMinimumStockQuantity(234)
            .withStock(9)
            .withSerialNumber("01380952")
            .withPricePerUnit("22").build();
    public static final Medicine FABIOR = new MedicineBuilder().withMedicineName("Faboir")
            .withMinimumStockQuantity(2344)
            .withStock(94333)
            .withSerialNumber("2131433445")
            .withPricePerUnit("23").build();
    public static final Medicine GELATO = new MedicineBuilder().withMedicineName("Gelato")
            .withMinimumStockQuantity(214)
            .withStock(9482)
            .withSerialNumber("397380111")
            .withPricePerUnit("22").build();

    // Manually added
    public static final Medicine HALAVEN = new MedicineBuilder().withMedicineName("Halaven")
            .withMinimumStockQuantity(233)
            .withStock(2344)
            .withSerialNumber("92470506")
            .withPricePerUnit("12").build();
    public static final Medicine IDAPROFEN = new MedicineBuilder().withMedicineName("Idaprofen")
            .withMinimumStockQuantity(229)
            .withStock(84821)
            .withSerialNumber("213456743")
            .withPricePerUnit("23").build();

    // Manually added - Medicine's details found in {@code CommandTestUtil}
    public static final Medicine PANADOL = new MedicineBuilder().withMedicineName(VALID_MEDICINE_NAME_PANADOL)
            .withStock(VALID_STOCK_PANADOL)
            .withMinimumStockQuantity(VALID_MINIMUM_STOCK_QUANTITY_PANADOL)
            .withSerialNumber(VALID_SERIAL_NUMBER_PANADOL)
            .withPricePerUnit(VALID_PRICE_PER_UNIT_PANADOL).build();
    public static final Medicine ZYRTEC = new MedicineBuilder().withMedicineName(VALID_MEDICINE_NAME_ZYRTEC)
            .withMinimumStockQuantity(VALID_MINIMUM_STOCK_QUANTITY_ZYRTEC)
            .withSerialNumber(VALID_SERIAL_NUMBER_ZYRTEC)
            .withPricePerUnit(VALID_PRICE_PER_UNIT_ZYRTEC)
            .withStock(VALID_STOCK_ZYRTEC).build();

    private TypicalMedicines() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical medicines.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Medicine medicine : getTypicalMedicines()) {
            ab.addMedicine(medicine);
        }
        return ab;
    }

    public static List<Medicine> getTypicalMedicines() {
        return new ArrayList<>(Arrays.asList(ACETAMINOPHEN, BACITRACIN, CALEX, DALMANE, ECOTRIN, FABIOR, GELATO));
    }
}

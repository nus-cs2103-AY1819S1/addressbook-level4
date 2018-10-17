package seedu.inventory.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.ReadOnlySaleList;
import seedu.inventory.model.SaleList;
import seedu.inventory.model.sale.Sale;

/**
 * An Immutable Sale List that is serializable to XML format
 */
@XmlRootElement(name = "sales")
public class XmlSerializableSaleList {

    public static final String MESSAGE_DUPLICATE_SALE = "Sale list contains duplicate sale(s).";

    @XmlElement(name = "sale")
    private List<XmlAdaptedSale> saleList;

    /**
     * Creates an empty XmlSerializableSaleList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableSaleList() {
        saleList = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableSaleList(ReadOnlySaleList src) {
        this();

        saleList.addAll(src.getSaleList().stream().map(XmlAdaptedSale::new).collect(Collectors.toList()));
    }

    /**
     * Converts this sale list into the model's {@code SaleList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedSale}.
     */
    public SaleList toModelType(ReadOnlyInventory inventory) throws IllegalValueException {
        SaleList saleList = new SaleList();
        for (XmlAdaptedSale p : this.saleList) {
            Sale sale = p.toModelType(inventory);
            if (saleList.hasSale(sale)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_SALE);
            }
            saleList.addSale(sale);
        }
        return saleList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableSaleList)) {
            return false;
        }
        return saleList.equals(((XmlSerializableSaleList) other).saleList);
    }
}

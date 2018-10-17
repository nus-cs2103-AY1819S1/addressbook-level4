package seedu.inventory.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.sale.Sale;
import seedu.inventory.model.sale.SaleDate;
import seedu.inventory.model.sale.SaleId;

/**
 * JAXB-friendly version of the Sale.
 */
public class XmlAdaptedSale {

    public static final String MISSING_ITEM = "Sale item cannot be found!";

    @XmlElement(required = true)
    private String saleId;
    @XmlElement(required = true)
    private String saleSku;
    @XmlElement(required = true)
    private String saleQuantity;
    @XmlElement(required = true)
    private String saleDate;

    /**
     * Constructs an XmlAdaptedSale.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSale() {}

    /**
     * Constructs an {@code XmlAdaptedSale} with the given item details.
     */
    public XmlAdaptedSale(String saleId, String saleSku, String saleQuantity, String saleDate) {
        this.saleId = saleId;
        this.saleSku = saleSku;
        this.saleQuantity = saleQuantity;
        this.saleDate = saleDate;
    }

    /**
     * Converts a given Sale into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedSale
     */
    public XmlAdaptedSale(Sale source) {
        saleId = source.getSaleId().toString();
        saleSku = source.getItem().getSku().toString();
        saleQuantity = source.getSaleQuantity().toString();
        saleDate = source.getSaleDate().toString();
    }

    /**
     * Converts this jaxb-friendly adapted sale object into the model's Sale object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted sale
     */
    public Sale toModelType(ReadOnlyInventory inventory) throws IllegalValueException {
        if (saleId == null) {
            throw new IllegalValueException(SaleId.MESSAGE_ID_CONSTRAINTS);
        }

        if (!SaleId.isValidSaleId(saleId)) {
            throw new IllegalValueException(SaleId.MESSAGE_ID_CONSTRAINTS);
        }

        if (saleSku == null) {
            throw new IllegalValueException(Sku.MESSAGE_SKU_CONSTRAINTS);
        }

        if (!Sku.isValidSku(saleSku)) {
            throw new IllegalValueException(Sku.MESSAGE_SKU_CONSTRAINTS);
        }

        Item modelSaleItem;

        try {
            modelSaleItem = inventory.getItemBySku(saleSku);

            if (modelSaleItem == null) {
                throw new IllegalValueException(MISSING_ITEM);
            }
        } catch (NullPointerException e) {
            throw new IllegalValueException(MISSING_ITEM);
        }

        final SaleId modelSaleId = new SaleId(saleId);

        if (saleQuantity == null) {
            throw new IllegalValueException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }

        if (!Quantity.isValidQuantity(saleQuantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }

        final Quantity modelQuantity = new Quantity(saleQuantity);

        if (saleDate == null) {
            throw new IllegalValueException(SaleDate.MESSAGE_DATE_CONSTRAINTS);
        }

        if (!SaleDate.isValidSaleDate(saleDate)) {
            throw new IllegalValueException(SaleDate.MESSAGE_DATE_CONSTRAINTS);
        }

        final SaleDate modelSaleDate = new SaleDate(saleDate);

        return new Sale(modelSaleId, modelSaleItem, modelQuantity, modelSaleDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedSale)) {
            return false;
        }

        XmlAdaptedSale otherItem = (XmlAdaptedSale) other;
        return Objects.equals(saleId, otherItem.saleId)
                && Objects.equals(saleQuantity, otherItem.saleQuantity)
                && Objects.equals(saleDate, otherItem.saleDate);
    }
}

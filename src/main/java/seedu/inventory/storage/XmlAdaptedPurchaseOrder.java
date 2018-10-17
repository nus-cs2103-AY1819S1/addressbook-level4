package seedu.inventory.storage;

import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;

import seedu.inventory.commons.exceptions.IllegalValueException;

import seedu.inventory.model.item.Name;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.purchaseorder.RequiredDate;
import seedu.inventory.model.purchaseorder.Supplier;


/**
 * JAXB-friendly version of the Purchase Order.
 */
public class XmlAdaptedPurchaseOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Purchase Order's %s field is missing!";

    @XmlElement(required = true)
    private String sku;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String quantity;
    @XmlElement(required = true)
    private String reqDate;
    @XmlElement(required = true)
    private String supplier;
    @XmlElement(required = true)
    private String status;

    /**
     * Constructs an XmlAdaptedPurchaseOrder.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPurchaseOrder() {
    }

    /**
     * Constructs an {@code XmlAdaptedItem} with the given item details.
     */
    public XmlAdaptedPurchaseOrder(String sku, String name, String quantity, String reqDate, String supplier,
                                   String status) {
        this.name = name;
        this.quantity = quantity;
        this.sku = sku;
        this.reqDate = reqDate;
        this.supplier = supplier;
        this.status = status;
    }

    /**
     * Converts a given Purchase order into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPurchaseOrder
     */
    public XmlAdaptedPurchaseOrder(PurchaseOrder source) {
        sku = source.getSku().value;
        name = source.getName().fullName;
        quantity = source.getQuantity().value;
        reqDate = source.getReqDate().requiredDate;
        supplier = source.getSupplier().supplierName;
        status = source.getStatus().name();
    }

    /**
     * Converts this jaxb-friendly adapted item object into the model's Purchase Order object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted item
     */
    public PurchaseOrder toModelType() throws IllegalValueException {

        if (sku == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Sku.class.getSimpleName()));
        }
        if (!Sku.isValidSku(sku)) {
            throw new IllegalValueException(Sku.MESSAGE_SKU_CONSTRAINTS);
        }
        final Sku modelSku = new Sku(sku);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (quantity == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName())
            );
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        if (reqDate == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, RequiredDate.class.getSimpleName())
            );
        }
        if (!RequiredDate.isValidDate(reqDate)) {
            throw new IllegalValueException(RequiredDate.MESSAGE_DATE_CONSTRAINTS);
        }
        final RequiredDate modelRequiredDate = new RequiredDate(reqDate);

        if (supplier == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Supplier.class.getSimpleName())
            );
        }
        if (!Supplier.isValidSupplier(supplier)) {
            throw new IllegalValueException(Supplier.MESSAGE_SUPPLIER_CONSTRAINTS);
        }
        final Supplier modelSupplier = new Supplier(supplier);

        if (status == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, PurchaseOrder.Status.class.getSimpleName())
            );
        }
        if (!PurchaseOrder.Status.isValidStatus(status)) {
            throw new IllegalValueException(PurchaseOrder.Status.MESSAGE_STATUS_CONSTRAINTS);
        }
        final PurchaseOrder.Status modelStatus = PurchaseOrder.Status.valueOf(status);

        return new PurchaseOrder(modelSku, modelName, modelQuantity, modelRequiredDate, modelSupplier, modelStatus);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPurchaseOrder)) {
            return false;
        }

        XmlAdaptedPurchaseOrder otherPurchaseOrder = (XmlAdaptedPurchaseOrder) other;
        return Objects.equals(sku, otherPurchaseOrder.sku)
                && Objects.equals(name, otherPurchaseOrder.name)
                && Objects.equals(quantity, otherPurchaseOrder.quantity)
                && Objects.equals(reqDate, otherPurchaseOrder.reqDate)
                && Objects.equals(supplier, otherPurchaseOrder.supplier)
                && Objects.equals(status, otherPurchaseOrder.status);

    }
}

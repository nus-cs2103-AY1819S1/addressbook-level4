package seedu.inventory.testutil.purchaseorder;

import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.purchaseorder.RequiredDate;
import seedu.inventory.model.purchaseorder.Supplier;

/**
 * A utility class to help with building Purchase order objects.
 */
public class PurchaseOrderBuilder {

    public static final String DEFAULT_SKU = "apple-iphone-xr";
    public static final String DEFAULT_QUANTITY = "10";
    public static final String DEFAULT_REQUIRED_DATE = "2098-12-31";
    public static final String DEFAULT_SUPPLIER = "Apple Inc.";
    public static final PurchaseOrder.Status DEFAULT_STATUS = PurchaseOrder.Status.PENDING;


    private Sku sku;
    private Quantity quantity;
    private RequiredDate reqDate;
    private Supplier supplier;
    private PurchaseOrder.Status status;

    public PurchaseOrderBuilder() {
        sku = new Sku(DEFAULT_SKU);
        quantity = new Quantity(DEFAULT_QUANTITY);
        reqDate = new RequiredDate(DEFAULT_REQUIRED_DATE);
        supplier = new Supplier(DEFAULT_SUPPLIER);
        status = DEFAULT_STATUS;
    }

    /**
     * Initializes the PurchaseOrderBuilder with the data of {@code purchaseorder}
     *
     * @param poToCopy the Purchase order to be copied
     */
    public PurchaseOrderBuilder(PurchaseOrder poToCopy) {
        sku = poToCopy.getSku();
        quantity = poToCopy.getQuantity();
        reqDate = poToCopy.getReqDate();
        supplier = poToCopy.getSupplier();
        status = poToCopy.getStatus();
    }

    /**
     * Sets the {@code Sku} of the {@code PurchaseOrder} that we are building.
     */
    public PurchaseOrderBuilder withSku(String sku) {
        this.sku = new Sku(sku);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code PurchaseOrder} that we are building.
     */
    public PurchaseOrderBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Sets the {@code RequiredDate} of the {@code PurchaseOrder} that we are building.
     */
    public PurchaseOrderBuilder withRequiredDate(String reqDate) {
        this.reqDate = new RequiredDate(reqDate);
        return this;
    }

    /**
     * Sets the {@code Supplier} of the {@code PurchaseOrder} that we are building.
     */
    public PurchaseOrderBuilder withSupplier(String supplier) {
        this.supplier = new Supplier(supplier);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code PurchaseOrder} that we are building.
     */
    public PurchaseOrderBuilder withStatus(String status) {
        this.status = PurchaseOrder.Status.valueOf(status);
        return this;
    }

    public PurchaseOrder build() {
        return new PurchaseOrder(sku, quantity, reqDate, supplier, status);
    }
}

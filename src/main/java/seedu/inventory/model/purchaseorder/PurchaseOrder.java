package seedu.inventory.model.purchaseorder;

import static seedu.inventory.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.inventory.model.item.Name;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;

/**
 * Represents a purchase order entity.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class PurchaseOrder {

    // Identity field
    private final Sku sku;
    private final Name name;

    // Data fields
    private final Quantity quantity;
    private final RequiredDate reqDate;
    private final Supplier supplier;
    private final Status status;

    /**
     * Status that can be applied.
     */
    public enum Status {
        PENDING,
        APPROVED,
        REJECTED;

        public static final Status DEFAULT_STATUS = Status.PENDING;

        public static final String MESSAGE_STATUS_CONSTRAINTS =
                "Status should only be one of the following state: PENDING, APPROVED, REJECTED";

        /**
         * Check if a given status is valid
         *
         * @param status
         * @return
         */
        public static boolean isValidStatus(String status) {
            for (Status s : Status.values()) {
                if (s.name().equals(status)) {
                    return true;
                }
            }

            return false;
        }
    }


    /**
     * Every field must be present and not null.
     */
    public PurchaseOrder(Sku sku, Name name, Quantity quantity, RequiredDate reqDate,
                         Supplier supplier, Status status) {
        requireAllNonNull(sku, name, quantity, reqDate, supplier, status);
        this.sku = sku;
        this.name = name;
        this.quantity = quantity;
        this.reqDate = reqDate;
        this.supplier = supplier;
        this.status = status;
    }

    public Sku getSku() {
        return sku;
    }

    public Name getName() {
        return name;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public RequiredDate getReqDate() {
        return reqDate;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns true if both purchase order have the same SKU.
     */
    public boolean isSameItem(PurchaseOrder otherPo) {
        if (otherPo == this) {
            return true;
        }

        return otherPo != null
                && otherPo.getSku().equals(getSku());
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, reqDate, supplier);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" SKU: ")
                .append(getSku())
                .append(" Item name: ")
                .append(getName())
                .append(" Quantity: ")
                .append(getQuantity())
                .append(" Date Required: ")
                .append(getReqDate())
                .append(" Supplier: ")
                .append(getSupplier())
                .append(" Status: ")
                .append(getStatus().name());
        return builder.toString();
    }

}

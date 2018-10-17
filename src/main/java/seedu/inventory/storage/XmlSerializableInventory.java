package seedu.inventory.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.Inventory;
import seedu.inventory.model.ReadOnlyInventory;
import seedu.inventory.model.StaffList;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.purchaseorder.PurchaseOrder;
import seedu.inventory.model.staff.Staff;

/**
 * An Immutable Inventory that is serializable to XML format
 */
@XmlRootElement(name = "inventory")
public class XmlSerializableInventory {

    public static final String MESSAGE_DUPLICATE_ITEM = "Inventory list contains duplicate item(s).";
    public static final String MESSAGE_DUPLICATE_STAFF = "Staff list contains duplicate staff(s).";

    @XmlElement
    private List<XmlAdaptedItem> items;
    @XmlElement
    private List<XmlAdaptedStaff> staffs;

    @XmlElement
    private List<XmlAdaptedPurchaseOrder> purchaseOrders;

    /**
     * Creates an empty XmlSerializableInventory.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableInventory() {
        items = new ArrayList<>();
        purchaseOrders = new ArrayList<>();
        staffs = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableInventory(ReadOnlyInventory src) {
        this();
        items.addAll(src.getItemList().stream().map(XmlAdaptedItem::new).collect(Collectors.toList()));
        purchaseOrders.addAll(src.getPurchaseOrderList().stream().map(XmlAdaptedPurchaseOrder::new)
                .collect(Collectors.toList()));
        staffs.addAll(src.getStaffList().stream().map(XmlAdaptedStaff::new).collect(Collectors.toList()));
    }

    /**
     * Converts this inventory into the model's {@code Inventory} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     *                               {@code XmlAdaptedItem}.
     */
    public Inventory toModelType() throws IllegalValueException {
        Inventory inventory = new Inventory();

        for (XmlAdaptedItem p : items) {
            Item item = p.toModelType();
            if (inventory.hasItem(item)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ITEM);
            }
            inventory.addItem(item);
        }


        for (XmlAdaptedPurchaseOrder po : purchaseOrders) {
            PurchaseOrder purchaseOrder = po.toModelType();
            inventory.addPurchaseOrder(purchaseOrder);
        }


        StaffList staffList = new StaffList();
        for (XmlAdaptedStaff s : staffs) {
            Staff staff = s.toModelType();
            if (staffList.hasStaff(staff)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STAFF);
            }
            staffList.addStaff(staff);
        }

        return inventory;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableInventory)) {
            return false;
        }
        return items.equals(((XmlSerializableInventory) other).items)
                && purchaseOrders.equals(((XmlSerializableInventory) other).purchaseOrders)
                && staffs.equals(((XmlSerializableInventory) other).staffs);
    }
}

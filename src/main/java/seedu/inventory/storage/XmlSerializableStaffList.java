package seedu.inventory.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.ReadOnlyStaffList;
import seedu.inventory.model.StaffList;
import seedu.inventory.model.staff.Staff;

/**
 * @author darren96
 * An Immutable StaffList that is serializable to XML format
 */
@XmlRootElement(name = "staffList")
public class XmlSerializableStaffList {

    public static final String MESSAGE_DUPLICATE_STAFF = "Staff list contains duplicate staff(s).";

    @XmlElement
    private List<XmlAdaptedStaff> staffs;

    /**
     * Constructs an empty XmlSerializableStaffList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableStaffList() {
        staffs = new ArrayList<>();
    }

    /**
     * Constructs a XmlSerializableStaffList with given ReadOnlyStaffList.
     *
     * @param src the ReadOnlyStaffList to be stored.
     */
    public XmlSerializableStaffList(ReadOnlyStaffList src) {
        this();
        staffs.addAll(src.getStaffList().stream().map(XmlAdaptedStaff::new).collect(Collectors.toList()));
    }

    /**
     * Converts this staff list into the model's {@code StaffList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedStaff}.
     */
    public StaffList toModelType() throws IllegalValueException {
        StaffList staffList = new StaffList();
        for (XmlAdaptedStaff s : staffs) {
            Staff staff = s.toModelType();
            if (staffList.hasStaff(staff)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STAFF);
            }
            staffList.addStaff(staff);
        }
        return staffList;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof XmlSerializableStaffList)) {
            return false;
        }

        if (other == this) {
            return true;
        }
        return staffs.equals(((XmlSerializableStaffList) other).staffs);
    }
}

package seedu.inventory.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.inventory.model.StaffList;
import seedu.inventory.model.staff.Staff;

/**
 * A utility class containing a list of {@code Staff} objects to be used in tests.
 */
public class TypicalStaffs {

    public static final Staff ZUL = new StaffBuilder().withName("Muhammad Zulqarnain")
            .withUsername("zulq9")
            .withPassword("zulq9999")
            .withRole(Staff.Role.admin).build();

    public static final Staff ESMOND = new StaffBuilder().withName("Esmond Tan")
            .withUsername("esmondTan")
            .withPassword("esmondtt")
            .withRole(Staff.Role.admin).build();

    public static final Staff TENGXIONG = new StaffBuilder().withName("Yao TengXiong")
            .withUsername("yaotx")
            .withPassword("pcyaotxcfanboi")
            .withRole(Staff.Role.manager).build();

    public static final Staff DARREN = new StaffBuilder().withName("Darren Ong")
            .withUsername("darren96")
            .withPassword("darrenSinglenus")
            .withRole(Staff.Role.manager).build();

    public static final Staff WANGCHAO = new StaffBuilder().withName("Wang Chao")
            .withUsername("fzdy1914")
            .withPassword("fengzhidaoying")
            .withRole(Staff.Role.user).build();

    // Manually added
    public static final Staff ANGZHIKAI = new StaffBuilder().withName("Ang Zhi Kai")
            .withUsername("azhikai")
            .withPassword("aaazeekai")
            .withRole(Staff.Role.user).build();
    public static final Staff CHUAENGSOON = new StaffBuilder().withName("Chua Eng Soon")
            .withUsername("chuaes")
            .withPassword("arsenalTahi")
            .withRole(Staff.Role.manager).build();

    private TypicalStaffs() {}

    /**
     * Returns an {@code StaffList} with all the typical staffs.
     */
    public static StaffList getTypicalStaffList() {
        StaffList staffList = new StaffList();
        for (Staff staff : getTypicalStaffs()) {
            staffList.addStaff(staff);
        }
        return staffList;
    }

    public static List<Staff> getTypicalStaffs() {
        return new ArrayList<>(Arrays.asList(ZUL, ESMOND, TENGXIONG, DARREN, WANGCHAO));
    }
}

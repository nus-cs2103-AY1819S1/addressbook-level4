package seedu.address.model.permission;

/**
 * Permissions available in the system, that can be assigned to individual users.
 */
public enum Permission {
    ADD_EMPLOYEE,
    DELETE_EMPLOYEE,
    EDIT_EMPLOYEE,
    RESTORE_EMPLOYEE,

    VIEW_EMPLOYEE_LEAVE,
    APPROVE_LEAVE,

    ADD_ASSIGNMENT,
    DELETE_ASSIGNMENT,
    EDIT_ASSIGNMENT,

    ASSIGN_PERMISSION; //Might need a superadmin permission to control this permission.

    public static final String MESSAGE_INVALID_PERMISSION = "Invalid permission name.";

    private static final Permission[] listOfAllPermissions = values();

    /**
     * Checks if {@code permission} is valid.
     */
    public static boolean isValidPermission(String permission) {
        for (Permission p : listOfAllPermissions) {
            if (p.name().equals(permission)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "[" + this.name() + "]";
    }

}

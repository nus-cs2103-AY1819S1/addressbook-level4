package seedu.address.model.permission;

/**
 * Permissions available in the system, that can be assigned to individual users.
 */
public enum Permission {
    ADD_EMPLOYEE,
    REMOVE_EMPLOYEE,
    EDIT_EMPLOYEE,

    VIEW_EMPLOYEE_LEAVE,
    APPROVE_LEAVE,

    CREATE_PROJECT,
    VIEW_PROJECT,
    ASSIGN_PROJECT,

    CREATE_DEPARTMENT,
    ASSIGN_DEPARTMENT,

    ASSIGN_PERMISSION //Might need a superadmin permission to control this permission.
}

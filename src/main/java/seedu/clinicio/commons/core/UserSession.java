package seedu.clinicio.commons.core;

import seedu.clinicio.model.staff.Staff;

//@@author jjlee050
/**
 * A utility class to handle user session in ClinicIO
 */
public class UserSession {
    private static Staff currentUser;

    /**
     * Create a new user session for ClinicIO.
     * @param user Authenticated user to add into user session
     */
    public static void createSession(Staff user) {
        currentUser = user;
    }

    /**
     * Delete the user session from ClinicIO.
     */
    public static void destorySession() {
        currentUser = null;
    }

    /**
     * Check if user is login to ClinicIO.
     * @return The status of any user session.
     */
    public static boolean isLogin() {
        return currentUser != null;
    }
}

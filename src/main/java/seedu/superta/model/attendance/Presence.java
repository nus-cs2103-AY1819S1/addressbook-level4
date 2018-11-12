package seedu.superta.model.attendance;

/**
 * Enumerations for attendance. Allows future extensibility.
 */
public enum Presence {
    PRESENT,
    ABSENT,
    EXCUSED;

    /**
     * Returns the enum constant specified with the name.
     * @param token the string matching the enum constant. Needs to be an exact match, including whitespace and case.
     */
    public static Presence getPresence(String token) {
        return Presence.valueOf(token);
    }

    public static String token(Presence status) {
        return status.name();
    }
}

package seedu.clinicio.ui.util;

import seedu.clinicio.ui.CommandBox;

//@@author jjlee050
/**
 * A formatter class responsible to handle 
 * masking and unmasking password for password prefix.
 */
public class PasswordPrefixFormatter {

    private CommandBox commandBox;
    private StringBuilder tempPassword;
    
    public PasswordPrefixFormatter(CommandBox commandBox) {
        this.commandBox = commandBox;
        tempPassword = new StringBuilder();
    }

    /**
     * Check if {@code CommandBox} has password prefix (pass/).
     */
    public boolean hasPasswordPrefix() {
        return false;
    }

    /**
     * Mask text 
     */
    public void maskPassword() {}

    public void unmaskPassword() {}

    public void resetTempPassword() {}
}

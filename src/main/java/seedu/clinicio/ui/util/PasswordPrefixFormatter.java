package seedu.clinicio.ui.util;

import static seedu.clinicio.logic.parser.CliSyntax.PREFIX_PASSWORD;

import javafx.scene.control.TextField;

//@@author jjlee050
/**
 * A formatter class responsible to handle
 * masking and unmasking password for password prefix.
 */
public class PasswordPrefixFormatter {

    private TextField commandTextField;
    private StringBuilder tempPassword;

    public PasswordPrefixFormatter(TextField commandTextField) {
        tempPassword = new StringBuilder();
        this.commandTextField = commandTextField;
    }

    /**
     * Check if {@code CommandBox} has password prefix (pass/).
     */
    public boolean hasPasswordPrefix() {
        return commandTextField.getText().contains(PREFIX_PASSWORD.getPrefix());
    }

    /**
     * Mask the password after pass/ prefix to '-'.
     */
    public String maskPassword(boolean isHistory) {
        if (!hasPasswordPrefix()) {
            return commandTextField.getText();
        }

        int passwordPrefixIndex = commandTextField.getText().indexOf(PREFIX_PASSWORD.getPrefix());
        int spaceAfterPasswordIndex = commandTextField.getText().indexOf(' ', passwordPrefixIndex);

        String prefixesBeforePasswordPrefix = commandTextField.getText().substring(0, passwordPrefixIndex);
        String password = findPassword(passwordPrefixIndex, spaceAfterPasswordIndex);

        StringBuilder maskedPassword = appendMaskedPassword(isHistory, password);

        return prefixesBeforePasswordPrefix + PREFIX_PASSWORD.getPrefix()
                + maskedPassword.toString() + getPrefixesAfterPasswordPrefix(spaceAfterPasswordIndex);
    }

    /**
     * Unmask the password that has been hidden with '-'.
     */
    public String unmaskPassword() {
        if (!hasPasswordPrefix()) {
            return commandTextField.getText();
        }

        int passwordPrefixIndex = commandTextField.getText().indexOf("pass/");
        int spaceAfterPasswordIndex = commandTextField.getText().indexOf(' ', passwordPrefixIndex);

        String prefixesBeforePasswordPrefix = commandTextField.getText().substring(0, passwordPrefixIndex);
        String password = findPassword(passwordPrefixIndex, spaceAfterPasswordIndex);

        String commandText = prefixesBeforePasswordPrefix + PREFIX_PASSWORD.getPrefix();
        commandText = comparePasswordWithTempPassword(password, commandText)
                + getPrefixesAfterPasswordPrefix(spaceAfterPasswordIndex);

        resetTempPassword();

        return commandText;
    }

    public StringBuilder appendMaskedPassword(boolean isHistory, String password) {
        StringBuilder maskedPassword;
        if (isHistory) {
            maskedPassword = storePasswordCharacters(password, password.length() - 1);
            maskedPassword.append(password.charAt(password.length() - 1));
        } else {
            maskedPassword = storePasswordCharacters(password, password.length());
            maskedPassword = unmaskLastCharacter(maskedPassword);
        }
        return maskedPassword;
    }

    public String comparePasswordWithTempPassword(String password, String commandText) {
        if (tempPassword.length() == password.length()) {
            commandText += tempPassword.toString();
        } else if (tempPassword.length() > password.length()) {
            commandText += tempPassword.substring(0, password.length());
        } else {
            commandText += tempPassword.toString() + password.substring(tempPassword.length());
        }
        return commandText;
    }

    public String findPassword(int passwordPrefixIndex, int spaceAfterPasswordIndex) {
        if (spaceAfterPasswordIndex > 0) {
            return commandTextField.getText()
                    .substring(passwordPrefixIndex + 5, spaceAfterPasswordIndex);
        }
        return commandTextField.getText().substring(passwordPrefixIndex + 5);
    }

    public StringBuilder storePasswordCharacters(String password, int passwordLength) {
        StringBuilder maskedPassword = new StringBuilder();

        for (int i = 0; i < passwordLength; i++) {
            if (password.charAt(i) != '-') {
                tempPassword.append(password.charAt(i));
            }
            maskedPassword.append("-");
        }
        return maskedPassword;
    }

    public StringBuilder unmaskLastCharacter(StringBuilder maskedPassword) {
        if (tempPassword.length() <= 0) {
            return maskedPassword;
        }
        char lastPasswordChar = tempPassword.charAt(tempPassword.length() - 1);
        tempPassword.deleteCharAt(tempPassword.length() - 1);
        maskedPassword.replace(maskedPassword.length() - 1,
                maskedPassword.length(), String.valueOf(lastPasswordChar));
        return maskedPassword;
    }

    public String getPrefixesAfterPasswordPrefix(int spaceAfterPasswordIndex) {
        if (spaceAfterPasswordIndex <= 0) {
            return "";
        }
        return commandTextField.getText().substring(spaceAfterPasswordIndex);
    }

    /**
     * Reset the temporary password in the command text field.
     */
    public void resetTempPassword() {
        tempPassword.setLength(0);
    }
}

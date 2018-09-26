package seedu.address.model;

import org.simplejavamail.email.Email;

/**
 * Wraps Email data.
 */
public class EmailModel {

    private Email email;

    public void saveEmail(Email email) {
        this.email = email;
    }

    public Email getEmail() {
        return email;
    }
}

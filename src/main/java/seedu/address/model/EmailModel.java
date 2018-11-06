package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.Recipient;

//@@author EatOrBeEaten
/**
 * Wraps Email data.
 */
public class EmailModel {

    private final String emlExtension = ".eml";

    private Email email;
    private String preview;
    private final Set<String> existingEmails;

    public EmailModel() {
        existingEmails = new HashSet<>();
    }

    public EmailModel(Set<String> emailNamesSet) {
        this();
        requireNonNull(emailNamesSet);
        existingEmails.addAll(emailNamesSet);
    }

    /**
     * Saves email to EmailModel.
     * @param email The email to be saved.
     */
    public void saveEmail(Email email) {
        requireNonNull(email);
        this.email = email;
        savePreview();
    }

    /**
     * Creates preview of email in EmailModel.
     */
    private void savePreview() {
        String previewHeader = "<u>Email Preview</u><br><br>";
        String from = "From: " + email.getFromRecipient().getAddress();
        Iterator<Recipient> itr = email.getRecipients().iterator();
        String to = "To: " + itr.next().getAddress();
        String subject = "Subject: " + email.getSubject();
        while (itr.hasNext()) {
            to = to + ", " + itr.next().getAddress();
        }
        this.preview = previewHeader + from + "<br>" + to + "<br>" + subject + "<br><br>" + email.getHTMLText();
    }

    /**
     * Saves a newly composed email to the EmailModel.
     *
     * @param email the newly composed email.
     */
    public void saveComposedEmail(Email email) {
        requireNonNull(email);
        assert !hasEmail(email.getSubject());
        saveEmail(email);
        addToExistingEmails(email.getSubject());
    }

    public Set<String> getExistingEmails() {
        return Collections.unmodifiableSet(existingEmails);
    }

    public Email getEmail() {
        return email;
    }

    public String getPreview() {
        return preview;
    }

    /**
     * Returns true if an email with the same subject as {@code fileName} exists in the EmailModel.
     */
    public boolean hasEmail(String fileName) {
        requireNonNull(fileName);
        return existingEmails.contains(fileName + emlExtension);
    }

    private void addToExistingEmails(String fileName) {
        existingEmails.add(fileName + emlExtension);
    }

    public void removeFromExistingEmails(String fileName) {
        existingEmails.remove(fileName + emlExtension);
    }

}

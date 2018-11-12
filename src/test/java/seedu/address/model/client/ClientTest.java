package seedu.address.model.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.contact.Contact;
import seedu.address.testutil.ClientBuilder;

public class ClientTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Contact contact = new ClientBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        contact.getTags().remove(0);
    }

    @Test
    public void isSameContact() {
        // same object -> returns true
        assertTrue(ALICE.isSameContact(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameContact(null));

        // different phone and email -> returns false
        Contact editedAlice = new ClientBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameContact(editedAlice));

        // different name -> returns false
        editedAlice = new ClientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameContact(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new ClientBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameContact(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new ClientBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameContact(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameContact(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Contact aliceCopy = new ClientBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different client -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Contact editedAlice = new ClientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ClientBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ClientBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ClientBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}

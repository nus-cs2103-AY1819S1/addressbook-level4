package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.contact.Contact;

/**
 * A utility class containing a list of {@code Contact} objects to be used in tests.
 */
public class TypicalContacts {
    // Manually added
    public static final Contact HOON = new ClientBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Contact IDA = new ClientBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Client's details found in {@code CommandTestUtil}
    public static final Contact AMY = new ClientBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Contact BOB = new ClientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    // A keyword that matches MEIER
    public static final String KEYWORD_MATCHING_MEIER = "n/Meier";

    private static int clientId = 1;
    private static int serviceProviderId = 1;

    public static final Contact ALICE = new ClientBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withId(clientId++)
            .withTags("friends").build();
    public static final Contact BENSON = new ClientBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withId(clientId++)
            .withTags("owesMoney", "friends").build();
    public static final Contact CARL = new ClientBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withId(clientId++).withAddress("wall street")
            .withServices("hotel $200.00", "dress $80.00", "catering $1080.00").build();
    public static final Contact DANIEL = new ClientBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withId(clientId++).withAddress("10th street").withTags("friends")
            .withServices("photographer $1888.80", "hotel $700.00", "ring $888.88").build();
    public static final Contact ELLE = new ClientBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withId(clientId++).withAddress("michegan ave")
            .withServices("photographer $1000.00", "hotel $288.00", "catering $2000.00").build();
    public static final Contact FIONA = new ClientBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withId(clientId++).withAddress("little tokyo")
            .withServices("catering $2800.00").build();
    public static final Contact GEORGE = new ClientBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withId(clientId++).withAddress("4th street")
            .withServices("ring $2000.00").build();

    // ServiceProviders below
    public static final Contact DOMINIC = new VendorBuilder().withName("Dominic Dong")
            .withAddress("123, Jurong East Ave 6, #08-111").withEmail("dominicdong@example.com")
            .withPhone("94311253")
            .withId(serviceProviderId++)
            .withTags("friends")
            .build();
    public static final Contact EEHOOI = new VendorBuilder().withName("Ng Ee Hooi")
            .withAddress("313, Clementi Ave 5, #02-25")
            .withEmail("eehooid@example.com").withPhone("98762432")
            .withId(serviceProviderId++)
            .withTags("owesMoney", "friends")
            .withServices("dress $68.00").build();
    public static final Contact GAN = new VendorBuilder().withName("Gan Chin Yao")
            .withAddress("313, Pioneer Ave 5, #02-25")
            .withEmail("gan@example.com").withPhone("18762432")
            .withId(serviceProviderId++)
            .withServices("ring $588.00", "invitation $100.00").build();
    public static final Contact JIANJIE = new VendorBuilder().withName("Liau Jian Jie")
            .withAddress("444, River Valley Ave 5, #02-25")
            .withEmail("jj@example.com").withPhone("93762432")
            .withId(serviceProviderId++)
            .withServices("photographer $1888.00", "invitation $288.00").build();
    public static final Contact WAILUN = new VendorBuilder().withName("Lim Wai Lun")
            .withAddress("313, Red Hill Ave 5, #02-25")
            .withEmail("wailunoob@example.com").withPhone("98761432")
            .withId(serviceProviderId++)
            .withServices("transport $60.00", "dress $100.00").build();
    public static final Contact SIJI = new VendorBuilder().withName("Dong SiJi")
            .withAddress("313, Buona Vista Ave 5, #02-25")
            .withEmail("dong.siji@example.com").withPhone("91232432")
            .withId(serviceProviderId++)
            .withServices("catering $2800.00", "transport $100.80").build();
    public static final Contact CHINYAO = new VendorBuilder().withName("Chino")
            .withAddress("313, Pasir Ris Ave 5, #02-25")
            .withEmail("chinoyaobi@example.com").withPhone("98711132")
            .withId(serviceProviderId++)
            .withServices("hotel $600.00", "ring $1800.00").build();
    public static final Contact JON = new VendorBuilder().withName("Jon")
            .withAddress("313, Clementi Ave 5, #02-25")
            .withEmail("jon@example.com").withPhone("91352468")
            .withId(serviceProviderId++)
            .withServices("photographer $888.00", "invitation $100.00").build();

    private TypicalContacts() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical contacts.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Contact contact : getTypicalContacts()) {
            ab.addContact(contact);
        }
        return ab;
    }

    public static List<Contact> getTypicalContacts() {
        Client.resetClientId();
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE,
                DOMINIC, EEHOOI, GAN, JIANJIE, WAILUN, SIJI, CHINYAO));
    }
}

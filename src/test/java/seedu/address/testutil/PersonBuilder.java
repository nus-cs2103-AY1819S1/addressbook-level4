package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.carpark.Address;
import seedu.address.model.carpark.Carpark;
import seedu.address.model.carpark.FreeParking;
import seedu.address.model.carpark.NightParking;
import seedu.address.model.carpark.ShortTerm;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Address address;
    private Set<Tag> tags;
    private ShortTerm name;
    private NightParking phone;
    private FreeParking email;

    public PersonBuilder() {
        name = new ShortTerm(DEFAULT_NAME);
        phone = new NightParking(DEFAULT_PHONE);
        email = new FreeParking(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Carpark personToCopy) {
        name = personToCopy.getShortTerm();
        phone = personToCopy.getNightParking();
        email = personToCopy.getFreeParking();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new ShortTerm(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new NightParking(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new FreeParking(email);
        return this;
    }

    //public Carpark build() {
    //    return new Carpark(name, name, name, phone, email, address, tags);
    //}

}

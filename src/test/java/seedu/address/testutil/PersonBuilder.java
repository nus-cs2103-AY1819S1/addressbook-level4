package seedu.address.testutil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.UniqueOccasionList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
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

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private UniqueModuleList moduleList;
    private UniqueOccasionList occasionList;
    private Set<Tag> tags;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        moduleList = new UniqueModuleList();
        occasionList = new UniqueOccasionList();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        moduleList = personToCopy.getModuleList();
        occasionList = personToCopy.getOccasionList();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
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
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the module list using {@code moduleList}.
     */
    public PersonBuilder withModuleList(List<Module> moduleList) {
        for (Module module : moduleList) {
            moduleList.add(module);
        }
        return this;
    }

    /**
     * Sets the module list using {@code moduleList}.
     */
    public PersonBuilder withModuleList(UniqueModuleList moduleList) {
        this.moduleList = moduleList;
        return this;
    }

    /**
     * Sets the occasion list using {@code occasionList}.
     */
    public PersonBuilder withOccasionList(List<Occasion> occasionList) {
        for (Occasion occasion : occasionList) {
            occasionList.add(occasion);
        }
        return this;
    }

    /**
     * Sets the occasion list using {@code occasionList}.
     */
    public PersonBuilder withOccasionList(UniqueOccasionList occasionList) {
        this.occasionList = occasionList;
        return this;
    }

    /**
     * Sets the {@code Person} as having no {@code Email}.
     */
    public PersonBuilder withoutEmail() {
        this.email = new Email();
        return this;
    }

    /**
     * Sets the {@code Person} as having no {@code Address}.
     */
    public PersonBuilder withoutAddress() {
        this.address = new Address();
        return this;
    }

    /**
     * Sets the {@code Person} as having no {@code Phone}.
     */
    public PersonBuilder withoutPhone() {
        this.phone = new Phone();
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, tags, new UniqueModuleList(), new UniqueOccasionList());
    }

}

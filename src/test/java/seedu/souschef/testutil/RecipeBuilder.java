package seedu.souschef.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.souschef.model.recipe.Address;
import seedu.souschef.model.recipe.Email;
import seedu.souschef.model.recipe.Name;
import seedu.souschef.model.recipe.Phone;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.tag.Tag;
import seedu.souschef.model.util.SampleDataUtil;

/**
 * A utility class to help with building Recipe objects.
 */
public class RecipeBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public RecipeBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the RecipeBuilder with the data of {@code recipeToCopy}.
     */
    public RecipeBuilder(Recipe recipeToCopy) {
        name = recipeToCopy.getName();
        phone = recipeToCopy.getPhone();
        email = recipeToCopy.getEmail();
        address = recipeToCopy.getAddress();
        tags = new HashSet<>(recipeToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Recipe} that we are building.
     */
    public RecipeBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Recipe} that we are building.
     */
    public RecipeBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Recipe build() {
        return new Recipe(name, phone, email, address, tags);
    }

}

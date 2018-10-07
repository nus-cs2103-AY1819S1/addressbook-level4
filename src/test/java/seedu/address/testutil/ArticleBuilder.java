package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.article.Address;
import seedu.address.model.article.Article;
import seedu.address.model.article.Email;
import seedu.address.model.article.Name;
import seedu.address.model.article.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Article objects.
 */
public class ArticleBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public ArticleBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ArticleBuilder with the data of {@code articleToCopy}.
     */
    public ArticleBuilder(Article articleToCopy) {
        name = articleToCopy.getName();
        phone = articleToCopy.getPhone();
        email = articleToCopy.getEmail();
        address = articleToCopy.getAddress();
        tags = new HashSet<>(articleToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Article} that we are building.
     */
    public ArticleBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Article} that we are building.
     */
    public ArticleBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Article} that we are building.
     */
    public ArticleBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Article} that we are building.
     */
    public ArticleBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Article} that we are building.
     */
    public ArticleBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Article build() {
        return new Article(name, phone, email, address, tags);
    }

}

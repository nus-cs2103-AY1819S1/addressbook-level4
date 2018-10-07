package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditArticleDescriptor;
import seedu.address.model.article.Address;
import seedu.address.model.article.Article;
import seedu.address.model.article.Email;
import seedu.address.model.article.Name;
import seedu.address.model.article.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditArticleDescriptor objects.
 */
public class EditArticleDescriptorBuilder {

    private EditArticleDescriptor descriptor;

    public EditArticleDescriptorBuilder() {
        descriptor = new EditArticleDescriptor();
    }

    public EditArticleDescriptorBuilder(EditArticleDescriptor descriptor) {
        this.descriptor = new EditArticleDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditArticleDescriptor} with fields containing {@code article}'s details
     */
    public EditArticleDescriptorBuilder(Article article) {
        descriptor = new EditArticleDescriptor();
        descriptor.setName(article.getName());
        descriptor.setPhone(article.getPhone());
        descriptor.setEmail(article.getEmail());
        descriptor.setAddress(article.getAddress());
        descriptor.setTags(article.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditArticleDescriptor} that we are building.
     */
    public EditArticleDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditArticleDescriptor} that we are building.
     */
    public EditArticleDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditArticleDescriptor} that we are building.
     */
    public EditArticleDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditArticleDescriptor} that we are building.
     */
    public EditArticleDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditArticleDescriptor}
     * that we are building.
     */
    public EditArticleDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditArticleDescriptor build() {
        return descriptor;
    }
}

package seedu.learnvocabulary.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.learnvocabulary.logic.commands.EditCommand;
import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.Meaning;
import seedu.learnvocabulary.model.word.Name;
import seedu.learnvocabulary.model.word.Word;

/**
 * A utility class to help with building EditWordDescriptor objects.
 */
public class EditWordDescriptorBuilder {

    private EditCommand.EditWordDescriptor descriptor;

    public EditWordDescriptorBuilder() {
        descriptor = new EditCommand.EditWordDescriptor();
    }

    public EditWordDescriptorBuilder(EditCommand.EditWordDescriptor descriptor) {
        this.descriptor = new EditCommand.EditWordDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditWordDescriptor} with fields containing {@code word}'s details
     */
    public EditWordDescriptorBuilder(Word word) {
        descriptor = new EditCommand.EditWordDescriptor();
        descriptor.setName(word.getName());
        descriptor.setMeaning(word.getMeaning());
        descriptor.setTags(word.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditWordDescriptor} that we are building.
     */
    public EditWordDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Meaning} of the {@code EditWordDescriptor} that we are building.
     */
    public EditWordDescriptorBuilder withMeaning(String meaning) {
        descriptor.setMeaning(new Meaning(meaning));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditWordDescriptor}
     * that we are building.
     */
    public EditWordDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditWordDescriptor build() {
        return descriptor;
    }
}

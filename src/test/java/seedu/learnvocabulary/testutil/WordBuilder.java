package seedu.learnvocabulary.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.util.SampleDataUtil;

import seedu.learnvocabulary.model.word.Address;
import seedu.learnvocabulary.model.word.Meaning;
import seedu.learnvocabulary.model.word.Name;
import seedu.learnvocabulary.model.word.Phone;
import seedu.learnvocabulary.model.word.Word;

/**
 * A utility class to help with building Word objects.
 */
public class WordBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_MEANING = "Test";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Meaning meaning;
    private Phone phone;
    private Address address;
    private Set<Tag> tags;

    public WordBuilder() {
        name = new Name(DEFAULT_NAME);
        meaning = new Meaning(DEFAULT_MEANING);
        phone = new Phone(DEFAULT_PHONE);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the WordBuilder with the data of {@code wordToCopy}.
     */
    public WordBuilder(Word wordToCopy) {
        name = wordToCopy.getName();
        meaning = wordToCopy.getMeaning();
        phone = wordToCopy.getPhone();
        address = wordToCopy.getAddress();
        tags = new HashSet<>(wordToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Word} that we are building.
     */
    public WordBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Meaning} of the {@code Word} that we are building.
     */
    public WordBuilder withMeaning(String meaning) {
        this.meaning = new Meaning(meaning);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Word} that we are building.
     */
    public WordBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Word} that we are building.
     */
    public WordBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Word} that we are building.
     */
    public WordBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }


    public Word build() {
        return new Word(name, meaning, phone, address, tags);
    }

}

package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.Event;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "Tutorial";
    public static final String DEFAULT_ADDRESS = "NUS UTown";
    public static final String DEFAULT_TAG = "friends";

    private Name name;
    private Address address;
    private Person organiser;
    private Set<Tag> tags;

    public EventBuilder() {
        name = new Name(DEFAULT_NAME);
        address = new Address(DEFAULT_ADDRESS);
        organiser = new PersonBuilder().build();
        tags = new HashSet<>();
        tags.add(new Tag(DEFAULT_TAG));
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        address = eventToCopy.getLocation();
        organiser = eventToCopy.getOrganiser();
        tags = new HashSet<>(eventToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Event} that we are building.
     */
    public EventBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Event} that we are building.
     */
    public EventBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code organiser} of the {@code Event} that we are building.
     */
    public EventBuilder withOrganiser(Person person) {
        this.organiser = person;
        return this;
    }

    /**
     * Builds an event.
     */
    public Event build() {
        Event event = new Event(name, address, tags);
        event.setOrganiser(organiser);
        return event;
    }

}

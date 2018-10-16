package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.Awareness;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    private static final String SPACE = " ";

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static Awareness getSampleAwareness() {

        HashMap<String, String> dictionary = new HashMap<String, String>();
        TreeSet<String> allFullPhrases = new TreeSet<String>();

        addSlangToFullPhraseMappings(dictionary, allFullPhrases, "computer science",
                new String[] {"cs", "compsci", "comsci"});

        addSlangToFullPhraseMappings(dictionary, allFullPhrases, "undergraduate",
                new String[] {"ug"});

        addSlangToFullPhraseMappings(dictionary, allFullPhrases, "postgraduate",
                new String[] {"pg"});

        addSlangToFullPhraseMappings(dictionary, allFullPhrases, "teaching assistant",
                new String[] {"ta"});

        addSlangToFullPhraseMappings(dictionary, allFullPhrases, "assistant",
                new String[] {"asst"});

        addSlangToFullPhraseMappings(dictionary, allFullPhrases, "double degree programme",
                new String[] {"ddp"});

        addSlangToFullPhraseMappings(dictionary, allFullPhrases, "machine learning",
                new String[] {"ml"});

        addSlangToFullPhraseMappings(dictionary, allFullPhrases, "singapore", new String[] {"sg"});

        addSlangToFullPhraseMappings(dictionary, allFullPhrases, "financial technology",
                new String[] {"fintech"});

        addFullPhrase(allFullPhrases, "opportunities");

        addFullPhrase(allFullPhrases, "hackathon");

        addFullPhrase(allFullPhrases, "research");

        addFullPhrase(allFullPhrases, "programme");

        // wip add sample Event Name - ResumeEntry mappings
        return new Awareness(dictionary, allFullPhrases);

    }

    /**
     * Updates a given dictionary and set of fullPhrases with additional mappings between a given fullPhrase and its
     * set of slang
     */
    private static void addSlangToFullPhraseMappings(HashMap<String, String> dictionary, TreeSet<String> allFullPhrases,
                                                     String fullPhrase, String[] slangSet) {

        addFullPhrase(allFullPhrases, fullPhrase);
        addSlang(dictionary, fullPhrase, slangSet);

    }

    private static void addFullPhrase(TreeSet<String> allFullPhrases, String fullPhrase) {
        Arrays.stream(fullPhrase.split(SPACE))
              .forEach(spaceDelimitedPhrase -> allFullPhrases.add(spaceDelimitedPhrase));
    }

    private static void addSlang(HashMap<String, String> dictionary, String fullPhrase, String[] slangSet) {
        Arrays.stream(slangSet)
              .forEach(eachSlang -> dictionary.put(eachSlang, fullPhrase));
    }

    private static void makeEventToEntryMappings(TreeMap<String, ResumeEntry> eventToEntryMappings,
                                            String eventName, ResumeEntry resumeEntry) {

        eventToEntryMappings.put(eventName, resumeEntry);
    }

}

package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.EntryBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEntryBook;
import seedu.address.model.awareness.Awareness;
import seedu.address.model.awareness.Dictionary;
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

    public static final ResumeEntry CAROUSELL = new EntryBuilder()
                                                 .withCategory("work")
                                                 .withTitle("Carousell")
                                                 .withDuration("Nov 2017 - Jan 2018")
                                                 .withSubHeader("Intern")
                                                 .withTags("web", "backend")
                                                 .addBulletToDescription("Attached to the backend team")
                                                 .addBulletToDescription("Assisted in migrating database")
                                                 .build();

    public static final ResumeEntry ANDROID_DEV = new EntryBuilder()
                                                   .withCategory("education")
                                                   .withTitle("Android Development Workshop")
                                                   .withDuration("December 2018")
                                                   .withSubHeader("Introduction to Android Development")
                                                   .withTags("course", "android", "mobile")
                                                   .build();

    public static final ResumeEntry MOBILE_GAME = new EntryBuilder()
                                                   .withCategory("projects")
                                                   .withTitle("Mobile Game Development")
                                                   .withDuration("Nov 2018 - Feb 2019")
                                                   .withSubHeader("Multiplayer mobile game project")
                                                   .withTags("game", "app", "unity")
                                                   .build();

    public static final ResumeEntry GRAPHICS_PROJ = new EntryBuilder()
                                                  .withCategory("projects")
                                                  .withTitle("3D Graphics Project")
                                                  .withDuration("Jan 2019 - May 2019")
                                                  .withSubHeader("For CS3241 Computer Graphics")
                                                  .withTags("graphics", "course", "opengl")
                                                  .build();

    public static final ResumeEntry FIRST_CLASS = new EntryBuilder()
                                                    .withCategory("education")
                                                    .withTitle("National University of Singapore")
                                                    .withDuration("2017 - 2021")
                                                    .withSubHeader("Bachelor of Computing in Computer Science")
                                                    .withTags("uni", "course")
                                                    .addBulletToDescription("Attained Dean's list every year")
                                                    .addBulletToDescription("Graduated with First Class Honours")
                                                    .build();


    public static final ResumeEntry MA1101R_TA = new EntryBuilder()
                                                 .withCategory("work")
                                                 .withTitle("Teaching Assistant for MA1101R")
                                                 .withDuration("2010 Semester 1")
                                                 .withSubHeader("Tutored an undergraduate linear algebra module")
                                                 .withTags("teaching", "math")
                                                 .build();

    public static final ResumeEntry COMP_CLUB_EXCO = new EntryBuilder()
                                                     .withCategory("nonacademics")
                                                     .withTitle("Computing Club Executive Committee Member")
                                                     .withDuration("2010 Semester 1 and 2")
                                                     .withSubHeader("Organised budget of the club")
                                                     .withTags("leadership", "management", "entrepreneurship")
                                                     .build();


    public static final ResumeEntry NUS_CS2103T = new EntryBuilder()
                                                  .withCategory("education")
                                                  .withTitle("CS2103T - Software Engineering")
                                                  .withDuration("2011 Semester 2")
                                                  .withSubHeader("Worked on morphing a large legacy codebase into a "
                                                                 + "new product")
                                                  .withTags("software_engineering", "java")
                                                  .build();

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

    public static ReadOnlyEntryBook getSampleEntryBook() {
        EntryBook sampleEb = new EntryBook();
        sampleEb.addEntry(CAROUSELL);
        sampleEb.addEntry(ANDROID_DEV);
        sampleEb.addEntry(MOBILE_GAME);
        sampleEb.addEntry(GRAPHICS_PROJ);
        sampleEb.addEntry(FIRST_CLASS);

        return sampleEb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static Dictionary getSampleDictionary() {
        Dictionary dictionary = new Dictionary();

        try {

            dictionary.registerMultipleMapping(new String[]{"cs", "compsci", "comsci"}, "computer science");

            dictionary.registerMapping("ta", "teaching assistant");
            dictionary.registerMapping("ug", "undergraduate");
            dictionary.registerMapping("pg", "postgraduate");
            dictionary.registerMapping("asst", "assistant");
            dictionary.registerMapping("ddp", "double degree programme");
            dictionary.registerMapping("ml", "machine learning");
            dictionary.registerMapping("sg", "singapore");
            dictionary.registerMapping("fintech", "financial technology");
            dictionary.registerMapping("exco", "executive committee");

            dictionary.registerFullPhrase("opportunities");
            dictionary.registerFullPhrase("hackathon");
            dictionary.registerFullPhrase("research");
            dictionary.registerFullPhrase("programme");
            dictionary.registerFullPhrase("club");


        } catch (IllegalValueException willNotOccur) {

            assert false : "The sample mappings should not cause an IllegalValueException";
        }

        return dictionary;
    }

    public static Awareness getSampleAwareness() {

        Dictionary dictionary = getSampleDictionary();
        TreeMap<String, ResumeEntry> nameToEntryMappings = makeNameToEntryMappings();

        return new Awareness(dictionary, nameToEntryMappings);
    }

    /**
     * Returns a set of sample mappings between EventNames and pre-filled ResumeEntries
     */
    public static TreeMap<String, ResumeEntry> makeNameToEntryMappings() {

        TreeMap<String, ResumeEntry> nameToEntryMappings = new TreeMap<String, ResumeEntry>();
        nameToEntryMappings.put("teaching assistant ma1101r", MA1101R_TA);
        nameToEntryMappings.put("computing club executive committee", COMP_CLUB_EXCO);
        nameToEntryMappings.put("cs2103t", NUS_CS2103T);

        return nameToEntryMappings;
    }

}

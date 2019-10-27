package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.EntryBook;
import seedu.address.model.ReadOnlyEntryBook;
import seedu.address.model.awareness.Awareness;
import seedu.address.model.awareness.Dictionary;
import seedu.address.model.entry.ResumeEntry;
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
            // the sample mappings will not throw this exception
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

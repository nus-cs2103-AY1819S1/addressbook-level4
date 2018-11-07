package seedu.address.model.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.interest.Interest;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Friend;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    private static final Person ALEX = new Person(new Name("Alex Yeoh"), new Phone("87438807"),
        new Email("alexyeoh@example.com"), new Password("password"),
        new Address("Blk 30 Geylang Street 29, #06-40"),
        getInterestSet("study"), getTagSet("SOC"));
    private static final Person BERNICE = new Person(new Name("Bernice Yu"), new Phone("99272758"),
        new Email("berniceyu@example.com"), new Password("password"),
        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
        getInterestSet("play"), getTagSet("SOC", "SDE"));
    private static final Person CHARLOTTE = new Person(new Name("Charlotte Oliveiro"),
        new Phone("93210283"), new Email("charlotte@example.com"),
        new Password("password"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
        getInterestSet("study"), getTagSet("FASS"));
    private static final Person DAVID = new Person(new Name("David Li"),
        new Phone("91031282"), new Email("lidavid@example.com"),
        new Password("password"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
        getInterestSet("play"), getTagSet("FASS"));
    private static final Person IRFAN = new Person(new Name("Irfan Ibrahim"),
        new Phone("92492021"), new Email("irfan@example.com"),
        new Password("password"), new Address("Blk 47 Tampines Street 20, #17-35"),
        getInterestSet("study"), getTagSet("Dentistry"));
    private static final Person ROY = new Person(new Name("Roy Balakrishnan"),
        new Phone("92624417"), new Email("royb@example.com"),
        new Password("password"), new Address("Blk 45 Aljunied Street 85, #11-31"),
        getInterestSet("play"), getTagSet("Business"));
    private static final Person Amony = new Person(new Name("Amony Richman"),
        new Phone("93628942"), new Email("amonr@example.com"),
        new Password("password"), new Address("Blk 452 Hougang Street 83, #21-31"),
        getInterestSet("gaming"), getTagSet("FASS"));
    private static final Person RICHARD = new Person(new Name("Richard Craftman"),
        new Phone("92344417"), new Email("richardc@example.com"),
        new Password("password"), new Address("Blk 453 Pasir Ris Street 25, #03-31"),
        getInterestSet("play"), getTagSet("Dentistry"));
    private static final Person ROYCE = new Person(new Name("Royce Chocolater"),
        new Phone("92621417"), new Email("roycec@example.com"),
        new Password("password"), new Address("Blk 451 Marine Parade Street 81, #11-21"),
        getInterestSet("play"), getTagSet("Business"));
    private static final Person CHICKIN = new Person(new Name("Chickin Riceman"),
        new Phone("92621217"), new Email("chickinr@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("play"), getTagSet("Business"));
    private static final Person HUNGRYH = new Person(new Name("Hungry Heron"),
        new Phone("97759115"), new Email("hungryh@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("Taonurus"), getTagSet("SDE"));
    private static final Person EXCITEDE = new Person(new Name("Excited Eland"),
        new Phone("99155710"), new Email("excitede@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("Jewry"), getTagSet("FOS"));
    private static final Person EBONYM = new Person(new Name("Ebony Mind"),
        new Phone("94153191"), new Email("ebonym@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("lairdie"), getTagSet("Business"));
    private static final Person ELEGANTE = new Person(new Name("Elegant Eland"),
        new Phone("97305592"), new Email("elegante@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("venter"), getTagSet("FOS"));
    private static final Person WORLDO = new Person(new Name("World of"),
        new Phone("92332658"), new Email("worldo@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("unrestingness"), getTagSet("FASS"));
    private static final Person TERRIBLES = new Person(new Name("Terrible Stunt"),
        new Phone("95604504"), new Email("terribles@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("overhomely"), getTagSet("SDE"));
    private static final Person PERFECTP = new Person(new Name("Perfect Porcupine"),
        new Phone("94964297"), new Email("perfectp@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("Silas"), getTagSet("SDE"));
    private static final Person UNINTERESTEDU = new Person(new Name("Uninterested Unicorn"),
        new Phone("90735738"), new Email("uninterestedu@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("unnutritive"), getTagSet("SOC"));
    private static final Person JOLLYJ = new Person(new Name("Jolly Jackal"),
        new Phone("96565866"), new Email("jollyj@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("chuckleheaded"), getTagSet("FOS"));
    private static final Person NBAA = new Person(new Name("NBA Aerobics"),
        new Phone("99399347"), new Email("nbaa@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("overlargely"), getTagSet("FASS"));
    private static final Person LOVELYL = new Person(new Name("Lovely Locust"),
        new Phone("98981324"), new Email("lovelyl@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("technica"), getTagSet("Business"));
    private static final Person MYSTICALH = new Person(new Name("Mystical Hardware"),
        new Phone("97176155"), new Email("mysticalh@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("medianimic"), getTagSet("FASS"));
    private static final Person PARANOIDC = new Person(new Name("Paranoid Cyborg"),
        new Phone("99868306"), new Email("paranoidc@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("nontopographical"), getTagSet("FOS"));
    private static final Person INBREDF = new Person(new Name("Inbred Fantasy"),
        new Phone("95246258"), new Email("inbredf@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("stormer"), getTagSet("SOC"));
    private static final Person NUCLEARL = new Person(new Name("Nuclear Lizard"),
        new Phone("93401992"), new Email("nuclearl@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("antixerophthalmic"), getTagSet("SOC"));
    private static final Person LAIRO = new Person(new Name("Lair of"),
        new Phone("97399722"), new Email("lairo@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("snarler"), getTagSet("Business"));
    private static final Person LIVELYL = new Person(new Name("Lively Lark"),
        new Phone("91390643"), new Email("livelyl@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("peastone"), getTagSet("Business"));
    private static final Person MYSTERIOUSM = new Person(new Name("Mysterious Meerkat"),
        new Phone("97256694"), new Email("mysteriousm@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("requit"), getTagSet("NUH"));
    private static final Person THOUGHTFULT = new Person(new Name("Thoughtful Tortoise"),
        new Phone("92765760"), new Email("thoughtfult@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("fairy"), getTagSet("SDE"));
    private static final Person SMOGGYS = new Person(new Name("Smoggy Sardine"),
        new Phone("91396582"), new Email("smoggys@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("cotyliform"), getTagSet("NUH"));
    private static final Person NUDISTT = new Person(new Name("Nudist Thunder"),
        new Phone("96085418"), new Email("nudistt@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("leucocism"), getTagSet("FASS"));
    private static final Person EMBARRASSEDE = new Person(new Name("Embarrassed Earthworm"),
        new Phone("99711359"), new Email("embarrassede@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("dermatography"), getTagSet("SOC"));
    private static final Person ANXIOUSA = new Person(new Name("Anxious Angelfish"),
        new Phone("99956526"), new Email("anxiousa@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("foiler"), getTagSet("FASS"));
    private static final Person EASYE = new Person(new Name("Easy Elephant"),
        new Phone("98423760"), new Email("easye@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("relatively"), getTagSet("NUH"));
    private static final Person DETERMINEDD = new Person(new Name("Determined Dolphin"),
        new Phone("91294071"), new Email("determinedd@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("sclerodactyly"), getTagSet("NUH"));
    private static final Person DARKD = new Person(new Name("Dark Dove"),
        new Phone("99668976"), new Email("darkd@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("ornithorhynchous"), getTagSet("Business"));
    private static final Person THEC = new Person(new Name("The Castle"),
        new Phone("96856037"), new Email("thec@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("Laserpitium"), getTagSet("Business"));
    private static final Person TENDERT = new Person(new Name("Tender Tapir"),
        new Phone("98929822"), new Email("tendert@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("tocusso"), getTagSet("FOS"));
    private static final Person SEARCHF = new Person(new Name("Search for"),
        new Phone("96364527"), new Email("searchf@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("cuproiodargyrite"), getTagSet("SOC"));
    private static final Person ADVENTUROUSA = new Person(new Name("Adventurous Antelope"),
        new Phone("99686963"), new Email("adventurousa@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("asepticize"), getTagSet("FOS"));
    private static final Person PAGANS = new Person(new Name("Pagan Speed"),
        new Phone("94397841"), new Email("pagans@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("flench"), getTagSet("SDE"));
    private static final Person FIERCEF = new Person(new Name("Fierce Flatworm"),
        new Phone("92113758"), new Email("fiercef@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("bureaucratism"), getTagSet("FASS"));
    private static final Person NEOB = new Person(new Name("Neo Bimbo"),
        new Phone("96570620"), new Email("neob@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("chlorosis"), getTagSet("NUH"));
    private static final Person MISTYM = new Person(new Name("Misty Markhor"),
        new Phone("92773911"), new Email("mistym@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("sweetfish"), getTagSet("FASS"));
    private static final Person THOUGHTLESST = new Person(new Name("Thoughtless Termite"),
        new Phone("92549884"), new Email("thoughtlesst@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("siphonorhine"), getTagSet("Business"));
    private static final Person CALMC = new Person(new Name("Calm Crocodile"),
        new Phone("94510354"), new Email("calmc@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("Illano"), getTagSet("NUH"));
    private static final Person ROCK = new Person(new Name("Rock n"),
        new Phone("99158587"), new Email("rock'@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("coccygalgia"), getTagSet("FASS"));
    private static final Person GOTHICS = new Person(new Name("Gothic Sailor"),
        new Phone("99405998"), new Email("gothics@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("cowman"), getTagSet("FASS"));
    private static final Person DULLD = new Person(new Name("Dull Dormouse"),
        new Phone("96832734"), new Email("dulld@example.com"),
        new Password("password"), new Address("National University of Singapore 21 Lower Kent Ridge Road"),
        getInterestSet("concausal"), getTagSet("NUH"));

    public static Person[] getSamplePersons() {
        return new Person[] {ALEX, BERNICE, CHARLOTTE, DAVID, IRFAN, ROY, Amony, RICHARD, ROYCE, CHICKIN,
            HUNGRYH, EXCITEDE, EBONYM, ELEGANTE, WORLDO, TERRIBLES, PERFECTP, UNINTERESTEDU, JOLLYJ, NBAA,
            LOVELYL, MYSTICALH, PARANOIDC, INBREDF, NUCLEARL, LAIRO, LIVELYL, MYSTERIOUSM, THOUGHTFULT,
            SMOGGYS, NUDISTT, EMBARRASSEDE, ANXIOUSA, EASYE, DETERMINEDD, DARKD, THEC, TENDERT, SEARCHF,
            ADVENTUROUSA, PAGANS, FIERCEF, NEOB, MISTYM, THOUGHTLESST, CALMC, ROCK, GOTHICS, DULLD};
    }

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(new EventName("CS2103 weekly meeting"), new Address("NUS SoC Canteen"), getTagSet("Urgent"),
                    LocalDate.of(2018, 12, 12), LocalTime.of(12, 30),
                    LocalTime.of(13, 00), ALEX),
            new Event(new EventName("CS2103 weekly meeting"), new Address("NUS SoC Canteen"), getTagSet(),
                    LocalDate.of(2018, 12, 19), LocalTime.of(12, 30),
                    LocalTime.of(13, 00), ALEX),
            new Event(new EventName("CS2100 discussion"), new Address("NUS Deck"), getTagSet(),
                    LocalDate.of(2018, 11, 12), LocalTime.of(17, 30),
                    LocalTime.of(18, 30), DAVID),
            new Event(new EventName("Week 12 karaoke session"), new Address("Clementi MRT"), getTagSet("Public"),
                    LocalDate.of(2018, 11, 8), LocalTime.of(20, 30),
                    LocalTime.of(22, 30), ROY),
            new Event(new EventName("Linguistics Reading Group"), new Address("NUS Central Library"), getTagSet(),
                    LocalDate.of(2018, 11, 12), LocalTime.of(17, 30),
                    LocalTime.of(18, 30), CHARLOTTE),
            new Event(new EventName("Linguistics Reading Group"), new Address("NUS Central Library"), getTagSet(),
                    LocalDate.of(2018, 11, 19), LocalTime.of(17, 30),
                    LocalTime.of(18, 30), CHARLOTTE),
            new Event(new EventName("Bernice's BD party"), new Address("Cinnamon College"), getTagSet(),
                    LocalDate.of(2018, 12, 1), LocalTime.of(22, 00),
                    LocalTime.of(23, 30), DAVID),
            new Event(new EventName("11th floor party"), new Address("Cinnamon College"), getTagSet(),
                    LocalDate.of(2018, 11, 15), LocalTime.of(22, 00),
                    LocalTime.of(23, 00), IRFAN),
            new Event(new EventName("Hackathon"), new Address("SoC LT19"), getTagSet(),
                    LocalDate.of(2018, 11, 27), LocalTime.of(10, 00),
                    LocalTime.of(22, 30), BERNICE),
            new Event(new EventName("Lunch"), new Address("NUS Techno"), getTagSet(),
                    LocalDate.of(2018, 12, 1), LocalTime.of(12, 30),
                    LocalTime.of(13, 00), IRFAN),
            new Event(new EventName("EOY Christmas Party"), new Address("Cinnamon College"), getTagSet(),
                    LocalDate.of(2018, 12, 22), LocalTime.of(19, 30),
                    LocalTime.of(22, 00), IRFAN)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Event sampleEvent : getSampleEvents()) {
            sampleAb.addEvent(sampleEvent);
        }
        return sampleAb;
    }

    /**
     * Returns a interest set containing the list of strings given.
     */
    public static Set<Interest> getInterestSet(String... strings) {
        return Arrays.stream(strings)
            .map(Interest::new)
            .collect(Collectors.toSet());
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

    /**
     * Returns a friend set containing the list of strings given.
     */
    public static Set<Friend> getFriendSet(String... strings) {
        return Arrays.stream(strings)
            .map(Friend::new)
            .collect(Collectors.toSet());
    }

}

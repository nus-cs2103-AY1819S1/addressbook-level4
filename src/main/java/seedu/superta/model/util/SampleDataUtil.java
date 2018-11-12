package seedu.superta.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.superta.model.ReadOnlySuperTaClient;
import seedu.superta.model.SuperTaClient;
import seedu.superta.model.student.Email;
import seedu.superta.model.student.Feedback;
import seedu.superta.model.student.Name;
import seedu.superta.model.student.Phone;
import seedu.superta.model.student.Student;
import seedu.superta.model.student.StudentId;
import seedu.superta.model.tag.Tag;
import seedu.superta.storage.XmlSuperTaClientStorage;

/**
 * Contains utility methods for populating {@code SuperTaClient} with sample data.
 */
public class SampleDataUtil {

    public static final Feedback EMPTY_FEEDBACK = new Feedback("");

    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new StudentId("A0123456T"),
                getTagSet("friends"),
                getFeedbackList("")),
            new Student(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new StudentId("A0144582N"),
                getTagSet("colleagues", "friends"),
                getFeedbackList("")),
            new Student(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new StudentId("A0128314Y"),
                getTagSet("neighbours"),
                getFeedbackList("")),
            new Student(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new StudentId("A0122301Y"),
                getTagSet("family"),
                getFeedbackList("")),
            new Student(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new StudentId("A0182324E"),
                getTagSet("classmates"),
                getFeedbackList("")),
            new Student(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new StudentId("A0139410Y"),
                getTagSet("colleagues"),
                getFeedbackList("")),
        };
    }

    public static ReadOnlySuperTaClient getSampleAddressBook() {
        ClassLoader classLoader = ReadOnlySuperTaClient.class.getClassLoader();
        try {
            Optional<ReadOnlySuperTaClient> client = XmlSuperTaClientStorage.readClientFromStream(
                    classLoader.getResourceAsStream("seed.xml"));
            if (client.isPresent()) {
                return client.get();
            }
        } catch (Exception e) {
            // retrieve data normally
        }

        SuperTaClient sampleAb = new SuperTaClient();
        for (Student sampleStudent : getSampleStudents()) {
            sampleAb.addStudent(sampleStudent);
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

    /**
     * Returns a feedback list containing the list of strings given.
     */
    public static List<Feedback> getFeedbackList(String... strings) {
        return Arrays.stream(strings)
                .map(Feedback::new)
                .collect(Collectors.toList());
    }

}

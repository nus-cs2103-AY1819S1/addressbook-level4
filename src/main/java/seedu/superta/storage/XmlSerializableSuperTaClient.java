package seedu.superta.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.superta.commons.exceptions.IllegalValueException;
import seedu.superta.model.ReadOnlySuperTaClient;
import seedu.superta.model.SuperTaClient;
import seedu.superta.model.student.Student;
import seedu.superta.model.tutorialgroup.TutorialGroup;

/**
 * An Immutable SuperTaClient that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableSuperTaClient {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate student(s).";

    @XmlElement
    private List<XmlAdaptedStudent> students;

    @XmlElement
    private List<XmlAdaptedTutorialGroup> tutorialGroups;

    /**
     * Creates an empty XmlSerializableSuperTaClient.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableSuperTaClient() {
        students = new ArrayList<>();
        tutorialGroups = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableSuperTaClient(ReadOnlySuperTaClient src) {
        this();
        students.addAll(src.getStudentList().stream().map(XmlAdaptedStudent::new).collect(Collectors.toList()));
        tutorialGroups.addAll(src.getTutorialGroupMap().values().stream().map(XmlAdaptedTutorialGroup::new).collect
            (Collectors.toList()));
    }

    /**
     * Converts this SuperTA client into the model's {@code SuperTaClient} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedStudent}.
     */
    public SuperTaClient toModelType() throws IllegalValueException {
        SuperTaClient superTaClient = new SuperTaClient();
        for (XmlAdaptedStudent p : students) {
            Student student = p.toModelType();
            if (superTaClient.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            superTaClient.addStudent(student);
        }
        for (XmlAdaptedTutorialGroup xmlTg : tutorialGroups) {
            TutorialGroup tg = xmlTg.toModelType(superTaClient);
            superTaClient.addTutorialGroup(tg);
        }
        return superTaClient;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableSuperTaClient)) {
            return false;
        }
        return students.equals(((XmlSerializableSuperTaClient) other).students);
    }
}

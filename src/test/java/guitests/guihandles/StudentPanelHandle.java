package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

import seedu.address.model.person.Person;

/**
 * Provides a handle to a student display in the student panel
 */
public class StudentPanelHandle extends NodeHandle<Node> {
    private static final String NAME_FIELD_ID = "#nameLabel";
    private static final String PHONE_FIELD_ID = "#phoneLabel";
    private static final String EMAIL_FIELD_ID = "#emailLabel";
    private static final String ADDRESS_FIELD_ID = "#addressLabel";
    private static final String EDUCATION_FIELD_ID = "#educationLabel";
    private static final String FEE_FIELD_ID = "#feeLabel";
    private static final String GRADES_FIELD_ID = "#gradesLabel";
    private static final String TIME_FIELD_ID = "#timeLabel";

    private final Label nameLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label addressLabel;
    private final Label educationLabel;
    private final Label feeLabel;
    private final Label gradesLabel;
    private final Label timeLabel;

    public StudentPanelHandle(Node studentNode) {
        super(studentNode);

        nameLabel = getChildNode(NAME_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);
        educationLabel = getChildNode(EDUCATION_FIELD_ID);
        feeLabel = getChildNode(FEE_FIELD_ID);
        gradesLabel = getChildNode(GRADES_FIELD_ID);
        timeLabel = getChildNode(TIME_FIELD_ID);
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getEducation() {
        return educationLabel.getText();
    }

    public String getFee() {
        return feeLabel.getText();
    }

    public String getGrades() {
        return gradesLabel.getText();
    }

    public String getTime() {
        return timeLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code person}.
     */
    public boolean equals(Person person) {
        String[] arr = person.getGrades().keySet().toArray(new String[person.getGrades().size()]);
        String result = "";
        String grade = "";
        for (int i = 0; i < arr.length; i++) {
            grade = person.getGrades().get(arr[i]).value;
            result += arr[i] + " " + grade + "\n";
        }
        return getName().equals(person.getName().fullName)
                && getPhone().equals(person.getPhone().value)
                && getEmail().equals(person.getEmail().value)
                && getAddress().equals(person.getAddress().value)
                && getEducation().equals(person.getEducation().toString())
                && getFee().equals(person.getFees().value)
                && getGrades().equals(result)
                && getTime().equals(person.getTime().toString());
    }
}

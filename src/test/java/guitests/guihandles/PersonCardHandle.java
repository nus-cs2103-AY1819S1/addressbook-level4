package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.person.Person;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String SALARY_FIELD_ID = "#salary";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label salaryLabel;
    private final Label phoneLabel;
    private final Label emailLabel;

    public PersonCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        salaryLabel = getChildNode(SALARY_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getSalary() {
        return salaryLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }


    /**
     * Returns true if this handle contains {@code person}.
     */
    public boolean equals(Person person) {
        return getName().equals(person.getName().fullName)
                && getSalary().equals(person.getSalary().value)
                && getPhone().equals(person.getPhone().value)
                && getEmail().equals(person.getEmail().value);
    }
}

package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalSsenisub;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class SsenisubTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Ssenisub ssenisub = new Ssenisub();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), ssenisub.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ssenisub.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlySsenisub_replacesData() {
        Ssenisub newData = getTypicalSsenisub();
        ssenisub.resetData(newData);
        assertEquals(newData, ssenisub);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        SsenisubStub newData = new SsenisubStub(newPersons);

        thrown.expect(DuplicatePersonException.class);
        ssenisub.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        ssenisub.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInSsenisub_returnsFalse() {
        assertFalse(ssenisub.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInSsenisub_returnsTrue() {
        ssenisub.addPerson(ALICE);
        assertTrue(ssenisub.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInSsenisub_returnsTrue() {
        ssenisub.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(ssenisub.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        ssenisub.getPersonList().remove(0);
    }

    /**
     * A stub ReadOnlySsenisub whose persons list can violate interface constraints.
     */
    private static class SsenisubStub implements ReadOnlySsenisub {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        SsenisubStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}

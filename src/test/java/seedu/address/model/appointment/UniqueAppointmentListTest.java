package seedu.address.model.appointment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.PersonNotFoundException;

public class UniqueAppointmentListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public final UniqueAppointmentList uniqueAppointmentList = new UniqueAppointmentList();

    @Test
    public void contains_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAppointmentList.contains(null);
    }

    @Test
    public void contains_appointmentNotInList_returnsFalse() {
        assertFalse(uniqueAppointmentList.contains());
    }

    @Test
    public void contains_appointmentInList_returnsTrue() {
        uniqueAppointmentList.add(); //TODO add appt
        assertTrue(uniqueAppointmentList.contains());
    }

    @Test
    public void contains_appointmentWithSameIdentityFieldsInList_returnsTrue() {
        uniqueAppointmentList.add(); //TODO add appt
        Appointment editedAppt = new Appointment();
        assertTrue(uniqueAppointmentList.contains(editedAppt));
    }

    @Test
    public void add_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAppointmentList.add(null);
    }

    @Test
    public void add_duplicateAppointment_throwsDuplicateAppointmentException() {
        uniqueAppointmentList.add(); //TODO add appt
        thrown.expect(DuplicateAppointmentException.class);
        uniqueAppointmentList.add(); //TODO add appt
    }

    @Test
    public void setAppointment_nullTargetAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAppointmentList.setAppointment(null, ); //TODO add appt
    }

    @Test
    public void setAppointment_nullEditedAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAppointmentList.setAppointment(, null); //TODO add appt
    }

    @Test
    public void setAppointment_targetAppointmentNotInList_throwsAppointmentNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        uniqueAppointmentList.add(); //TODO add appt
    }

    @Test
    public void setAppointment_editedAppointmentIsSameAppointment_success() {
        uniqueAppointmentList.add(); //TODO add appt
        uniqueAppointmentList.setAppointment(); //TODO add appt
        UniquePersonList expectedUniqueAppointmentList = new UniquePersonList();
        expectedUniqueAppointmentList.add(); //TODO add appt
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointment_editedAppointmentHasSameIdentity_success() {
        uniqueAppointmentList.add(); //TODO add appt
        Appointment editedAppt = new Appointment();
        uniqueAppointmentList.setAppointment(); //TODO add appt
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(); //TODO add appt
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointment_editedAppointmentHasDifferentIdentity_success() {
        uniqueAppointmentList.add(); //TODO add appt
        uniqueAppointmentList.setAppointment(); //TODO add appt
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(); //TODO add appt
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointment_editedAppointmentHasNonUniqueIdentity_throwsDuplicateAppointmentException() {
        uniqueAppointmentList.add(); //TODO add appt
        uniqueAppointmentList.add(); //TODO add appt
        thrown.expect(DuplicateAppointmentException.class);
        uniqueAppointmentList.setAppointment(); //TODO add appt
    }

    @Test
    public void remove_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAppointmentList.remove(null);
    }

    @Test
    public void remove_appointmentDoesNotExist_throwsAppointmentNotFoundException() {
        thrown.expect(AppointmentNotFoundException.class);
        uniqueAppointmentList.remove(); //TODO add appt
    }

    @Test
    public void remove_existingAppointment_removesAppointment() {
        uniqueAppointmentList.add(); //TODO add appt
        uniqueAppointmentList.remove(); //TODO add appt
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointments_nullUniqueAppointmentList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAppointmentList.setAppointments((UniqueAppointmentList) null);
    }

    @Test
    public void setAppointments_uniqueAppointmentList_replacesOwnListWithProvidedUniqueAppointmentList() {
        uniqueAppointmentList.add(); //TODO add appt
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        uniqueAppointmentList.add(); //TODO add appt
        uniqueAppointmentList.setAppointments(expectedUniqueAppointmentList);
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointments_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAppointmentList.setAppointments((List<Appointment>) null);
    }

    @Test
    public void setAppointments_list_replacesOwnListWithProvidedList() {
        uniqueAppointmentList.add(); //TODO add appt
        List<Appointment> appointmentList = Collections.singletonList(); //TODO add appt
        uniqueAppointmentList.setAppointments(appointmentList);
        UniquePersonList expectedUniqueAppointmentList = new UniquePersonList();
        expectedUniqueAppointmentList.add(); //TODO add appt
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointments_listWithDuplicateAppointments_throwsDuplicateAppointmentException() {
        List<Appointment> listWithDuplicateAppointment = Arrays.asList(); //TODO add dup appt
        thrown.expect(DuplicateAppointmentException.class);
        uniqueAppointmentList.setAppointments(listWithDuplicateAppointment);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueAppointmentList.asUnmodifiableObservableList().remove(0);
    }
}

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
import seedu.address.testutil.AppointmentBuilder;

public class UniqueAppointmentListTest {
    private static final Appointment APPOINTMENT_ONE = new AppointmentBuilder().build();
    private static final Appointment APPOINTMENT_TWO = new AppointmentBuilder().build();
    private static final Appointment APPOINTMENT_THREE = new AppointmentBuilder().withAppointmentId(12345).build();

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final UniqueAppointmentList uniqueAppointmentList = new UniqueAppointmentList();

    @Test
    public void contains_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAppointmentList.contains(null);
    }

    @Test
    public void contains_appointmentNotInList_returnsFalse() {
        assertFalse(uniqueAppointmentList.contains(APPOINTMENT_ONE));
    }

    @Test
    public void contains_appointmentInList_returnsTrue() {
        uniqueAppointmentList.add(APPOINTMENT_ONE);
        assertTrue(uniqueAppointmentList.contains(APPOINTMENT_ONE));
    }

    @Test
    public void contains_appointmentWithSameAppointmentId_returnsTrue() {
        uniqueAppointmentList.add(APPOINTMENT_ONE);
        Appointment editedAppointmentOne =
                new AppointmentBuilder(APPOINTMENT_ONE).withDateTime("2018-10-30 21:00").build();
        assertTrue(uniqueAppointmentList.contains(editedAppointmentOne));
    }

    @Test
    public void add_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAppointmentList.add(null);
    }

    @Test
    public void add_duplicateAppointment_throwsDuplicateAppointmentException() {
        uniqueAppointmentList.add(APPOINTMENT_ONE);
        thrown.expect(DuplicateAppointmentException.class);
        uniqueAppointmentList.add(APPOINTMENT_ONE);
    }

    @Test
    public void setAppointment_nullTargetAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAppointmentList.setAppointment(null, APPOINTMENT_ONE);
    }

    @Test
    public void setAppointment_nullEditedAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAppointmentList.setAppointment(APPOINTMENT_ONE, null);
    }

    @Test
    public void setAppointment_targetAppointmentNotInList_throwsAppointmentNotFoundException() {
        thrown.expect(AppointmentNotFoundException.class);
        uniqueAppointmentList.setAppointment(APPOINTMENT_ONE, APPOINTMENT_ONE);
    }

    @Test
    public void setAppointment_editedAppointmentIsSameAppointment_success() {
        uniqueAppointmentList.add(APPOINTMENT_ONE);
        uniqueAppointmentList.setAppointment(APPOINTMENT_ONE, APPOINTMENT_ONE);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(APPOINTMENT_ONE);
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void remove_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAppointmentList.remove(null);
    }

    @Test
    public void remove_appointmentDoesNotExist_throwsAppointmentNotFoundException() {
        thrown.expect(AppointmentNotFoundException.class);
        uniqueAppointmentList.remove(APPOINTMENT_ONE);
    }

    @Test
    public void remove_existingAppointment_removesAppointment() {
        uniqueAppointmentList.add(APPOINTMENT_ONE);
        uniqueAppointmentList.remove(APPOINTMENT_ONE);
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
        uniqueAppointmentList.add(APPOINTMENT_ONE);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(APPOINTMENT_THREE);
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
        uniqueAppointmentList.add(APPOINTMENT_ONE);
        List<Appointment> appointmentList = Collections.singletonList(APPOINTMENT_THREE);
        uniqueAppointmentList.setAppointments(appointmentList);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(APPOINTMENT_THREE);
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointments_listWithDuplicateAppointments_throwsDuplicateAppointmentException() {
        List<Appointment> listWithDuplicateAppointments = Arrays.asList(APPOINTMENT_ONE, APPOINTMENT_ONE);
        thrown.expect(DuplicateAppointmentException.class);
        uniqueAppointmentList.setAppointments(listWithDuplicateAppointments);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueAppointmentList.asUnmodifiableObservableList().remove(0);
    }
}

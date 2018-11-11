package seedu.clinicio.model.appointment;

//@@gingivitiss

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.clinicio.testutil.TypicalPersons.ALEX_APPT;
import static seedu.clinicio.testutil.TypicalPersons.BRYAN_APPT;
import static seedu.clinicio.testutil.TypicalPersons.CARL_APPT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.clinicio.model.appointment.exceptions.AppointmentClashException;
import seedu.clinicio.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.clinicio.model.appointment.exceptions.DuplicateAppointmentException;

import seedu.clinicio.testutil.AppointmentBuilder;

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
        assertFalse(uniqueAppointmentList.contains(ALEX_APPT));
    }

    @Test
    public void contains_appointmentInList_returnsTrue() {
        uniqueAppointmentList.add(ALEX_APPT);
        assertTrue(uniqueAppointmentList.contains(ALEX_APPT));
    }

    @Test
    public void contains_appointmentWithSameSimilarFieldsInList_returnsFalse() {
        uniqueAppointmentList.add(ALEX_APPT);
        Appointment editedAppt = new AppointmentBuilder(ALEX_APPT).withDate(4, 10, 2018).build();
        assertFalse(uniqueAppointmentList.contains(editedAppt));
    }

    @Test
    public void add_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAppointmentList.add(null);
    }

    @Test
    public void add_duplicateAppointment_throwsDuplicateAppointmentException() {
        uniqueAppointmentList.add(BRYAN_APPT);
        thrown.expect(DuplicateAppointmentException.class);
        uniqueAppointmentList.add(BRYAN_APPT);
    }

    @Test
    public void add_clashingAppointment_throwsAppointmentClashException() {
        uniqueAppointmentList.add(ALEX_APPT);
        Appointment clashingAppt = new AppointmentBuilder(BRYAN_APPT).withTime(13, 00).build();
        thrown.expect(AppointmentClashException.class);
        uniqueAppointmentList.add(clashingAppt);
    }

    @Test
    public void setAppointment_nullTargetAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAppointmentList.setAppointment(null, CARL_APPT);
    }

    @Test
    public void setAppointment_nullEditedAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAppointmentList.setAppointment(CARL_APPT, null);
    }

    @Test
    public void setAppointment_targetAppointmentNotInList_throwsAppointmentNotFoundException() {
        thrown.expect(AppointmentNotFoundException.class);
        uniqueAppointmentList.setAppointment(CARL_APPT, CARL_APPT);
    }

    @Test
    public void setAppointment_editedAppointmentHasDifferentIdentity_success() {
        uniqueAppointmentList.add(ALEX_APPT);
        uniqueAppointmentList.setAppointment(ALEX_APPT, BRYAN_APPT);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(BRYAN_APPT);
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointment_editedAppointmentHasNonUniqueIdentity_throwsDuplicateAppointmentException() {
        uniqueAppointmentList.add(ALEX_APPT);
        uniqueAppointmentList.add(CARL_APPT);
        thrown.expect(DuplicateAppointmentException.class);
        uniqueAppointmentList.setAppointment(CARL_APPT, ALEX_APPT);
    }

    @Test
    public void setAppointment_editedAppointmentHasClash1_throwsAppointmentClashException() {
        uniqueAppointmentList.add(ALEX_APPT);
        uniqueAppointmentList.add(BRYAN_APPT);
        Appointment editedAppt = new AppointmentBuilder(BRYAN_APPT)
                .withTime(13, 00).build();
        thrown.expect(AppointmentClashException.class);
        uniqueAppointmentList.setAppointment(BRYAN_APPT, editedAppt);
    }

    @Test
    public void setAppointment_editedAppointmentHasClash2_throwsAppointmentClashException() {
        uniqueAppointmentList.add(BRYAN_APPT);
        thrown.expect(AppointmentClashException.class);
        uniqueAppointmentList.add(CARL_APPT);
    }

    @Test
    public void cancelAppointment_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueAppointmentList.cancelAppointment(null);
    }

    @Test
    public void cancelAppointment_appointmentDoesNotExist_throwsAppointmentNotFoundException() {
        thrown.expect(AppointmentNotFoundException.class);
        uniqueAppointmentList.cancelAppointment(CARL_APPT);
    }

    @Test
    public void cancelAppointment_existingAppointment_cancelsAppointment() {
        uniqueAppointmentList.add(ALEX_APPT);
        uniqueAppointmentList.cancelAppointment(ALEX_APPT);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        Appointment cancelledAppt = ALEX_APPT;
        cancelledAppt.cancelAppointment();
        expectedUniqueAppointmentList.add(cancelledAppt);
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
        uniqueAppointmentList.remove(BRYAN_APPT);
    }

    @Test
    public void remove_existingAppointment_removesAppointment() {
        uniqueAppointmentList.add(ALEX_APPT);
        uniqueAppointmentList.remove(ALEX_APPT);
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
        uniqueAppointmentList.add(CARL_APPT);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        uniqueAppointmentList.add(ALEX_APPT);
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
        uniqueAppointmentList.add(ALEX_APPT);
        List<Appointment> appointmentList = Collections.singletonList(BRYAN_APPT);
        uniqueAppointmentList.setAppointments(appointmentList);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(BRYAN_APPT);
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointments_listWithDuplicateAppointments_throwsDuplicateAppointmentException() {
        List<Appointment> listWithDuplicateAppointment = Arrays.asList(ALEX_APPT, ALEX_APPT);
        thrown.expect(DuplicateAppointmentException.class);
        uniqueAppointmentList.setAppointments(listWithDuplicateAppointment);
    }

    @Test
    public void setAppointments_listWithClashingAppointments_throwsAppointmentClashException() {
        Appointment clashingAppt = new AppointmentBuilder(CARL_APPT).withTime(13, 30).build();
        List<Appointment> listWithClashingAppointment = Arrays.asList(ALEX_APPT, clashingAppt);
        thrown.expect(AppointmentClashException.class);
        uniqueAppointmentList.setAppointments(listWithClashingAppointment);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueAppointmentList.asUnmodifiableObservableList().remove(0);
    }
}

package seedu.address.storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.leaveapplication.Description;
import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.leaveapplication.LeaveId;
import seedu.address.model.leaveapplication.LeaveStatus;
import seedu.address.model.permission.PermissionSet;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * JAXB-friendly version of the LeaveApplication.
 */
public class XmlAdaptedLeaveApplication {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "LeaveApplication's %s field is missing!";

    @XmlElement(required = true)
    private Integer id;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String status;
    @XmlElement(required = true)
    private Set<String> dates = new HashSet<>();

    // Employee identity fields
    @XmlElement(required = true)
    private String employeeName;
    @XmlElement(required = true)
    private String employeeEmail;
    @XmlElement(required = true)
    private String employeePhone;

    /**
     * Constructs an XmlAdaptedLeaveApplication.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLeaveApplication() {
    }

    /**
     * Constructs an {@code XmlAdaptedLeaveApplication} with the given leave application details.
     */
    public XmlAdaptedLeaveApplication(Integer id, String description, String status, String employeeName,
                                      String employeeEmail, String employeePhone, Set<Date> dates) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeePhone = employeePhone;
        this.dates = dates.stream()
                     .map(Date::toString)
                     .collect(Collectors.toSet());
    }

    /**
     * Converts a given LeaveApplication into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedLeaveApplication
     */
    public XmlAdaptedLeaveApplication(LeaveApplication source) {
        id = source.getId().value;
        description = source.getDescription().value;
        status = source.getLeaveStatus().value.toString();
        dates = source.getDates().stream()
                .map(Date::toString)
                .collect(Collectors.toSet());
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's LeaveApplication object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted leave application
     */
    public LeaveApplication toModelType() throws IllegalValueException {
        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, LeaveId.class.getSimpleName()));
        }
        if (!LeaveId.isValidLeaveId(id)) {
            throw new IllegalValueException(LeaveId.MESSAGE_LEAVEID_CONSTRAINTS);
        }
        final LeaveId modelId = new LeaveId(id);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LeaveStatus.class.getSimpleName()));
        }
        if (!LeaveStatus.isValidStatus(status)) {
            throw new IllegalValueException(LeaveStatus.MESSAGE_STATUS_CONSTRAINTS);
        }
        final LeaveStatus modelStatus = new LeaveStatus(status);

        if (employeeName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(employeeName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(employeeName);

        if (employeeEmail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(employeeEmail)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(employeeEmail);

        if (employeePhone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(employeePhone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(employeePhone);

        final Set<Date> modelDates = new HashSet<>();
        for (String dateString : dates) {
            Date parsedDate;
            try {
                parsedDate = DateUtil.format(dateString);
            } catch (ParseException pe) {
                throw new IllegalValueException(pe.getMessage());
            }
            modelDates.add(parsedDate);
        }
        return new LeaveApplication(modelId, modelDescription, modelStatus, modelDates);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedLeaveApplication)) {
            return false;
        }

        XmlAdaptedLeaveApplication otherLeave = (XmlAdaptedLeaveApplication) other;
        return Objects.equals(id, otherLeave.id)
                && Objects.equals(description, otherLeave.description)
                && Objects.equals(status, otherLeave.status)
                && Objects.equals(dates, otherLeave.dates);
    }
}

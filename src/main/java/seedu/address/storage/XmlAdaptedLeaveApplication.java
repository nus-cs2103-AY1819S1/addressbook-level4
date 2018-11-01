package seedu.address.storage;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.leaveapplication.Description;
import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.leaveapplication.LeaveStatus;

/**
 * JAXB-friendly version of the LeaveApplication.
 */
public class XmlAdaptedLeaveApplication {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "LeaveApplication's %s field is missing!";

    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String status;
    @XmlElement(required = true)
    private List<String> dates = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedLeaveApplication.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLeaveApplication() {
    }

    /**
     * Constructs an {@code XmlAdaptedLeaveApplication} with the given leave application details.
     */
    public XmlAdaptedLeaveApplication(String description, String status, List<LocalDate> dates) {
        this.description = description;
        this.status = status;
        requireAllNonNull(dates);
        this.dates = dates.stream()
                     .map(DateUtil::convertToString)
                     .collect(Collectors.toList());
    }

    /**
     * Converts a given LeaveApplication into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedLeaveApplication
     */
    public XmlAdaptedLeaveApplication(LeaveApplication source) {
        description = source.getDescription().value;
        status = source.getLeaveStatus().value.toString();
        dates = source.getDates().stream()
                .map(DateUtil::convertToString)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's LeaveApplication object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted leave application
     */
    public LeaveApplication toModelType() throws IllegalValueException {
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

        final List<LocalDate> modelDates = new ArrayList<>();
        for (String dateString : dates) {
            LocalDate parsedDate;
            try {
                parsedDate = DateUtil.convertToDate(dateString);
            } catch (DateTimeException e) {
                throw new IllegalValueException(e.getMessage());
            }
            modelDates.add(parsedDate);
        }
        return new LeaveApplication(modelDescription, modelStatus, modelDates);
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
        return Objects.equals(description, otherLeave.description)
                && Objects.equals(status, otherLeave.status)
                && Objects.equals(dates, otherLeave.dates);
    }
}

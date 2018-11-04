package seedu.address.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.collections.ObservableList;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.model.event.Event;
import seedu.address.model.record.Record;
import seedu.address.model.volunteer.Gender;
import seedu.address.model.volunteer.Volunteer;

/**
 * Contains methods used when calling the overview command.
 */
public class Overview {
    private ObservableList<Volunteer> volunteers;
    private ObservableList<Event> events;
    private ObservableList<Record> records;

    // Event-related stats
    private int numOfOngoingEvents;
    private int numOfUpcomingEvents;
    private int numOfCompletedEvents;

    // Volunteer-related stats
    private int numOfMale;
    private int numOfFemale;
    private int numOfChildren; //Age 14 and below
    private int numOfYouth; //Age 15 to 24
    private int numOfAdult; //Age 25 to 64
    private int numOfSenior; //Age 65 and above

    public Overview(ObservableList<Volunteer> volunteers,
                    ObservableList<Event> events,
                    ObservableList<Record> records) {
        this.volunteers = volunteers;
        this.events = events;
        this.records = records;

        calculateNumOfEvents();
        calculateVolunteerDemographics();
    }

    public int getNumOfOngoingEvents() {
        return numOfOngoingEvents;
    }

    public int getNumOfUpcomingEvents() {
        return numOfUpcomingEvents;
    }

    public int getNumOfCompletedEvents() {
        return numOfCompletedEvents;
    }

    public int getNumOfMale() {
        return numOfMale;
    }

    public int getNumOfFemale() {
        return numOfFemale;
    }

    public int getNumOfChildren() {
        return numOfChildren;
    }

    public int getNumOfYouth() {
        return numOfYouth;
    }

    public int getNumOfAdult() {
        return numOfAdult;
    }

    public int getNumOfSenior() {
        return numOfSenior;
    }

    /**
     * This method helps to get the number of events for the respective types based on time.
     */
    private void calculateNumOfEvents() {
        numOfUpcomingEvents = 0;
        numOfOngoingEvents = 0;
        numOfCompletedEvents = 0;


        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            int status = DateTimeUtil.getEventStatus(e.getStartDate(), e.getStartTime(),
                    e.getEndDate(), e.getEndTime());
            if (status == DateTimeUtil.UPCOMING_EVENT) {
                numOfUpcomingEvents++;
            }
            if (status == DateTimeUtil.ONGOING_EVENT) {
                numOfOngoingEvents++;
            }
            if (status == DateTimeUtil.COMPLETED_EVENT) {
                numOfCompletedEvents++;
            }
        }
    }

    /**
     * This method helps to calculate the volunteer demographics.
     */
    private void calculateVolunteerDemographics() {
        numOfMale = 0;
        numOfFemale = 0;
        numOfChildren = 0;
        numOfYouth = 0;
        numOfAdult = 0;
        numOfSenior = 0;

        //create calendar object for current day
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer v = volunteers.get(i);
            if (v.getGender().value.equals(Gender.GENDER_MALE_VALIDATION_REGEX)) {
                numOfMale++;
            }
            if (v.getGender().value.equals(Gender.GENDER_FEMALE_VALIDATION_REGEX)) {
                numOfFemale++;
            }

            int age = 0;
            try {
                Date birthDate = sdf.parse(v.getBirthday().value);
                age = calculateAge(now, birthDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (age <= 14) {
                numOfChildren++;
            }
            if (age >= 15 && age <= 24) {
                numOfYouth++;
            }

            if (age >= 25 && age <= 64) {
                numOfAdult++;
            }

            if (age >= 65) {
                numOfSenior++;
            }

        }
    }

    /**
     * Calculate the age of a volunteer.
     *
     * @param now       DateTime today
     * @param birthDate Birth date of a volunteer
     * @return integer age of volunteer
     */
    private int calculateAge(Calendar now, Date birthDate) {
        int years = 0;
        int months = 0;

        //create calendar object for birth day
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthDate.getTime());

        //Get difference between years
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;

        //Get difference between months
        months = currMonth - birthMonth;

        //if month difference is in negative then reduce years by one
        //and calculate the number of months.
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                months--;
            }
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }

        //Create new Age object
        return years;
    }
}


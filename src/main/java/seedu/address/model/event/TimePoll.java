package seedu.address.model.event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.Slot;
import seedu.address.model.person.UniquePersonList;

/**
 *
 */
public class TimePoll extends AbstractPoll {
    Schedule sharedShedule;
    private int id;
    private String pollName;
    private HashMap<String, UniquePersonList> pollData;

    /**
     *
     */
    public TimePoll(UniquePersonList participantList, LocalDate startDate, LocalDate endDate) {
        ArrayList<Schedule> schedules = new ArrayList<Schedule>;
        for (Person person : participantList) {
            Schedule schedule = person.getSchedule();
            schedules.add(schedule);
        }
        sharedShedule = Schedule.maxSchedule(schedules.toArray(new Schedule[schedules.size()]));
        try {
            ArrayList<Slot> freeTimes = sharedShedule.getFreeTime();
            List<LocalDate> dates = startDate.datesUntil(endDate).collect(Collectors.toList());
            for (LocalDate date : dates) {
                ArrayList<String> options = createOptionsFromDate(date);
                for (String option : options) {
                    UniquePersonList voterList = new UniquePersonList();
                    pollData.put(option, voterList);
                }
            }
        } catch (ParseException e) {

        }
    }

    private ArrayList<String> createOptionsFromDate(LocalDate date) throws ParseException {
        Integer dayOfWeek = date.getDayOfWeek().getValue();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateString = date.format(dateFormat);
        List<String> times = sharedShedule.getFreeTime()
                .stream()
                .map(slot -> slot.getTime())
                .collect(Collectors.toList());
        ArrayList<String> options = new ArrayList<>();
        for (String time : times) {
            String option = dateString + " " + time;
            options.add(option);
        }
        return options;
    }

}

package seedu.address.model.person;

/**
 * @author adjscent
 */
public class Lesson {

    private String moduleCode;
    private String classNo;
    private String lessonType;
    private String weekText;
    private String dayText;
    private String startTime;
    private String endTime;
    private int duration;


    public Lesson(String moduleCode, String classNo, String lessonType,
                  String weekText, String dayText, String startTime, String endTime) {
        this.moduleCode = moduleCode;
        this.classNo = classNo;
        this.lessonType = lessonType;
        this.weekText = weekText;
        this.dayText = dayText;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = Integer.parseInt(getEndTime().substring(0, 2))
            - Integer.parseInt(getStartTime().substring(0, 2));

    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getClassNo() {
        return classNo;
    }

    public String getLessonType() {
        return lessonType;
    }

    public String getWeekText() {
        return weekText;
    }

    public String getDayText() {
        return dayText;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s %s",
            moduleCode, classNo, lessonType, weekText, dayText, startTime, endTime);
    }

}

package seedu.address.model.person;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class LessonTest {
    @Test
    void testMethods() throws ParseException {
        Lesson lesson = new Lesson("CS2103", "1", "LEC", "1", "1", "0800", "0900");
        lesson.getDayText();
        lesson.getStartTime();
        lesson.getEndTime();
        lesson.getDuration();
        lesson.getClassNo();
        lesson.getLessonType();
        lesson.getModuleCode();
        lesson.getWeekText();
        lesson.toString();
    }
}

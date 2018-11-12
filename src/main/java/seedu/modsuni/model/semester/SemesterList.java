package seedu.modsuni.model.semester;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Represents the students schedule.
 * Encapsulates the list of semesters in the schedule.
 */
public class SemesterList implements Iterable<Semester> {
    private List<Semester> semesterList;

    public SemesterList() {
        this.semesterList = new ArrayList<>();
    }

    /**
     * Adds a new semester to the semesterlist.
     */
    public void addSemester(Semester newSemester) {
        requireNonNull(newSemester);
        semesterList.add(newSemester);
    }

    public void reverseOrder() {
        Collections.reverse(semesterList);
    }

    @Override
    public String toString() {
        int count = 1;
        StringBuilder sb = new StringBuilder();
        for (Semester semester : semesterList) {
            sb.append("semester ");
            sb.append(count);
            sb.append(": ");
            sb.append(semester.toString());
            sb.append("\n");
            count++;
        }
        return sb.toString();
    }

    @Override
    public Iterator<Semester> iterator() {
        return semesterList.iterator();
    }
}

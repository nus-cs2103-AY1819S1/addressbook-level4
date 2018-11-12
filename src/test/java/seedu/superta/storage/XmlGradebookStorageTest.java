package seedu.superta.storage;

import static seedu.superta.testutil.TypicalSuperTaClient.BENSON;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import seedu.superta.model.assignment.GradeBook;
import seedu.superta.model.assignment.GradeEntry;
import seedu.superta.model.student.StudentId;

public class XmlGradebookStorageTest {
    @Test
    public void toModelType_success() {
        GradeBook source = new GradeBook();
        source.addGrade(BENSON.getStudentId(), 10.0);
        Map<StudentId, GradeEntry> map = new HashMap<>();
        map.put(BENSON.getStudentId(), new GradeEntry(BENSON.getStudentId(), 10.0));
        XmlAdaptedGradeBook gradeBook = new XmlAdaptedGradeBook(map);
        gradeBook.toModelType().equals(source);
    }
}

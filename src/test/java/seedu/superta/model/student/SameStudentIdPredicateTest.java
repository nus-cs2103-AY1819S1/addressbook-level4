package seedu.superta.model.student;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

public class SameStudentIdPredicateTest {

    @Test
    public void test_sameStudentIdPredicate() {
        SameStudentIdPredicate predicate = new SameStudentIdPredicate("A0123456Z");

        assertTrue(predicate.test(new Student(
                new Name("Tester Tester"),
                new Phone("98765432"),
                new Email("tester@tester.com"),
                new StudentId("A0123456Z"),
                new HashSet<>(),
                new ArrayList<>())));

        assertFalse(predicate.test(new Student(
                new Name("Tester Tester"),
                new Phone("98765432"),
                new Email("tester@tester.com"),
                new StudentId("A0123456A"),
                new HashSet<>(),
                new ArrayList<>())));
    }
}

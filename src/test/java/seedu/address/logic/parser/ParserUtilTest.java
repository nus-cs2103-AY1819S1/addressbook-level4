package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import static seedu.address.testutil.TypicalModules.ASKING_QUESTIONS;
import static seedu.address.testutil.TypicalModules.CODE_ASKING_QUESTIONS;
import static seedu.address.testutil.TypicalModules.YEAR_FIVE;
import static seedu.address.testutil.TypicalModules.YEAR_FOUR;
import static seedu.address.testutil.TypicalModules.YEAR_ONE;
import static seedu.address.testutil.TypicalModules.YEAR_THREE;
import static seedu.address.testutil.TypicalModules.YEAR_TWO;

import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Code;
import seedu.address.model.module.Semester;
import seedu.address.testutil.Assert;

public class ParserUtilTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseTokenizeNullThrowsNullPointerException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> {
            ParserUtil.tokenize(null);
        });
    }

    @Test
    public void parseTokenizeSplitsSuccess() {
        String[] expected = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
        String[] tokenize = ParserUtil.tokenize("   a                     "
                + "b \t "
                + "c \r"
                + "d \n"
                + "e \t\r"
                + "f \t\n"
                + "g \r\n h"
                + "\t\r\n i");

        assertEquals(expected[0], tokenize[0]);
        assertEquals(expected[1], tokenize[1]);
        assertEquals(expected[2], tokenize[2]);
        assertEquals(expected[3], tokenize[3]);
        assertEquals(expected[4], tokenize[4]);
        assertEquals(expected[5], tokenize[5]);
        assertEquals(expected[6], tokenize[6]);
        assertEquals(expected[7], tokenize[7]);
        assertEquals(expected[8], tokenize[8]);
    }

    @Test
    public void validateNumOfArgs() throws Exception {
        String[] tokenize = {"a", "b", "c", "d"};

        ParserUtil.argsWithBounds(tokenize, 4);
        ParserUtil.argsWithBounds(tokenize, Integer.MIN_VALUE, 4);
        ParserUtil.argsWithBounds(tokenize, 4, Integer.MAX_VALUE);

        thrown.expect(ParseException.class);
        ParserUtil.argsWithBounds(tokenize, 5, Integer.MAX_VALUE);

        thrown.expect(ParseException.class);
        ParserUtil.argsWithBounds(tokenize, Integer.MAX_VALUE, 3);

        Set<Integer> allowedNumOfArgs = new HashSet<>();
        allowedNumOfArgs.add(2);
        allowedNumOfArgs.add(3);

        thrown.expect(ParseException.class);
        ParserUtil.argsWithBounds(tokenize, allowedNumOfArgs);
    }

    @Test
    public void parseCodeNullThrowsNullPointerException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> {
            ParserUtil.parseCode(null);
        });
    }

    @Test
    public void parseCodeValid() throws Exception {
        Code geqCode = ParserUtil.parseCode(CODE_ASKING_QUESTIONS);
        assertEquals(geqCode, ASKING_QUESTIONS.getCode());

        String lowerCaseCode = CODE_ASKING_QUESTIONS.toLowerCase();
        Code geqCodeLowerCase = ParserUtil.parseCode(lowerCaseCode);
        assertEquals(geqCodeLowerCase, ASKING_QUESTIONS.getCode());
    }

    @Test
    public void parseCodeInvalid() throws Exception {
        Code geqCode = ParserUtil.parseCode(CODE_ASKING_QUESTIONS);
        assertEquals(geqCode, ASKING_QUESTIONS.getCode());

        Assert.assertThrows(ParseException.class, () -> {
            ParserUtil.parseCode(CODE_ASKING_QUESTIONS
                    + " "
                    + CODE_ASKING_QUESTIONS);
        });
    }

    @Test
    public void parseYearNullThrowsNullPointerException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> {
            ParserUtil.parseYear(null);
        });
    }

    @Test
    public void parseYearTest() throws Exception {
        ParserUtil.parseYear(YEAR_ONE + "");
        ParserUtil.parseYear(YEAR_TWO + "");
        ParserUtil.parseYear(YEAR_THREE + "");
        ParserUtil.parseYear(YEAR_FOUR + "");
        ParserUtil.parseYear(YEAR_FIVE + "");

        Assert.assertThrows(ParseException.class, () -> {
            ParserUtil.parseYear("0");
        });

        Assert.assertThrows(ParseException.class, () -> {
            ParserUtil.parseYear("6");
        });
    }

    @Test
    public void parseSemesterNullThrowsNullPointerException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> {
            ParserUtil.parseSemester(null);
        });
    }

    @Test
    public void parseSemesterTest() throws Exception {
        ParserUtil.parseSemester(Semester.SEMESTER_ONE);
        ParserUtil.parseSemester(Semester.SEMESTER_TWO);
        ParserUtil.parseSemester(Semester.SEMESTER_SPECIAL_ONE);
        ParserUtil.parseSemester(Semester.SEMESTER_SPECIAL_TWO);

        Assert.assertThrows(ParseException.class, () -> {
            ParserUtil.parseSemester("0");
        });

        Assert.assertThrows(ParseException.class, () -> {
            ParserUtil.parseSemester("3");
        });

        Assert.assertThrows(ParseException.class, () -> {
            ParserUtil.parseSemester("s0");
        });

        Assert.assertThrows(ParseException.class, () -> {
            ParserUtil.parseSemester("s3");
        });

        Assert.assertThrows(ParseException.class, () -> {
            ParserUtil.parseSemester("");
        });
    }

    @Test
    public void parseCreditNullThrowsNullPointerException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> {
            ParserUtil.parseCredit(null);
        });
    }

    @Test
    public void parseCreditTest() throws Exception {
        ParserUtil.parseCredit("1");
        ParserUtil.parseCredit("20");

        Assert.assertThrows(ParseException.class, () -> {
            ParserUtil.parseCredit("0");
        });

        Assert.assertThrows(ParseException.class, () -> {
            ParserUtil.parseCredit("21");
        });
    }

    @Test
    public void parseGradeNullThrowsNullPointerException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> {
            ParserUtil.parseGrade(null);
        });
    }

    @Test
    public void parseGradeTest() throws Exception {
        ParserUtil.parseGrade("A+");

        Assert.assertThrows(ParseException.class, () -> {
            ParserUtil.parseGrade("G");
        });
    }
}

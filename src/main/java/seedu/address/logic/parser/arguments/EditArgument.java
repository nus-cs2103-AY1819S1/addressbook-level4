package seedu.address.logic.parser.arguments;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Basic enum for EditArgument.
 */
public enum EditArgument {
    TARGET_CODE("t", "targetCode") {
        @Override
        public Object getValue(String value) throws ParseException {
            return ParserUtil.parseCode(value);
        }
    },
    TARGET_YEAR("e", "targetYear") {
        @Override
        public Object getValue(String value) throws ParseException {
            return ParserUtil.parseYear(value);
        }
    },
    TARGET_SEMESTER("z", "targetSemester") {
        @Override
        public Object getValue(String value) throws ParseException {
            return ParserUtil.parseSemester(value);
        }
    },
    NEW_CODE("m", "code") {
        @Override
        public Object getValue(String value) throws ParseException {
            return ParserUtil.parseCode(value);
        }
    },
    NEW_YEAR("y", "year") {
        @Override
        public Object getValue(String value) throws ParseException {
            return ParserUtil.parseYear(value);
        }
    },
    NEW_SEMESTER("s", "semester") {
        @Override
        public Object getValue(String value) throws ParseException {
            return ParserUtil.parseSemester(value);
        }
    },
    NEW_CREDIT("c", "credit") {
        @Override
        public Object getValue(String value) throws ParseException {
            return ParserUtil.parseCredit(value);
        }
    },
    NEW_GRADE("g", "grade") {
        @Override
        public Object getValue(String value) throws ParseException {
            return ParserUtil.parseGrade(value);
        }
    };

    private String shortName;
    private String longName;

    /**
     *
     * @return
     */
    public String getShortName() {
        return ParserUtil.NAME_PREFIX_SHORT + shortName;
    }

    /**
     *
     * @return
     */
    public String getLongName() {
        return ParserUtil.NAME_PREFIX_LONG + longName;
    }

    /**
     *
     * @param value
     * @return
     * @throws ParseException
     */
    public Object getValue(String value) throws ParseException {
        return null;
    }

    /**
     *
     * @param shortOption
     * @param longOption
     */
    EditArgument(String shortOption, String longOption) {
        this.shortName = shortOption;
        this.longName = longOption;
    }
}

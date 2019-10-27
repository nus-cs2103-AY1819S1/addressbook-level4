package seedu.address.model.entry;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.StringUtil.isEmptyString;
import static seedu.address.commons.util.StringUtil.isOnlyWhiteSpace;

import java.util.LinkedList;
import java.util.List;

/**
 * represents
 */
public class EntryInfo {

    public static final String ENTRYINFO_VALIDATION_REGEX = "^[A-Za-z0-9 ()-]+$";
    public static final String MESSAGE_ENTRYINFO_CONSTRAINTS =
            "header, subHeader and duration must be alphanumeric, "
                    + "separated by brackets or hyphen or space and cannot be an empty string or"
                    + "entirely consist of whitespaces";
    private static final String NEW_LINE = "\n";
    private List<String> entryInfo = new LinkedList<String>();

    /**
     * default constructor with empty entryInfo.
     */
    public EntryInfo() {}

    /**
     * @param title
     * @param subheader
     * @param duration
     */
    public EntryInfo(String title, String subheader, String duration) {
        requireAllNonNull(title, subheader, duration);
        checkArguments(title, subheader, duration);
        entryInfo.add(title);
        entryInfo.add(subheader);
        entryInfo.add(duration);
    }

    /**
     * precond: entryInfo must not contain any null element
     * @param entryInfo list containing title, subHeader, duration. Can be empty
     */
    public EntryInfo(List<String> entryInfo) {
        requireAllNonNull(entryInfo);
        checkArgument(isValidEntryInfo(entryInfo), MESSAGE_ENTRYINFO_CONSTRAINTS);
        this.entryInfo.addAll(entryInfo);
    }


    public List<String> getEntryInfo() {
        return entryInfo;
    }

    /**
     * @return true if EntryInfo is empty, i.e. no title, subHeader, duration
     */
    public boolean isEmpty() {
        return entryInfo.isEmpty();
    }

    /**
     * checks if a string is a valid EntryInfo field
     */
    public static boolean isValidEntryInfoField(String s) {
        return !isOnlyWhiteSpace(s)
                && !isEmptyString(s)
                && s.matches(ENTRYINFO_VALIDATION_REGEX);
    }

    /**
     * checks if a list of string matches the EntryInfo Regex
     */
    public static boolean isValidEntryInfo(List<String> entryInfo) {
        return entryInfo.stream().allMatch(s -> isValidEntryInfoField(s));
    }

    /**
     * checks if any of the fields is of invalid format.
     * @throws IllegalArgumentException with error message MESSAGE_ENTRYINFO_CONSTRAINTS if invalid format is detected.
     */
    private void checkArguments(String title, String subtitle, String duration) {
        checkArgument(isValidEntryInfoField(title), MESSAGE_ENTRYINFO_CONSTRAINTS);
        checkArgument(isValidEntryInfoField(subtitle), MESSAGE_ENTRYINFO_CONSTRAINTS);
        checkArgument(isValidEntryInfoField(duration), MESSAGE_ENTRYINFO_CONSTRAINTS);

    }

    /**
     * @return Title of the entry.
     */
    public String getTitle() {
        return isEmpty() ? "" : entryInfo.get(0);
    }

    /**
     * @return Subheader of the entry.
     */
    // precond: for v1.1, input for subheader is compulsory
    public String getSubHeader() {
        return isEmpty() ? "" : entryInfo.get(1);
    }

    /**
     * precond: entryInfo not empty.
     * @return duration the entry is associated with.
     */
    public String getDuration() {
        return isEmpty() ? "" : entryInfo.get(2);
    }

    /**
     * precond: entryInfo not empty.
     * set title of the entry
     */
    public void setTitle(String title) {
        requireNonNull(title);
        checkArgument(isValidEntryInfoField(title), MESSAGE_ENTRYINFO_CONSTRAINTS);
        entryInfo.set(0, title);
    }

    /**
     * precond: entryInfo not empty.
     * set subHeader of the entry
     */
    public void setSubHeader(String subHeader) {
        requireNonNull(subHeader);
        checkArgument(isValidEntryInfoField(subHeader), MESSAGE_ENTRYINFO_CONSTRAINTS);
        entryInfo.set(1, subHeader);
    }

    /**
     * precond: entryInfo not empty.
     * set duration of the entry
     */
    public void setDuration(String duration) {
        requireNonNull(duration);
        checkArgument(isValidEntryInfoField(duration), MESSAGE_ENTRYINFO_CONSTRAINTS);
        entryInfo.set(2, duration);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof EntryInfo
                && entryInfo.equals(((EntryInfo) other).getEntryInfo()));
    }

    @Override
    public int hashCode() {
        return entryInfo.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Title: ")
                .append(getTitle())
                .append(NEW_LINE)
                .append("SubHeader: ")
                .append(getSubHeader())
                .append(NEW_LINE)
                .append("Duration: ")
                .append(getDuration());
        return builder.toString();
    }
}

package seedu.address.model.entry;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.StringUtil.isEmptyString;
import static seedu.address.commons.util.StringUtil.isOnlyWhiteSpace;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  Represents bullet points content for an entry.
 */
public class EntryDescription {

    public static final String MESSAGE_ENTRYDESC_CONSTRAINTS = "A bullet description must not be "
            + "empty and must have at least one non whitespace character.";

    private List<String> descriptionList;

    public EntryDescription() {
        this.descriptionList = new ArrayList<>();
    }

    /**
     * construct to duplicate EntryDescription
     * @param toCopy
     */
    public EntryDescription(EntryDescription toCopy) {
        requireNonNull(toCopy);
        descriptionList = toCopy
                .getDescriptionList()
                .stream()
                .collect(Collectors.toList());
    }

    public List<String> getDescriptionList() {
        return descriptionList;
    }

    /**
     * Add a bullet to the descriptionList
     * @param bullet
     */
    public void addBullet(String bullet) {
        checkArgument(isValidBullet(bullet), MESSAGE_ENTRYDESC_CONSTRAINTS);

        descriptionList.add(bullet);
    }

    /**
     * checks if a bullet is contained in the EntryDescription.
     */
    public boolean contains(String bullet) {
        return descriptionList.contains(bullet);
    }

    /**
     * Checks if a bullet is valid.
     * i.e bullet is not empty string and does not consist entirely of whitespace
     * @param bullet a bullet description
     * @return
     */
    public static boolean isValidBullet(String bullet) {
        return !isOnlyWhiteSpace(bullet) && !isEmptyString(bullet);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof EntryDescription
                && descriptionList.equals(((EntryDescription) other)
                    .getDescriptionList()));
    }

    @Override
    public String toString() {
        int index = 1;
        final StringBuilder builder = new StringBuilder();

        for (String bullet: descriptionList) {
            builder.append(index).append(". ").append(bullet).append("\n");
            index++;
        }

        return builder.toString();
    }
}

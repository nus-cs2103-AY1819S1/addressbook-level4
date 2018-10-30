package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ACADEMICYEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULETITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.logic.commands.EditModuleCommand.EditModuleDescriptor;
import seedu.address.model.module.Module;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Module.
 */
public class ModuleUtil {

    /**
     * Returns an add command string for adding the {@code module}.
     */
    public static String getAddCommand(Module module) {
        return AddModuleCommand.COMMAND_WORD + " " + getModuleDetails(module);
    }

    /**
     * Returns the part of command string for the given {@code module}'s details.
     */
    public static String getModuleDetails(Module module) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_MODULECODE + module.getModuleCode().fullModuleCode + " ");
        sb.append(PREFIX_MODULETITLE + module.getModuleTitle().fullModuleTitle + " ");
        sb.append(PREFIX_ACADEMICYEAR + "" + module.getAcademicYear().yearNumber + " ");
        sb.append(PREFIX_SEMESTER + module.getSemester().semesterNumber + " ");
        module.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditModuleDescriptor}'s details.
     */
    public static String getEditModuleDescriptorDetails(EditModuleDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getModuleCode().ifPresent(name -> sb.append(PREFIX_MODULECODE)
                .append(name.fullModuleCode).append(" "));
        descriptor.getModuleTitle().ifPresent(moduleTitle -> sb.append(PREFIX_MODULETITLE)
                .append(moduleTitle.fullModuleTitle)
                .append(" "));
        descriptor.getAcademicYear().ifPresent(academicYear -> sb.append(PREFIX_ACADEMICYEAR)
                .append(academicYear.yearNumber).append(" "));
        descriptor.getSemester().ifPresent(semester -> sb.append(PREFIX_SEMESTER).append(semester.semesterNumber)
                .append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}

package seedu.address.logic.parser;

import java.util.Scanner;

/**
 * Prompt handler for users.
 */
public class Prompt {

    public static final String LEADING_MESSAGE = "Please enter a time slot here: ";

    /**
     * Gets more input from the user
     * @param messageToUser message to be displayed
     * @return input by the user
     */
    public String promptForMoreInput (String messageToUser) {
        Scanner in = new Scanner(System.in); // need to connect to UI later
        System.out.println(LEADING_MESSAGE + messageToUser);
        return in.nextLine(); // commandBox.getTextField()
    }

}

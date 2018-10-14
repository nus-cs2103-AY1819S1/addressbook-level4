package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.UserData;
import seedu.address.model.accounting.Debt;
import seedu.address.model.jio.Jio;
import seedu.address.model.timetable.UniqueBusySchedule;
import seedu.address.model.user.Friendship;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * An list of users that is serializable to XML format
 */
@XmlRootElement(name = "users")
public class XmlSerializableUsers {

    public static final String MESSAGE_DUPLICATE_PERSON = "User list contains duplicate User(s).";
    public static final String MESSAGE_NO_USER_FRIENDSHIP = "User required for friendship not found";
    public static final String MESSAGE_NO_USER_DEBTS = "User required for debts record not found";
    public static final String MESSAGE_DUPLICATE_JIO = "This jio already exists in the book";

    @XmlElement
    private List<XmlAdaptedUser> user;
    @XmlElement
    private List<XmlAdaptedFriendship> friendship;
    @XmlElement
    private List<XmlAdaptedDebt> debts;
    @XmlElement
    private List<XmlAdaptedJio> jios;
    @XmlElement
    private List<XmlAdaptedBusySchedule> busySchedules;

    /**
     * Creates an empty XmlSerializableUsers.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableUsers() {
        user = new ArrayList<>();
        friendship = new ArrayList<>();
        debts = new ArrayList<>();
        jios = new ArrayList<>();
        busySchedules = new ArrayList<>();
    }

    /**
     * Conversion from Model into XML.
     */
    public XmlSerializableUsers(UserData userData) {
        this();

        List<User> allUsers = new ArrayList<User>(userData.getUsernameUserHashMap().values());

        // adds Users into the hashmap
        allUsers.forEach(individualUser -> user
                .add(new XmlAdaptedUser(individualUser)));

        // updates hashmap with friends of all Users
        allUsers.forEach(individualUser -> individualUser.getFriends()
                .forEach(f -> friendship.add(new XmlAdaptedFriendship(f))));

        // updates hashmap with friendRequests of all Users
        allUsers.forEach(individualUser -> individualUser.getFriendRequests()
                .forEach(f -> friendship.add(new XmlAdaptedFriendship(f))));
        allUsers.forEach(individualUser -> individualUser.getDebts()
                .forEach(d -> debts.add(new XmlAdaptedDebt(d))));

        // updates jios list
        userData.getJios().forEach(jio -> jios.add(new XmlAdaptedJio(jio)));

        // adds all schedules into the user data in preparation for XML Storage.
        allUsers.forEach(individualUser ->
                busySchedules.add(new XmlAdaptedBusySchedule(individualUser.getBusySchedule())));
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedRestaurant}.
     */
    public UserData toModelType() throws IllegalValueException {
        UserData userData = new UserData();
        for (XmlAdaptedUser u : user) {
            User user = u.toModelType();
            if (userData.hasUser(user.getUsername())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            userData.addUser(user);
        }

        for (XmlAdaptedFriendship f: friendship) {
            Friendship friendship = f.toModelType(userData.getUsernameUserHashMap());
            if (!userData.hasUser(friendship.getMyUsername())) {
                throw new IllegalValueException(MESSAGE_NO_USER_FRIENDSHIP);
            }
            if (!userData.hasUser(friendship.getFriendUsername())) {
                throw new IllegalValueException(MESSAGE_NO_USER_FRIENDSHIP);
            }

            userData.addUser(friendship.getMyUsername(),
                    userData.getUser(friendship.getMyUsername()).addFriendship(friendship));
        }

        /** Converts the UserData's timetable information into the model's {@code UniqueBusySchedule} object
         * and stores it in the respective user object.
         */
        for (XmlAdaptedBusySchedule busySchedule : busySchedules) {

            // Bbtain the Models.
            UniqueBusySchedule currentSchedule = busySchedule.toModelType();
            Username currentUsername = currentSchedule.getUsername();
            // Get the user.
            User currentUser = userData.getUser(currentUsername);

            // Add the schedule into the user.
            currentUser.addUniqueBusySchedule(currentSchedule);
        }


        for (XmlAdaptedDebt d: debts) {
            Debt debts = d.toModelType(userData.getUsernameUserHashMap());
            if (!userData.getUsernameUserHashMap().containsKey(debts.getCreditor().getUsername())) {
                throw new IllegalValueException(MESSAGE_NO_USER_DEBTS);
            }
            if (!userData.getUsernameUserHashMap().containsKey(debts.getDebtor().getUsername())) {
                throw new IllegalValueException(MESSAGE_NO_USER_DEBTS);
            }

            userData.getUsernameUserHashMap().put(debts.getCreditor().getUsername(),
                    userData.getUsernameUserHashMap().get(debts.getCreditor().getUsername()).addDebt(debts));

            userData.getUsernameUserHashMap().put(debts.getDebtor().getUsername(),
                    userData.getUsernameUserHashMap().get(debts.getDebtor().getUsername()).addDebt(debts));

        }

        for (XmlAdaptedJio j: jios) {
            Jio jio = j.toModelType();
            if (!userData.hasJio(jio)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_JIO);
            }
            userData.addJio(jio);
        }

        return userData;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableUsers)) {
            return false;
        }
        return user.equals(((XmlSerializableUsers) other).user);
    }
}

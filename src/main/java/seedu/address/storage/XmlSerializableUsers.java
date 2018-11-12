package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.UserData;
import seedu.address.model.accounting.Debt;
import seedu.address.model.friend.Friendship;
import seedu.address.model.group.Group;
import seedu.address.model.jio.Jio;
import seedu.address.model.timetable.UniqueSchedule;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * An list of users that is serializable to XML format
 */
@XmlRootElement(name = "users")
public class XmlSerializableUsers {

    public static final String MESSAGE_DUPLICATE_PERSON = "User list contains duplicate User(s).";
    public static final String MESSAGE_DUPLICATE_GROUP = "Group name already exists.";
    public static final String MESSAGE_DUPLICATE_JIO = "This jio already exists in the book";

    private static final Logger logger = LogsCenter.getLogger(XmlUsersStorage.class);

    @XmlElement
    private List<XmlAdaptedUser> user;
    @XmlElement
    private List<XmlAdaptedFriendship> friendship;
    @XmlElement
    private List<XmlAdaptedGroup> groups;
    @XmlElement
    private List<XmlAdaptedDebt> debts;
    @XmlElement
    private List<XmlAdaptedJio> jios;
    @XmlElement
    private List<XmlAdaptedBusySchedule> busySchedules;
    @XmlElement
    private List<XmlAdaptedRestaurantReview> restaurantReviews;

    /**
     * Creates an empty XmlSerializableUsers.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableUsers() {
        user = new ArrayList<>();
        friendship = new ArrayList<>();
        groups = new ArrayList<>();
        debts = new ArrayList<>();
        jios = new ArrayList<>();
        busySchedules = new ArrayList<>();
        restaurantReviews = new ArrayList<>();
    }

    /**
     * Conversion from Model into XML.
     */
    public XmlSerializableUsers(UserData userData) {
        this();

        List<User> allUsers = new ArrayList<User>(userData.getUsernameUserHashMap().values());
        List<Group> allGroups = new ArrayList<Group>(userData.getGroupHashmap().values());

        // adds Users into the hashmap
        allUsers.forEach(individualUser -> user
                .add(new XmlAdaptedUser(individualUser)));

        // updates hashmap with friends of all Users
        allUsers.forEach(individualUser -> individualUser.getFriends()
                .forEach(f -> friendship.add(new XmlAdaptedFriendship(f))));

        // updates hashmap with friendRequests of all Users
        allUsers.forEach(individualUser -> individualUser.getFriendRequests()
                .forEach(f -> friendship.add(new XmlAdaptedFriendship(f))));

        // updates hashmap with debts of all users
        allUsers.forEach(individualUser -> individualUser.getDebts()
                .forEach(d -> debts.add(new XmlAdaptedDebt(d))));

        // updates groups list
        allGroups.forEach(group -> groups.add(new XmlAdaptedGroup(group)));

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
            userData.addUser(friendship.getMyUsername(),
                    userData.getUser(friendship.getMyUsername()).addFriendship(friendship));
        }

        for (XmlAdaptedGroup g: groups) {
            Group group = g.toModelType(userData.getUsernameUserHashMap());
            if (userData.hasGroup(group.getGroupName())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_GROUP);
            }

            // adds all the groups into group hashmap
            userData.addGroup(group);

            // updates Users as to which groups they have
            group.getAcceptedUsers().forEach(user -> user.addGroup(group));
            group.getPendingUsers().forEach(user -> user.addGroupPending(group));
        }

        /** Converts the UserData's timetable information into the model's {@code UniqueSchedule} object
         * and stores it in the respective user object.
         */
        for (XmlAdaptedBusySchedule busySchedule : busySchedules) {

            // Obtain the Models.
            UniqueSchedule currentSchedule = busySchedule.toModelType();
            Username currentUsername = currentSchedule.getUsername();
            // Get the user.
            User currentUser = userData.getUser(currentUsername);

            // Add the schedule into the user.
            currentUser.addUniqueBusySchedule(currentSchedule);
        }

        /** Converts the UserData's debt information into the Debt object
         * , if it is not exist in that user data debt list,
         *  add the data to the respective user debt list.
         */
        for (XmlAdaptedDebt d: debts) {
            Debt debt = d.toModelType(userData.getUsernameUserHashMap());

            if (!userData.getUsernameUserHashMap().get(debt.getCreditor().getUsername()).getDebts().contains(debt)) {
                userData.getUsernameUserHashMap().put(debt.getCreditor().getUsername(),
                        userData.getUsernameUserHashMap().get(debt.getCreditor().getUsername()).addDebt(debt));
            }
            if (!userData.getUsernameUserHashMap().get(debt.getDebtor().getUsername()).getDebts().contains(debt)) {
                userData.getUsernameUserHashMap().put(debt.getDebtor().getUsername(),
                        userData.getUsernameUserHashMap().get(debt.getDebtor().getUsername()).addDebt(debt));
            }
        }

        for (XmlAdaptedJio j: jios) {
            Jio jio = j.toModelType();
            if (userData.hasJio(jio)) {
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

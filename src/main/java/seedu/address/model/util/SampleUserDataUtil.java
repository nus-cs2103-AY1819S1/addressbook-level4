package seedu.address.model.util;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.Name;
import seedu.address.model.UserData;
import seedu.address.model.accounting.Amount;
import seedu.address.model.accounting.DebtStatus;
import seedu.address.model.group.Group;
import seedu.address.model.jio.Jio;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Rating;
import seedu.address.model.restaurant.WrittenReview;
import seedu.address.model.timetable.Date;
import seedu.address.model.timetable.Day;
import seedu.address.model.timetable.Time;
import seedu.address.model.timetable.UniqueSchedule;
import seedu.address.model.timetable.Week;
import seedu.address.model.user.Email;
import seedu.address.model.user.Password;
import seedu.address.model.user.Phone;
import seedu.address.model.user.RestaurantReview;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleUserDataUtil {

    public static final String SAMPLE_GROUP_NAME = "2103";

    private static final RestaurantReview navekom_reviews01 = new RestaurantReview(
            new seedu.address.model.restaurant.Name("Waa Cow"), new Rating(3),
            new WrittenReview("I tried the Ultimate Beef Sushi and they were delicious."));

    private static final RestaurantReview chelchia_reviews01 = new RestaurantReview(
            new seedu.address.model.restaurant.Name("Waa Cow"), new Rating(2),
            new WrittenReview("I tried the Udon Cirashi and they were so-so."));

    private static final RestaurantReview chelchia_reviews02 = new RestaurantReview(
            new seedu.address.model.restaurant.Name("Subway"), new Rating(5),
            new WrittenReview("Namaste. Subway nice nice. Good food."));

    private static final RestaurantReview meena567_reviews01 = new RestaurantReview(
            new seedu.address.model.restaurant.Name("Waa Cow"), new Rating(2),
            new WrittenReview("Definitely coming back."));
    private static final RestaurantReview meena567_reviews02 = new RestaurantReview(
            new seedu.address.model.restaurant.Name("Subway"), new Rating(4),
            new WrittenReview("Subway give fresh food."));

    private static final RestaurantReview katespades_reviews01 = new RestaurantReview(
            new seedu.address.model.restaurant.Name("Waa Cow"), new Rating(2),
            new WrittenReview("The maki was fantastic."));

    private static final RestaurantReview katespades_reviews02 = new RestaurantReview(
            new seedu.address.model.restaurant.Name("Subway"), new Rating(5),
            new WrittenReview("5 stars sub."));

    private static final RestaurantReview themyth_reviews01 = new RestaurantReview(
            new seedu.address.model.restaurant.Name("Subway"), new Rating(5),
            new WrittenReview("Subway eat fresh."));

    private static final RestaurantReview themyth_reviews02 = new RestaurantReview(
            new seedu.address.model.restaurant.Name("The Royals Bistro"), new Rating(5),
            new WrittenReview("I stalked LHL here."));

    private static final RestaurantReview brotherLoong_reviews01 = new RestaurantReview(
            new seedu.address.model.restaurant.Name("The Royals Bistro"), new Rating(5),
            new WrittenReview("The food here is 5 stars. Just like the Singapore flag."));

    public static User[] getSampleUsers() {
        return new User[] {
            new User(new Username("navekom"), new Password("pwwd123"),
                    new Name("Evan Mok"), new Phone("91234567"),
                    new Email("evanm@gmail.com"))
                    .addRestaurantReviewToUser(navekom_reviews01),
            new User(new Username("meena567"), new Password("meenapwd123"),
                    new Name("Meena"), new Phone("98834568"),
                    new Email("meena567@gmail.com"))
                    .addRestaurantReviewToUser(meena567_reviews01)
                    .addRestaurantReviewToUser(meena567_reviews02),
            new User(new Username("chelchia"), new Password("chelpwd123"),
                    new Name("Chelsea Chia"), new Phone("90134569"),
                    new Email("chelchia@gmail.com"))
                    .addRestaurantReviewToUser(chelchia_reviews01)
                    .addRestaurantReviewToUser(chelchia_reviews02),
            new User(new Username("katespades"), new Password("katepwd123"),
                    new Name("Kate Ng"), new Phone("91434567"),
                    new Email("kateSpades@gmail.com"))
                    .addRestaurantReviewToUser(katespades_reviews01)
                    .addRestaurantReviewToUser(katespades_reviews02),
            new User(new Username("themyth"), new Password("themyth2k18"),
                    new Name("AP Damith"), new Phone("91134567"),
                    new Email("damith@nus.edu.com"))
                    .addRestaurantReviewToUser(themyth_reviews01)
                    .addRestaurantReviewToUser(themyth_reviews02),
            new User(new Username("brotherLoong"), new Password("LHL2k18"),
                    new Name("Hsien Loong"), new Phone("80232567"),
                    new Email("leehl@pmo.com"))
                    .addRestaurantReviewToUser(brotherLoong_reviews01),
            new User(new Username("thejrlinguist"), new Password("kengji123"),
                    new Name("KengJi"), new Phone("95534567"),
                    new Email("kengji@gmail.com"))
        };
    }

    public static Jio[] getSampleJios() {
        ArrayList<Username> lunchpeople = new ArrayList<>();
        lunchpeople.add(new Username("navekom"));
        lunchpeople.add(new Username("meena567"));
        lunchpeople.add(new Username("chelchia"));
        lunchpeople.add(new Username("katespades"));
        lunchpeople.add(new Username("aideeeen"));

        return new Jio[]{
            new Jio(new Name("lunch"), new Date(new Week("1"), new Day("mon"), new Time("1200")),
                    new Address("finefood"), lunchpeople, new Username("navekom")),
            new Jio(new Name("dinner"), new Date(new Week("2"), new Day("tue"), new Time("1800")),
                    new Address("foodclique"), new Username("navekom")),
            new Jio(new Name("MALA"), new Date(new Week("1"), new Day("mon"), new Time("1200")),
                    new Address("finefood"), new Username("meena567"))
        };
    }

    public static List<Date> getSampleDates() {
        List<Date> sampleDates = Date.generateRandomUniqueDates(2000);
        return sampleDates;
    }

    public static Group[] getSampleGroups(UserData sampleUd) {
        ArrayList<User> acceptedUsers = new ArrayList<> ();
        ArrayList<User> pendingUsers = new ArrayList<> ();
        User evan = new User(new Username("navekom"), new Password("pwwd123"),
                new Name("Evan Mok"), new Phone("91234567"),
                new Email("evanm@gmail.com"))
                .addRestaurantReviewToUser(navekom_reviews01);
        User meena = new User(new Username("meena567"), new Password("meenapwd123"),
                new Name("Meena"), new Phone("98834568"),
                new Email("meena567@gmail.com"))
                .addRestaurantReviewToUser(meena567_reviews01)
                .addRestaurantReviewToUser(meena567_reviews02);
        User yewwoei = new User(new Username("aiden"), new Password("aidenpwd123"),
                new Name("Low Yew Woei"), new Phone("91765567"),
                new Email("yewwoei@gmail.com"));
        User kate = new User(new Username("katespades"), new Password("katepwd123"),
                new Name("Kate Ng"), new Phone("91434567"),
                new Email("kateSpades@gmail.com"))
                .addRestaurantReviewToUser(katespades_reviews01)
                .addRestaurantReviewToUser(katespades_reviews02);
        User chel = new User(new Username("chelchia"), new Password("chelpwd123"),
                new Name("Chelsea Chia"), new Phone("90134569"),
                new Email("chelchia@gmail.com"))
                .addRestaurantReviewToUser(chelchia_reviews01)
                .addRestaurantReviewToUser(chelchia_reviews02);
        acceptedUsers.add(evan);
        acceptedUsers.add(meena);
        acceptedUsers.add(yewwoei);
        acceptedUsers.add(kate);
        pendingUsers.add(chel);

        sampleUd.addUser(evan);
        sampleUd.addUser(meena);
        sampleUd.addUser(yewwoei);
        sampleUd.addUser(kate);
        sampleUd.addUser(chel);

        return new Group[]{
            new Group(new Name(SAMPLE_GROUP_NAME), acceptedUsers, pendingUsers)
        };
    }

    public static UserData getSampleUserData() {
        UserData sampleUd = new UserData();

        ArrayList<User> userList = new ArrayList<User>();
        for (User sampleUser : getSampleUsers()) {
            sampleUd.addUser(sampleUser);
            userList.add(sampleUser);


        }
        for (Jio sampleJio : getSampleJios()) {
            sampleUd.addJio(sampleJio);
        }

        for (Group sampleGroup : getSampleGroups(sampleUd)) {
            sampleGroup.getAcceptedUsers().forEach(user -> user.addGroup(sampleGroup));
            sampleGroup.getPendingUsers().forEach(user -> user.addGroupRequest(sampleGroup));
            sampleUd.addGroup(sampleGroup);
        }

        // adding schedule for each user.
        List<User> allUsers = sampleUd.getUsers();
        for (User sampleUser : allUsers) {
            // creating a schedule for each user.
            UniqueSchedule sampleSchedule = new UniqueSchedule(sampleUser.getUsername());
            List<Date> sampleDates = getSampleDates();
            sampleDates.stream().forEach(sampleSchedule::add);
            // adding schedule for each user.
            sampleUser.addUniqueBusySchedule(sampleSchedule);
        }

        for (int i = 0; i < userList.size() - 1; i++) {
            userList.get(i).addDebt(userList.get(i + 1), new Amount(String.valueOf(i + 1)));
            userList.get(i).addFriend(userList.get(i + 1));
        }

        for (int i = 1; i < (userList.size() - 1) / 2; i++) {
            userList.get(i).acceptFriendRequest(userList.get(i - 1));
        }

        userList.get(userList.size() - 1).addDebt(userList.get(0), new Amount(String.valueOf(3)));

        for (int i = userList.size() - 1; i > 0; i--) {
            userList.get(i).addDebt(userList.get(i - 1), new Amount(String.valueOf(i)), DebtStatus.ACCEPTED);
        }
        userList.get(0).addDebt(userList.get(userList.size() - 1), new Amount(String.valueOf(3)), DebtStatus.ACCEPTED);
        return sampleUd;
    }
}

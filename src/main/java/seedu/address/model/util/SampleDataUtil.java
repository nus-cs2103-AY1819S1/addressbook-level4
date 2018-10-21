package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.FinancialDatabase;
import seedu.address.model.ReadOnlyFinancialDatabase;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Deadline;
import seedu.address.model.transaction.Transaction;

/**
 * Contains utility methods for populating {@code DebtTracker} with sample data.
 */
public class SampleDataUtil {
    public static seedu.address.model.transaction.Transaction[] getSampleTransactions() {
        return new seedu.address.model.transaction.Transaction[]{
            new Transaction(new Amount("SGD 33.00"), new Deadline("15/12/2018"),
                    new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                            new Address("Blk 30 Geylang Street 29, #06-40"),
                            getTagSet("debtor", "friends"))),
            new Transaction(new Amount("USD 55.50"), new Deadline("17/11/2018"),
                    new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                            new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                            getTagSet("lender", "colleagues", "friends"))),
            new Transaction(new Amount("AUD 46.80"), new Deadline("15/12/2018"),
                    new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                            new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                            getTagSet("lender", "neighbours"))),
            new Transaction(new Amount("MYR 155.50"), new Deadline("17/11/2018"),
                    new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                            new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                            getTagSet("debtor", "family"))),
            new Transaction(new Amount("KRW 378.50"), new Deadline("15/12/2018"),
                    new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                            new Address("Blk 47 Tampines Street 20, #17-35"),
                            getTagSet("debtor", "classmates"))),
            new Transaction(new Amount("IDR 1028.90"), new Deadline("17/11/2018"),
                    new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                            new Address("Blk 45 Aljunied Street 85, #11-31"),
                            getTagSet("lender", "colleagues")))
        };
    }

    public static ReadOnlyFinancialDatabase getSampleFinancialDatabase() {
        FinancialDatabase sampleFd = new FinancialDatabase();
        for (seedu.address.model.transaction.Transaction sampleTransaction : getSampleTransactions()) {
            sampleFd.addTransaction(sampleTransaction);
        }
        return sampleFd;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}

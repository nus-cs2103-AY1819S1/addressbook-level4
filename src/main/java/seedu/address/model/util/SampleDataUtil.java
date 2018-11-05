package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.MedicineName;
import seedu.address.model.medicine.MinimumStockQuantity;
import seedu.address.model.medicine.PricePerUnit;
import seedu.address.model.medicine.SerialNumber;
import seedu.address.model.medicine.Stock;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.IcNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Patient;
import seedu.address.model.person.Phone;
import seedu.address.model.person.medicalrecord.Disease;
import seedu.address.model.person.medicalrecord.DrugAllergy;
import seedu.address.model.person.medicalrecord.Note;
import seedu.address.model.person.medicalrecord.Quantity;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Patient[] getSamplePersons() {
        return new Patient[] {
            new Patient(new Name("Alex Yeoh"), new IcNumber("S9318157C"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                null),
            new Patient(new Name("Bernice Yu"), new IcNumber("S4177294H"), new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                null),
            new Patient(new Name("Charlotte Oliveiro"), new IcNumber("S1729047I"), new Phone("93210283"),
                    new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                null),
            new Patient(new Name("David Li"), new IcNumber("S6810239A"), new Phone("91031282"),
                    new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                null),
            new Patient(new Name("Irfan Ibrahim"), new IcNumber("S6371232I"), new Phone("92492021"),
                    new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                null),
            new Patient(new Name("Roy Balakrishnan"), new IcNumber("S2054290Z"), new Phone("92624417"),
                    new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                null)
        };
    }

    public static Medicine[] getSampleMedicines() {
        return new Medicine[] {
            new Medicine(new MedicineName("Panadol"), new MinimumStockQuantity(100),
                    new PricePerUnit("1"), new SerialNumber("65345"), new Stock(300)),
            new Medicine(new MedicineName("Zyrtec"), new MinimumStockQuantity(100),
                    new PricePerUnit("5"), new SerialNumber("52343"), new Stock(400)),
            new Medicine(new MedicineName("Anarex"), new MinimumStockQuantity(50),
                    new PricePerUnit("2"), new SerialNumber("44363"), new Stock(200)),
            new Medicine(new MedicineName("Tenofovir"), new MinimumStockQuantity(200),
                    new PricePerUnit("10"), new SerialNumber("98345"), new Stock(800))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Patient samplePatient : getSamplePersons()) {
            sampleAb.addPerson(samplePatient);
        }
        for (Medicine sampleMedicine: getSampleMedicines()) {
            sampleAb.addMedicine(sampleMedicine);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a drug allergy list containing the list of strings given.
     */
    public static List<DrugAllergy> getDrugAllergyList(String... strings) {
        return Arrays.stream(strings)
                .map(DrugAllergy::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a disease history list containing the list of strings given.
     */
    public static List<Disease> getDiseaseList(String... strings) {
        return Arrays.stream(strings)
                .map(Disease::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a note list containing the list of strings given.
     */
    public static List<Note> getNoteList(String... strings) {
        return Arrays.stream(strings)
                .map(Note::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a map of SerialNumber to Quantity containing the list of pairs given.
     */
    public static Map<MedicineName, Quantity> getDispensedMedicines(Map.Entry<String, Integer>... entries) {
        Map<MedicineName, Quantity> dispensedMedicines = new HashMap<>();
        Arrays.stream(entries).forEach((entry) -> {
            MedicineName medicineName = new MedicineName(entry.getKey());
            Quantity quantity = new Quantity(entry.getValue().toString());
            dispensedMedicines.put(medicineName, quantity);
        });
        return dispensedMedicines;
    }

}

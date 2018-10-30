package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.appointment.AppointmentsList;
import seedu.address.model.diet.DietCollection;
import seedu.address.model.medicine.PrescriptionList;
import seedu.address.model.tag.Tag;

public class DataGenerator {
    private final NricGenerator nricGenerator = new NricGenerator();
    private final NameGenerator nameGenerator = new NameGenerator();
    private final PhoneGenerator phoneGenerator = new PhoneGenerator();
    private final EmailGenerator emailGenerator = new EmailGenerator();
    private final AddressGenerator addressGenerator = new AddressGenerator();
    private final DrugAllergyGenerator drugAllergyGenerator = new DrugAllergyGenerator();
    private final PrescriptionListGenerator prescriptionListGenerator = new PrescriptionListGenerator();
    private final AppointmentsListGenerator appointmentsListGenerator = new AppointmentsListGenerator();
    private final DietCollectionGenerator dietCollectionGenerator = new DietCollectionGenerator();

    public Nric generateNric() {
        return nricGenerator.generate();
    }

    public Name generateName() {
        return nameGenerator.generate();
    }

    public Phone generatePhone() {
        return phoneGenerator.generate();
    }

    public Email generateEmail() {
        return emailGenerator.generate();
    }

    public Address generateAddress() {
        return addressGenerator.generate();
    }

    public Set<Tag> generateDrugAllergies() {
        return drugAllergyGenerator.generate();
    }

    public PrescriptionList generatePrescriptionList() {
        return prescriptionListGenerator.generate();
    }

    public AppointmentsList generateAppointmentsList() {
        return appointmentsListGenerator.generate();
    }

    public DietCollection generateDietCollection() {
        return dietCollectionGenerator.generate();
    }

    class NricGenerator implements Generator<Nric> {

        @Override
        public Nric generate() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    class NameGenerator implements Generator<Name>{

        @Override
        public Name generate() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    class PhoneGenerator implements Generator<Phone>{

        @Override
        public Phone generate() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    class EmailGenerator implements Generator<Email>{

        @Override
        public Email generate() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    class AddressGenerator implements Generator<Address>{

        @Override
        public Address generate() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    class DrugAllergyGenerator implements Generator<Set<Tag>>{

        @Override
        public Set<Tag> generate() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    class PrescriptionListGenerator implements Generator<PrescriptionList>{

        @Override
        public PrescriptionList generate() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    class AppointmentsListGenerator implements Generator<AppointmentsList>{

        @Override
        public AppointmentsList generate() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    class DietCollectionGenerator implements Generator<DietCollection>{

        @Override
        public DietCollection generate() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    interface Generator<T> {
        T generate();
    }
}

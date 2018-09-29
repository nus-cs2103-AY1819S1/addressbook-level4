package seedu.address.model.patient;

import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;
import java.util.Set;

public class Patient extends Person {
    private String telegramId;
    private MedicalHistory medicalHistory;

    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags, String telegramId) {
        super(name, phone, email, address, tags);
        setTelegramId(telegramId);
    }

    public String getTelegramId() {
        return telegramId;
    }

    public MedicalHistory getMedicalHistory() {
        return medicalHistory;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public void setMedicalHistory(MedicalHistory medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public void addAllergy(String allergy) {
        medicalHistory.addAllergy(allergy);
    }

    public void addCondition(String condition) {
        medicalHistory.addCondition(condition);
    }
}

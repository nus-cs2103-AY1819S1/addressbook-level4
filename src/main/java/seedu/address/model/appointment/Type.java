package seedu.address.model.appointment;

public enum Type {
    PROPAEDEUTIC("PROP"), DIAGNOSTIC("DIAG"), THERAPEUTIC("THP"), SURGICAL("SRG");

    private String abbreviation;

    public String getAbbreviation() {
        return this.abbreviation;
    }

    Type(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}

package seedu.address.model.appointment;

public enum Type {
    PROPAEDEUTIC("PROP"), DIAGNOSTIC("DIAG"), THERAPEUTIC("THP"), SURGICAL("SRG");

    private String abbreviation;

    public String getAbbreviation() {
        return this.abbreviation;
    }

    public Type getType(String abbreviation) {
        if (abbreviation.equals("PROP")) {
            return Type.PROPAEDEUTIC;
        } else if (abbreviation.equals("DIAG")) {
            return Type.DIAGNOSTIC;
        } else if (abbreviation.equals("THP")) {
            return Type.THERAPEUTIC;
        } else {
            return Type.SURGICAL;
        }
    }

    Type(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}

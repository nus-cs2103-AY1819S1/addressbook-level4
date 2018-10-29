package seedu.address.model.notification;

//@@Snookerballs

/**
 * Represents a tip that will be displayed as a notification.
 */
public class Tip {
    private String header = "";
    private String body = "";

    public Tip() {

    }

    public Tip(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String toString() {
        return header + " " + body;
    }

}

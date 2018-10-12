package seedu.address.storage;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import seedu.address.model.event.DateTime;

/**
 * A XmlAdapter that helps to map {@code DateTime} to JAXB format
 */
public class DateTimeAdapter extends XmlAdapter<String, DateTime> {

    public DateTime unmarshal(String v) {
        return new DateTime(LocalDateTime.parse(v));
    }

    public String marshal(DateTime v) {
        return v.value.toString();
    }

}

package seedu.parking.storage;

//import static org.junit.Assert.assertEquals;
//import static seedu.parking.storage.XmlAdaptedCarpark.MISSING_FIELD_MESSAGE_FORMAT;
//import static seedu.parking.testutil.TypicalPersons.BENSON;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.junit.Test;
//
//import seedu.parking.commons.exceptions.IllegalValueException;
//import seedu.parking.model.carpark.Address;
//import seedu.parking.model.carpark.Email;
//import seedu.parking.model.carpark.Name;
//import seedu.parking.model.carpark.Phone;
//import seedu.parking.testutil.Assert;

public class XmlAdaptedCarparkTest {
//    private static final String INVALID_NAME = "R@chel";
//    private static final String INVALID_PHONE = "+651234";
//    private static final String INVALID_ADDRESS = " ";
//    private static final String INVALID_EMAIL = "example.com";
//    private static final String INVALID_TAG = "#friend";
//
//    private static final String VALID_NAME = BENSON.getName().toString();
//    private static final String VALID_PHONE = BENSON.getPhone().toString();
//    private static final String VALID_EMAIL = BENSON.getEmail().toString();
//    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
//    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
//            .map(XmlAdaptedTag::new)
//            .collect(Collectors.toList());
//
//    @Test
//    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
//        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(BENSON);
//        assertEquals(BENSON, carpark.toModelType());
//    }
//
//    @Test
//    public void toModelType_invalidName_throwsIllegalValueException() {
//        XmlAdaptedCarpark carpark =
//                new XmlAdaptedCarpark(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
//        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullName_throwsIllegalValueException() {
//        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
//        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
//    }
//
//    @Test
//    public void toModelType_invalidPhone_throwsIllegalValueException() {
//        XmlAdaptedCarpark carpark =
//                new XmlAdaptedCarpark(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
//        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullPhone_throwsIllegalValueException() {
//        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
//        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
//    }
//
//    @Test
//    public void toModelType_invalidEmail_throwsIllegalValueException() {
//        XmlAdaptedCarpark carpark =
//                new XmlAdaptedCarpark(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
//        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullEmail_throwsIllegalValueException() {
//        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
//        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
//    }
//
//    @Test
//    public void toModelType_invalidAddress_throwsIllegalValueException() {
//        XmlAdaptedCarpark carpark =
//                new XmlAdaptedCarpark(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS);
//        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
//        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
//    }
//
//    @Test
//    public void toModelType_nullAddress_throwsIllegalValueException() {
//        XmlAdaptedCarpark carpark = new XmlAdaptedCarpark(VALID_NAME, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS);
//        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
//        Assert.assertThrows(IllegalValueException.class, expectedMessage, carpark::toModelType);
//    }
//
//    @Test
//    public void toModelType_invalidTags_throwsIllegalValueException() {
//        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
//        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
//        XmlAdaptedCarpark carpark =
//                new XmlAdaptedCarpark(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, invalidTags);
//        Assert.assertThrows(IllegalValueException.class, carpark::toModelType);
//    }

}

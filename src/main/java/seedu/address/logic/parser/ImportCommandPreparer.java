//@@author chantca95
package seedu.address.logic.parser;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public class ImportCommandPreparer {
    ArrayList<Person> persons = new ArrayList<>();
    
    public ImportCommand init() throws ParseException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select .csv file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TEXT", "*.txt"));
        File file = fileChooser.showOpenDialog(new Stage());
        
        return parseFile(file);
    } 
    
    public ImportCommand parseFile(File file) throws ParseException {
        
        FileReader fr = null;

        boolean hasContactWithInvalidField = false;
        boolean hasContactWithoutName = false;
        
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException fnfe) {
            throw new ParseException("File not found");
        }

        BufferedReader br = new BufferedReader(fr);

        try {
            String line = br.readLine();
            while (line != null) {
                String[] attributes = line.split(",", -1);
                
                if(attributes[0].equalsIgnoreCase("Name") ||
                attributes[0].equalsIgnoreCase("Name:")){ // ignore headers
                    line = br.readLine();
                    continue;
                }

                int numAttributes = attributes.length;
                
                if(hasInvalidContact(attributes, numAttributes)) {
                    hasContactWithoutName = true;
                    line = br.readLine();
                    continue;
                }
                
                Name name = null;
                Optional<Phone> phone = Optional.empty();
                Optional<Email> email = Optional.empty();
                Optional<Address> address = Optional.empty();
                
                try {
                    name = ParserUtil.parseName(attributes[0]);
                    if (!attributes[1].matches("")) {
                        phone = Optional.of(ParserUtil.parsePhone(attributes[1]));
                    }
                    if (!attributes[2].matches("")) {
                        email = Optional.of(ParserUtil.parseEmail(attributes[2]));
                    }
                    if (!attributes[3].matches("")) {
                        address = Optional.of(ParserUtil.parseAddress(attributes[3]));
                    }
                } catch (ParseException pe) {
                    hasContactWithInvalidField = true;
                    line = br.readLine();
                    continue;
                }

                ArrayList<String> tags = new ArrayList<>();
                //Check for tags 
                if (numAttributes >= 5) {
                    for (int i = 4; i < numAttributes; i++) {
                        if (!attributes[i].matches("")) {
                            tags.add(attributes[i]);
                        }
                    }
                }
                
                Set<Tag> tagList = ParserUtil.parseTags(tags);
                persons.add(new Person(name, phone, email, address, tagList));
                line = br.readLine();
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return new ImportCommand(persons, hasContactWithInvalidField, hasContactWithoutName);
    }
    /**
        this method checks if the compulsory name field is filled up
        Note: it is okay if the fields are filled up improperly, ie an email without @,
        The format checking routine associated with AddCommand will handle that and alert the user
    */
    private boolean hasInvalidContact(String[] attributes, int numAttributes) {
        if(attributes[0].matches("")) {
            return true;
        }
        return false;
    }
}

package seedu.address.ui;

import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.LoginEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class LoginForm extends UiPart<Region> {
	
	public static final String USERNAME = "Username: ";
	public static final String PASSWORD = "Password: ";
	
	private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
	private static final String FXML = "LoginForm.fxml";
	
	@FXML
	private Label usernameLabel;
	
	@FXML
	private Label passwordLabel;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private TextField passwordField;
	
	@FXML
	private Button loginButton;
	
	@FXML
	private Label signInResult;
	
	public LoginForm() {
		super(FXML);
		usernameLabel.setText(USERNAME);
		passwordLabel.setText(PASSWORD);
		
		loginButton.setOnAction(e -> raise(new LoginEvent(usernameField.getText(), passwordField.getText())));
		registerAsAnEventHandler(this);
	}
}

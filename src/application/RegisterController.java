package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.DBWorker;

public class RegisterController extends Controller {
	
	private DBWorker worker = new DBWorker();
	private int index = 0;
	private ArrayList<Image> images = new ArrayList<Image>();
	private String[] keys = new String[3]; 
	private String nameImageOfCaptcha = "";
	
	@FXML private Label successLabel = new Label();
	@FXML private Label errorLabel = new Label();
	@FXML private ImageView errorCaptcha = new ImageView();
	@FXML private ImageView wrongPassword = new ImageView();
	
	@FXML private ImageView captchaImage = new ImageView();
	@FXML private JFXTextField usernameField = new JFXTextField();
	@FXML private JFXTextField emailField = new JFXTextField();
	@FXML private JFXPasswordField passwordField = new JFXPasswordField();
	@FXML private JFXPasswordField confirmPasswordField = new JFXPasswordField();
	@FXML private TextField captchaText = new TextField();
	
	public RegisterController() {
		images.add(new Image("file:resources/images/recaptcha/google.jpg"));
		images.add(new Image("file:resources/images/recaptcha/smwm.png"));
		images.add(new Image("file:resources/images/recaptcha/w68hp.png"));
		
		keys[0] = "google";
		keys[1] = "smwm";
		keys[2] = "w68hp";
		
		captchaImage.setImage(images.get(index));
		nameImageOfCaptcha = keys[0];
	}

	@FXML
	public void refresh() {
		Image img = images.get(index);
		captchaImage.setImage(img);
		nameImageOfCaptcha = keys[index];
		
		index++;
		if(index == 3)
			index -= 3;
	}
	
	@FXML
	public void back() {
		this.main.getStage().setScene(this.main.scene1);
		this.main.getStage().centerOnScreen();
	}
	
	@FXML
	public void exit() {
		this.main.getStage().close();
	}
	
	@FXML
	public void create() {
		//Checking the Captcha
		if(checkCaptcha()) {
			errorCaptcha.setStyle("-fx-opacity: 1;");
			successLabel.setStyle("-fx-opacity: 0;");
			refresh();
			return;
		} else {
			errorCaptcha.setStyle("-fx-opacity: 0;");
		}
		
		//Comparing two password field
		if(checkPasswordField()) {
			wrongPassword.setStyle("-fx-opacity: 1;");
			successLabel.setStyle("-fx-opacity: 0;");
			return;
		} else {
			wrongPassword.setStyle("-fx-opacity: 0;");
		}
		
		//Checking to fill all field
		if(checkAllFields()) {
			errorLabel.setStyle("-fx-opacity: 1;");
			successLabel.setStyle("-fx-opacity: 0;");
			errorLabel.setText("Fill in all the fields");
			return;
		} else {
			successLabel.setStyle("-fx-opacity: 1;");
			errorLabel.setStyle("-fx-opacity: 0;");
		}
		
		//Checking Email
		if(checkEmail()) {
			errorLabel.setStyle("-fx-opacity: 1;");
			successLabel.setStyle("-fx-opacity: 0;");
			errorLabel.setText("Invalid email");
			return;
		} else {
			successLabel.setStyle("-fx-opacity: 1;");
			errorLabel.setStyle("-fx-opacity: 0;");
		}
		
		//Checking username
		if(checkUserName()) {
			errorLabel.setStyle("-fx-opacity: 1;");
			successLabel.setStyle("-fx-opacity: 0;");
			errorLabel.setText("Username must contain more than 3 characters");
		} else {
			//User validation
			if(isUserExists()) {
				errorLabel.setStyle("-fx-opacity: 1;");
				successLabel.setStyle("-fx-opacity: 0;");
				errorLabel.setText("This user already exists, change username.");
			} else {
				successLabel.setStyle("-fx-opacity: 1;");
				errorLabel.setStyle("-fx-opacity: 0;");
				addInDB();
				AlertBox.display("Registration", "You have successfully registered!");
				back();
			}
		}
	}
	
	public boolean checkUserName() {
		return !(usernameField.getText().length() > 3);
	}
	
	public boolean checkEmail() {
		return !emailField.getText().contains("@");
	}
	
	public boolean checkCaptcha() {
		return !captchaText.getText().toLowerCase().equals(nameImageOfCaptcha);
	}
	
	public boolean checkPasswordField() {
		return !passwordField.getText().equals(confirmPasswordField.getText());
	}
	
	public boolean checkAllFields() {
		return usernameField.getText().equals("") || emailField.getText().equals("") || passwordField.getText().equals("");
	}
	
	public void addInDB() {
		try {
			Statement statement = worker.getConnection().createStatement();
			String adding = "INSERT INTO users(username, email, password) VALUES ('" + usernameField.getText() + "', '" + emailField.getText() + "', '" + passwordField.getText() + "');";                                    
			statement.execute(adding);
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public boolean isUserExists() {
		String query = "select 1 from users where username = ?";  
		try {
			
			PreparedStatement statement = worker.getConnection().prepareStatement(query);
			statement.setString(1, usernameField.getText());
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) 
                return true;
            else 
            	return false;
            
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR 404 ");
			return true;
		}
	}
}

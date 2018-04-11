package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import models.User;
import util.DBWorker;

public class LoginController extends Controller {
	
	DBWorker worker = new DBWorker();
	private User user;
	private int id_user;
	
	@FXML private AnchorPane pane = new AnchorPane();
	@FXML private Label successLabel = new Label();
	@FXML private Label fieldError = new Label();
	@FXML private JFXTextField usernameField = new JFXTextField();
	@FXML private JFXPasswordField passwordField = new JFXPasswordField();
	
	public void initialize() {
		AlertBox.addDraggableNode(pane);
	}
	
	@FXML
	public void exit() {
		this.main.getStage().close();
	}
	
	@FXML
	public void signIn() {
		if(!usernameField.getText().equals("") && !passwordField.getText().equals("")) {
			if (isUserExists()) {
				fieldError.setStyle("-fx-opacity: 0;");
				//successLabel.setStyle("-fx-opacity: 1;");
				toMoviesMenu();
			} else {
				successLabel.setStyle("-fx-opacity: 0;");
				fieldError.setStyle("-fx-opacity: 1;");
				fieldError.setText("Invalid username or password");
			}
		} else {
			successLabel.setStyle("-fx-opacity: 0;");
			fieldError.setStyle("-fx-opacity: 1;");
			fieldError.setText("Fill in all the fields");
		}
	}
	
	@FXML
	public void toMoviesMenu() {
		this.user = new User(id_user, usernameField.getText(), passwordField.getText());
		this.main.moviesMenuController.setUser(user);
		this.main.bookingController.setUser(user);
		this.main.getStage().setScene(this.main.scene4);
		this.main.getStage().centerOnScreen();
		usernameField.setText("");
		passwordField.setText("");
	}
	
	@FXML 
	public void toRegister() {
		this.main.getStage().setScene(this.main.scene2);
		this.main.getStage().centerOnScreen();
	}
	
	@FXML 
	public void toAdmin() {
		this.main.getStage().setScene(this.main.scene3);
		this.main.getStage().centerOnScreen();
	}
	
	public boolean isUserExists() {
		String query = "select idusers from users where username = ? and password = ?";  
		try {
			
			PreparedStatement statement = worker.getConnection().prepareStatement(query);
			statement.setString(1, usernameField.getText());
			statement.setString(2, passwordField.getText());
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) {
				id_user = rs.getInt("idusers");
                return true;
			} else { 
            	return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR 404 ");
			return false;
		}
	}
	
	
}

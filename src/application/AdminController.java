package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import util.DBWorker;

public class AdminController extends Controller {
	
	DBWorker worker = new DBWorker();
	@FXML private Label successLabel = new Label();
	@FXML private Label fieldError = new Label();
	@FXML private JFXTextField loginField = new JFXTextField();
	@FXML private JFXPasswordField passwordField = new JFXPasswordField();
	
	@FXML
	public void exit() {
		this.main.getStage().close();
	}
	
	@FXML
	public void back() {
		this.main.getStage().setScene(this.main.scene1);
		this.main.getStage().centerOnScreen();
	}
	
	@FXML
	public void signIn() {
		if(!loginField.getText().equals("") && !passwordField.getText().equals("")) {
			if (isAdminExists()) {
				fieldError.setStyle("-fx-opacity: 0;");
				//successLabel.setStyle("-fx-opacity: 1;");
				toAddMovie();
			} else {
				successLabel.setStyle("-fx-opacity: 0;");
				fieldError.setStyle("-fx-opacity: 1;");
				fieldError.setText("Invalid login or password");
			}
		} else {
			successLabel.setStyle("-fx-opacity: 0;");
			fieldError.setStyle("-fx-opacity: 1;");
			fieldError.setText("Fill in all the fields");
		}
	}
	
	public boolean isAdminExists() {
		String query = "select 1 from admins where login = ? and password = ?";  
		try {
			
			PreparedStatement statement = worker.getConnection().prepareStatement(query);
			statement.setString(1, loginField.getText());
			statement.setString(2, passwordField.getText());
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) 
                return true;
            else 
            	return false;
            
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR 404 ");
			return false;
		}
	}
	
	@FXML
	public void toAddMovie() {
		this.main.getStage().setScene(this.main.scene6);
		this.main.getStage().centerOnScreen();
		loginField.setText("");
		passwordField.setText("");
	}
}

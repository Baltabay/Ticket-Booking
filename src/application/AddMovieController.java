package application;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import util.DBWorker;

public class AddMovieController extends Controller {
	
	private DBWorker worker = new DBWorker();
	private String poster_name = "";
	
	
	@FXML boolean isSelected = false;
	@FXML AnchorPane pane = new AnchorPane();
	@FXML Label wg = new Label();
	@FXML ImageView poster = new ImageView();
	@FXML JFXTextField name = new JFXTextField();
	@FXML JFXTextField director = new JFXTextField();
	@FXML JFXTextField actors = new JFXTextField();
	@FXML JFXTextField genre = new JFXTextField();
	@FXML JFXTextField id = new JFXTextField();
	@FXML JFXTextArea description = new JFXTextArea();
	@FXML JFXDatePicker premiere = new JFXDatePicker();
	@FXML JFXTimePicker seance1 = new JFXTimePicker();
	@FXML JFXTimePicker seance2 = new JFXTimePicker();
	@FXML JFXTimePicker seance3 = new JFXTimePicker();
	@FXML JFXTimePicker seance4 = new JFXTimePicker();
	@FXML JFXTimePicker seance5 = new JFXTimePicker();
	
	public void initialize() {
		AlertBox.addDraggableNode(pane);
	}
	
	@FXML
	public void exit() {
		this.main.getStage().close();
	}
	
	@FXML
	public void minimize() {
		this.main.getStage().setIconified(true);;
	}
	
	@FXML
	public void fileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
		         );
		File selectedFile = fileChooser.showOpenDialog(this.main.getStage());
		
		if (selectedFile != null) {
			isSelected = true;
			Image image = new Image(selectedFile.toURI().toString());
			poster.setImage(image);
			poster_name = selectedFile.getName();
		} else {
			isSelected = false;
		}
	}

	@FXML
	public void submit() {
		System.out.println();
		if(name.getText() != null && director.getText() != null && actors.getText() != null && genre.getText() != null && id.getText() != null && description.getText() != null && premiere.getValue() != null && seance1.getValue() != null) {
			if (isSelected) {
				wg.setStyle("-fx-opacity: 0;");
				addInDB();
				AlertBox.display("Add movie", "Movie successfully uploaded!");
				back();
			} else {
				wg.setText("Select poster");
				wg.setStyle("-fx-opacity: 1;");
			}
		} else {
			wg.setText("Fill all fields");
			wg.setStyle("-fx-opacity: 1;");
		}
	}
	
	public void addInDB() {
		String query = "insert into movies(id, name, genre, description, producer, actors, premiere, poster_name) values (?, ?, ?, ?, ?, ?, ?, ?)";  
		
		try {
			PreparedStatement statement = worker.getConnection().prepareStatement(query);
			statement.setString(1, id.getText());
			statement.setString(2, name.getText());
			statement.setString(3, genre.getText());
			statement.setString(4, description.getText());
			statement.setString(5, director.getText());
			statement.setString(6, actors.getText());
			statement.setString(7, premiere.getValue().toString());
			statement.setString(8, poster_name);
			
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		String query1 = "insert into seances(id, seance1, seance2, seance3, seance4, seance5) values (?, ?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement statement = worker.getConnection().prepareStatement(query1);
			statement.setString(1, id.getText());
			
			if(seance1.getValue() != null) {
				statement.setString(2, seance1.getValue().toString());
			} else {
				statement.setString(2, null);
			}
			
			if(seance2.getValue() != null) {
				statement.setString(3, seance2.getValue().toString());
			} else {
				statement.setString(3, null);
			}
			
			if(seance3.getValue() != null) {
				statement.setString(4, seance3.getValue().toString());
			} else {
				statement.setString(4, null);
			}
			
			if(seance4.getValue() != null) {
				statement.setString(5, seance4.getValue().toString());
			} else {
				statement.setString(5, null);
			}
			
			if(seance5.getValue() != null) {
				statement.setString(6, seance5.getValue().toString());
			} else {
				statement.setString(6, null);
			}
			
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
	}

	@FXML
	public void back() {
		this.main.getStage().setScene(this.main.scene1);
		this.main.getStage().centerOnScreen();
	}

}

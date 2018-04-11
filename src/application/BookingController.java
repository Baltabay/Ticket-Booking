package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import models.Movie;
import models.Ticket;
import models.User;
import util.DBWorker;

public class BookingController extends Controller {

	private DBWorker worker = new DBWorker();
	private User user;
	private String id_movie;
	public List<Movie> listOfMovies;
	
	@FXML Label wrong = new Label();
	@FXML GridPane cinemaHall = new GridPane();
	@FXML ToggleButton[][] chairs = new ToggleButton[7][12];
	@FXML JFXDatePicker datePicker = new JFXDatePicker();
	@FXML JFXComboBox<String> filmsBox = new JFXComboBox<String>();
	@FXML JFXComboBox<String> timesBox = new JFXComboBox<String>();
	
	public void initialize() {
		for (int row = 0; row < chairs.length; row++) {
			for (int column = 0; column < chairs[0].length; column++) {
				ToggleButton chair = new ToggleButton();
				chairs[row][column] = chair;
				GridPane.setHalignment(chair, HPos.CENTER);
				GridPane.setValignment(chair, VPos.CENTER);
				cinemaHall.add(chair, column, row);
			}
		}
		
		
	}
	
	@FXML
	public void exit() {
		this.main.getStage().close();
	}
	
	@FXML
	public void minimize() {
		this.main.getStage().setIconified(true);
	}
	
	@FXML
	public void back() {
		this.main.getStage().setScene(this.main.scene4);
		this.main.getStage().centerOnScreen();
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@FXML
	public void date() {
		filmsBox.getItems().clear();
		LocalDate date = datePicker.getValue();
		
		for (Iterator<Movie> iterator = listOfMovies.iterator(); iterator.hasNext();) {
			Movie movie = (Movie) iterator.next();
			LocalDate dateOfMovie = LocalDate.parse(movie.getPremiere());
			if(date.isAfter(dateOfMovie) || date.isEqual(dateOfMovie) ) {
				filmsBox.getItems().add(movie.getName());
			}
		}
	}
	
	@FXML
	public void film() {
		timesBox.getItems().clear();
		String name_movie = filmsBox.getValue();
		for (Iterator<Movie> iterator = listOfMovies.iterator(); iterator.hasNext();) {
			Movie movie = (Movie) iterator.next();
			if(movie.getName().equals(name_movie)) {
				id_movie = movie.getId();
				String query = "select * from seances where id = ?";  
				try {
					
					PreparedStatement statement = worker.getConnection().prepareStatement(query);
					statement.setString(1, movie.getId());
					ResultSet rs = statement.executeQuery();
					
					if(rs.next()) {
						if(rs.getTime("seance1") != null) {
							timesBox.getItems().add(rs.getTime("seance1").toString());
						}
						if(rs.getTime("seance2") != null) {
							timesBox.getItems().add(rs.getTime("seance2").toString());
						}
						if(rs.getTime("seance3") != null) {
							timesBox.getItems().add(rs.getTime("seance3").toString());
						}
						if(rs.getTime("seance4") != null) {
							timesBox.getItems().add(rs.getTime("seance4").toString());
						}
						if(rs.getTime("seance5") != null) {
							timesBox.getItems().add(rs.getTime("seance5").toString());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void checkTickets() {
		initialize();
		String query = "select seat from tickets where date = ? and time = ? and id_movie = ?";  
		try {
			PreparedStatement statement = worker.getConnection().prepareStatement(query);
			statement.setString(1, datePicker.getValue().toString());
			statement.setString(2, timesBox.getValue());
			statement.setString(3, id_movie);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				String seat = rs.getString("seat");
				String[] st = seat.split(Pattern.quote("."));
				int row = Math.abs(Integer.parseInt(st[0])-7);
				int numberOfSeat = Integer.parseInt(st[1]) - 1;
				chairs[row][numberOfSeat].setDisable(false);
				chairs[row][numberOfSeat].setStyle("-fx-graphic: url('file:///C:/Users/HP/Desktop/FinalProject/FinalProject/resources/images/cinema-chair2.png');");
			}
			
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	 
	public void book() {
		if(datePicker.getValue() != null && filmsBox.getValue() != null && timesBox.getValue() != null) {
			if(existsSelected()) {
				wrong.setStyle("-fx-opacity: 0;");
				String allSeats = "";
				for (int row = 0; row < chairs.length; row++) {
					for (int column = 0; column < chairs[0].length; column++) {
						if(chairs[row][column].isSelected()) {
							String seat = Math.abs(row - 7) + "." + (column+1);
							allSeats += seat + " ";
							
							String query = "insert into tickets(seat, time, date,  id_movie, id_user) values (?, ?, ?, ?, ?)";  
							
							try {
								PreparedStatement statement = worker.getConnection().prepareStatement(query);
								statement.setString(1, seat);
								statement.setString(2, timesBox.getValue());
								statement.setString(3, datePicker.getValue().toString());
								statement.setString(4, id_movie);
								statement.setInt(5, user.getId());
								
								statement.executeUpdate();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							};
						}
					}
				}
				AlertBox.display("Tickets", "You have booked a ticket, click \"OK\" to receive your ticket");
				TicketBox.display(new Ticket(1, allSeats, timesBox.getValue(), datePicker.getValue().toString(), id_movie, user.getId()));
				back();
			} else {
				wrong.setText("Select the seat");
				wrong.setStyle("-fx-opacity: 1;");
			}
		} else {
			wrong.setText("Fill all fields");
			wrong.setStyle("-fx-opacity: 1;");
		}
	}
	
	public void setListOfMovies(List<Movie> listOfMovies) {
		this.listOfMovies = listOfMovies;
	}
	
	public boolean existsSelected() {
		for (int row = 0; row < chairs.length; row++) {
			for (int column = 0; column < chairs[0].length; column++) {
				if(chairs[row][column].isSelected()) {
					return true;
				}
			}
		}
		return false;
	}
}

package application;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import util.DBWorker;
import models.Movie;
import models.Ticket;
import models.User;

public class MoviesMenuController extends Controller {
	
	public  User user;
	private DBWorker worker = new DBWorker();
	
	@FXML public Pane draggableNode = new Pane();
	@FXML public VBox vbox = new VBox();
	@FXML public Label label = new Label();
	
	public AnchorPane pane;
	public List<Movie> listOfMovies = new ArrayList<Movie>();
	public List<Ticket> listOfTicket = new ArrayList<Ticket>();
	
	public void initialize() {
		
		AlertBox.addDraggableNode(draggableNode);
		movies();
	}
	
	@FXML
	public void exit() {
		this.main.getStage().close();
	}
	
	@FXML
	public void signOut() {
		this.main.getStage().setScene(this.main.scene1);
		this.main.getStage().centerOnScreen();
	}
	
	@FXML
	public void movies() {
		vbox.getChildren().clear();
		
		listOfMovies.clear();
		fillMovies();
		Collections.reverse(listOfMovies);
		
		label.setText("Movies");
		
		Iterator<Movie> iter = listOfMovies.iterator();
		while(iter.hasNext()) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/application/MovieCard.fxml"));
				pane = (AnchorPane) loader.load();
				
				MovieCardController movieCardController = loader.getController();
				movieCardController.setMain(this.main);
				movieCardController.setMovie(iter.next());
				vbox.getChildren().add(pane);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void fillMovies() {
		String query = "select * from movies";
		
		try {
			Statement statement = worker.getConnection().createStatement();
			ResultSet results = statement.executeQuery(query);
			
			while(results.next()) {
				String id = results.getString("id");
				String name = results.getString("name");
				String genre = results.getString("genre");
				String description = results.getString("description");
				String producer = results.getString("producer");
				String actors = results.getString("actors");
				String premiere = results.getDate("premiere").toString();
				String poster_name = results.getString("poster_name");
				
				Movie movie = new Movie(id, name, genre, description, producer, actors, premiere, poster_name);
				listOfMovies.add(movie);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void reservedTickets() {
		vbox.getChildren().clear();
		
		listOfTicket.clear();
		fillTickets();
		
		
		label.setText("Tickets");
		
		Iterator<Ticket> iter = listOfTicket.iterator();
		while(iter.hasNext()) {
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/application/TicketsView.fxml"));
				pane = (AnchorPane) loader.load();
				
				TicketViewController ticketViewController = loader.getController();
				ticketViewController.setMain(this.main);
				ticketViewController.setTicket(iter.next(), this);
				vbox.getChildren().add(pane);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void fillTickets() {
		String query = "select * from tickets where id_user = ?";
		
		try {
			PreparedStatement statement = worker.getConnection().prepareStatement(query);
			statement.setInt(1, user.getId());
			ResultSet results = statement.executeQuery();
			
			while(results.next()) {
				int id = results.getInt("id_ticket");
				String seat = results.getString("seat");
				String time = results.getString("time");
				String date = results.getString("date");
				String id_movie = results.getString("id_movie");
				int id_user = results.getInt("id_user");
				
				Ticket ticket = new Ticket(id, seat, time, date, id_movie, id_user);
				listOfTicket.add(ticket);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void minimize() {
		this.main.getStage().setIconified(true);;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void getMovies() {
		this.main.bookingController.setListOfMovies(this.listOfMovies);
	}
	
	public void removeTicket(int num) {
		String query = "delete from tickets where id_ticket = ?";
		
		try {
			PreparedStatement statement = worker.getConnection().prepareStatement(query);
			statement.setInt(1, num);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listOfTicket.clear();
		fillTickets();
		reservedTickets();
	}
	
}

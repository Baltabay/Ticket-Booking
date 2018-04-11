package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import models.Movie;

public class MovieCardController extends Controller {

	@FXML private Label name = new Label();
	@FXML private Text producer = new Text();
	@FXML private Text genre = new Text();
	@FXML private Text actors = new Text();
	@FXML private Text premiere = new Text();
	@FXML private Text description = new Text();
	@FXML private ImageView poster = new ImageView();


	@FXML
	public void exit() {
		this.main.getStage().close();
	}
	
	public void setMovie(Movie movie) {
		this.name.setText(movie.getName());;
		this.producer.setText(movie.getProducer());;
		this.genre.setText(movie.getGenre());;
		this.actors.setText(movie.getActors());;
		this.premiere.setText(movie.getPremiere());;
		this.description.setText(movie.getDescription());;
		this.poster.setImage(new Image("file:///C:/Users/HP/Desktop/FinalProject/FinalProject/resources/Posters/"+movie.getPoster_name()));
	
	}
	
	public void toBookings() {
		this.main.getStage().setScene(this.main.scene5);
		this.main.getStage().centerOnScreen();
	}
}

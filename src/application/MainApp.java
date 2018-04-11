package application;


import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {
	
	//Needed attributes
	private Stage window;
	protected Scene scene1, scene2, scene3, scene4, scene5, scene6;
	public MoviesMenuController moviesMenuController;
	public BookingController bookingController;
	
	@Override
    public void start(Stage primaryStage) {
		//Editing main Stage
        this.window = primaryStage;
        this.window.setTitle("MyTestProject");
        this.window.getIcons().add(new Image("file:resources/images/logo.png"));
        this.window.initStyle(StageStyle.TRANSPARENT);
        
                
        /********* Creating Scene ¹1 (Login application) *********/
        try {
        	//Loading view
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/LoginView.fxml"));
        	//Putting loads in a scene
        	scene1 = new Scene(loader.load());
        	//Binding with controller
			LoginController loginController = loader.getController();
			loginController.setMain(this);
			loginController.initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
      /******** Creating Scene ¹2 (Register application) *********/
        try {
        	//Loading view
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/RegisterView.fxml"));
        	//Putting loads in a scene
        	scene2 = new Scene(loader.load());
        	//Binding with controller
			RegisterController registController = loader.getController();
			registController.setMain(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
      /******** Creating Scene ¹3 (Admin Login) *********/
        try {
        	//Loading view
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/AdminView.fxml"));
        	//Putting loads in a scene
        	scene3 = new Scene(loader.load());
        	//Binding with controller
			AdminController adminController = loader.getController();
			adminController.setMain(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        /******** Creating Scene ¹4 (Movies) *********/
        try {
        	//Loading view
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/MoviesMenu.fxml"));
        	//Putting loads in a scene
        	scene4 = new Scene(loader.load());
        	//Binding with controller
        	moviesMenuController = loader.getController();
        	moviesMenuController.setMain(this);;
        	moviesMenuController.initialize();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
        /******** Creating Scene ¹5 (Booking) *********/
        try {
        	//Loading view
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Booking.fxml"));
        	//Putting loads in a scene
        	scene5 = new Scene(loader.load());
        	scene5.getStylesheets().add((getClass().getResource("/application/style.css")).toExternalForm());
        	//Binding with controller
        	bookingController = loader.getController();
			bookingController.setMain(this);
			bookingController.initialize();
			moviesMenuController.getMovies();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        /******** Creating Scene ¹6 (ADD Movies) *********/
        try {
        	//Loading view
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/AddMovie.fxml"));
        	//Putting loads in a scene
        	scene6 = new Scene(loader.load());
        	//Binding with controller
			AddMovieController addMovieController = loader.getController();
			addMovieController.setMain(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        //Putting first scene in the window
        this.window.setScene(scene1);
        this.window.show();
    }
		
	//Getter Stage
	public Stage getStage() {
		return this.window;
	}
	
	//Main method
	public static void main(String[] args) {
        launch(args);
    }
}

package application;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertBox {
	static double initialX;
	static double initialY;
	public static void display(String title, String message) {
		final Stage window = new Stage();
		window.setTitle("MyTestProject");
		window.initModality(Modality.APPLICATION_MODAL);
        window.getIcons().add(new Image("file:resources/images/logo.png"));
        window.initStyle(StageStyle.TRANSPARENT);
        
        AnchorPane layout = new AnchorPane();
        layout.setStyle("-fx-background-color: #D9DADC");
        
        AnchorPane pane = new AnchorPane();
        pane.setStyle("-fx-background-color:  #84207c");
        
        pane.setPrefSize(600, 50);
        
        Label tle = new Label(title);
        tle.setPrefSize(110, 31);
        
        tle.setStyle("-fx-font-size:  20px; -fx-font-weight: bold; -fx-text-fill: white;");
        tle.setLayoutX(14);
        tle.setLayoutY(9);
        
        ImageView image = new ImageView(new Image("file:resources/images/close-button.png"));
        image.setFitWidth(20);
        image.setFitHeight(20);
        Button closeButton = new Button("");
        closeButton.setGraphic(image);
        closeButton.setPrefSize(20, 20);
        closeButton.setStyle("-fx-background-color: transparent");
        
        closeButton.setLayoutX(552);
        closeButton.setLayoutY(10);
        
        closeButton.setOnAction(event -> close(window));
        closeButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
            closeButton.setStyle("-fx-background-color: #D5D3D6;");
            }
          });
        closeButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
            closeButton.setStyle("-fx-background-color: transparent");
            }
          });
        
        pane.getChildren().addAll(closeButton, tle);
        
        ImageView image1 = new ImageView(new Image("file:resources/images/checked (1).png"));
        image1.setFitWidth(64);
        image1.setFitHeight(64);
        image1.setLayoutX(54);
        image1.setLayoutY(103);
        
        Text mes = new Text(message);
        mes.setWrappingWidth(350);
        
        mes.setStyle("-fx-font-size:  25px;");
        mes.setLayoutX(154);
        mes.setLayoutY(110);
        
        Button ok = new Button("Ok");
        ok.setStyle("-fx-background-color: #84207c; -fx-background-radius: 2em; -fx-text-fill: white; -fx-font-size:  20px; -fx-font-weight: bold");
        ok.setPrefSize(110, 43);
        ok.setLayoutX(451);
        ok.setLayoutY(221);
        ok.setOnAction(event -> close(window));
        
        ok.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
              ok.setStyle("-fx-background-color: #ff00f6; -fx-background-radius: 2em; -fx-text-fill: white; -fx-font-size:  20px; -fx-font-weight: bold");
            }
          });
        ok.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
              ok.setStyle("-fx-background-color: #84207c; -fx-background-radius: 2em; -fx-text-fill: white; -fx-font-size:  20px; -fx-font-weight: bold");
            }
          });
        
        addDraggableNode(pane);
        layout.getChildren().addAll(pane, image1, mes, ok);
        
        Scene scene = new Scene(layout, 600, 278);
        window.setScene(scene);
        window.showAndWait();
	}
	
	public static void close(Stage window) {
		window.close();
	}
	
	public static void addDraggableNode(final Node node) {
	    node.setOnMousePressed(new EventHandler<MouseEvent>() {
	    	
	        @Override
	        public void handle(MouseEvent me) {
	            if (me.getButton() != MouseButton.MIDDLE) {
	                initialX = me.getSceneX();
	                initialY = me.getSceneY();
	            }
	        }
	    });

	    node.setOnMouseDragged(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent me) {
	            if (me.getButton() != MouseButton.MIDDLE) {
	                node.getScene().getWindow().setX(me.getScreenX() - initialX);
	                node.getScene().getWindow().setY(me.getScreenY() - initialY);
	            }
	        }
	    });
	}
}

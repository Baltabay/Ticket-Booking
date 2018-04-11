package application;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import models.Ticket;

public class TicketBox {
	public static void display(Ticket ticket) {
		final Stage window = new Stage();
		window.setTitle("MyTestProject");
		window.initModality(Modality.APPLICATION_MODAL);
        window.getIcons().add(new Image("file:resources/images/logo.png"));
        window.initStyle(StageStyle.TRANSPARENT);
		AnchorPane layout = new AnchorPane();
        
		AnchorPane pane = new AnchorPane();
		
		ImageView image = new ImageView(new Image("file:resources/images/TicketView.png"));
		image.setFitWidth(816);
		image.setFitHeight(264);
		
		Text name = new Text(ticket.getName());
		name.setLayoutX(300);
		name.setLayoutY(70);
		name.setFont(Font.font("Bold", 41));
		name.setTextAlignment(TextAlignment.LEFT);
		name.setWrappingWidth(215);
		name.setFill(Paint.valueOf("#84207c"));
		
		if(ticket.getName().length() > 21) {
			name.setFont(Font.font("Bold", 31));
		}
		
		
		Label date = new Label(ticket.getDate());
		Label time = new Label(ticket.getTime());
		Label seat = new Label(ticket.getStringSeat());
		
		date.setPrefSize(216, 38);
		time.setPrefSize(216, 38);
		seat.setPrefSize(216, 38);
		
		date.setLayoutX(521);
		date.setLayoutY(41);
		date.setFont(Font.font(17));
		date.setAlignment(Pos.CENTER);
		
		time.setLayoutX(521);
		time.setLayoutY(113);
		time.setFont(Font.font(17));
		time.setAlignment(Pos.CENTER);
		
		seat.setLayoutX(521);
		seat.setLayoutY(193);
		seat.setFont(Font.font(17));
		seat.setAlignment(Pos.CENTER);
		
		pane.getChildren().addAll(image, date, time, seat, name);
		
		Button button = new Button("Save as PNG");
		button.setStyle("-fx-background-color: transparent;\r\n" + 
				        "-fx-border-color: #84207c;\r\n" + 
				        "-fx-border-radius: 2em;");
		button.setTextFill(Paint.valueOf("#84207c"));;
		button.setFont(Font.font(17));
		button.setPrefWidth(143);
		button.setPrefHeight(42);
		button.setLayoutX(336);
		button.setLayoutY(270);
		
		button.setOnAction(event -> saveScreenshot(pane, window));
		
		layout.getChildren().addAll(pane, button);
		
		

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout, 816, 317);
        window.setScene(scene);
        window.showAndWait();
    }
	
	public static void saveScreenshot(AnchorPane pane, Stage window) {
		WritableImage image = pane.snapshot(new SnapshotParameters(), null);
		
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Image Files", "*.png"));
        File file = fileChooser.showSaveDialog(window);
        
        
        if (file != null) {
            try {
            	ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        window.close();
	}

}

package application;


import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.Ticket;

public class TicketViewController extends Controller{
	private int num;
	MoviesMenuController cont;
	
	@FXML private Label number = new Label();
	@FXML private Label seat = new Label();
	@FXML private Label time = new Label();
	@FXML private Label date= new Label();
	@FXML private Label name = new Label();
	
	public void setTicket(Ticket ticket, MoviesMenuController cont) {
		this.num = ticket.getId();
		this.number.setText(String.valueOf(ticket.getId()));
		String seat = ticket.getSeat();
		String[] st = seat.split(Pattern.quote("."));
		int row = Integer.parseInt(st[0]);
		int numberOfSeat = Integer.parseInt(st[1]);
		this.seat.setText("Row " + row + " seat " + numberOfSeat);
		this.time.setText(ticket.getTime());
		this.date.setText(ticket.getDate());
		this.name.setText(ticket.getName());
		this.cont = cont;
	}
	
	public void removeTicket() {
		cont.removeTicket(num);
		
	}
}

package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

import util.DBWorker;

public class Ticket {
	private int id;
	private String seat;
	private String time;
	private String date;
	private String id_movie;
	private int id_user;
	DBWorker worker = new DBWorker();
	
	public Ticket(int id, String seat, String time, String date, String id_movie, int id_user) {
		super();
		this.id = id;
		this.seat = seat;
		this.time = time;
		this.date = date;
		this.id_movie = id_movie;
		this.id_user = id_user;
	}

	public int getId() {
		return id;
	}

	public String getSeat() {
		return seat;
	}

	public String getTime() {
		return time;
	}

	public String getDate() {
		return date;
	}

	public String getId_movie() {
		return id_movie;
	}

	public int getId_user() {
		return id_user;
	}

	public String getStringSeat() {
		String seats = this.seat;
        String[] s1 = seats.split(" ");

        System.out.println();
        String[][] sortSeats = new String[2][s1.length];

        for (int index = 0; index < s1.length; index++) {
            String[] s2 = s1[index].split(Pattern.quote("."));
            sortSeats[0][index] = s2[0];
            sortSeats[1][index] = s2[1];
        }

        String row = "";
        for (int index = 0; index < sortSeats[0].length; index++) {
            if (!row.contains(sortSeats[0][index])) {
                row += sortSeats[0][index] + " ";
            }
        }

        String[] rows = row.split(" ");

        String[][] S = new String[rows.length][s1.length + 1];

        for (int index1 = 0; index1 < rows.length ; index1++) {
            for (int index2 = 0; index2 < s1.length ; index2++) {
                S[index1][0] = rows[index1];
                if (rows[index1].equals(sortSeats[0][index2])){
                    S[index1][index2+1] = sortSeats[1][index2];
                }
            }
        }

        String text = "";

        for (int r = 0; r < S.length ; r++) {
            text += "Row " + S[r][0] + " seat ";
            for (int c = 1; c < S[0].length ; c++) {
                if(S[r][c] != null){
                    text += S[r][c] + " ";
                }
            }
        }

        return text;
		
	}
	
	public String getName() {
		String query = "select name from movies where id = ?";  
		try {
			
			PreparedStatement statement = worker.getConnection().prepareStatement(query);
			statement.setString(1, this.id_movie);
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) {
                return rs.getString("name");
			} else { 
            	return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR 404 ");
		}
		return "";
	}
}

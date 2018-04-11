package models;

public class Movie {
	private String id;
	private String name;
	private String genre;
	private String description;
	private String producer;
	private String actors;
	private String premiere;
	private String poster_name;
	
	public Movie(String id, String name, String genre, String description, String producer, String actors, String premiere, String poster_name) {
		this.id = id;
		this.name = name;
		this.genre = genre;
		this.description = description;
		this.producer = producer;
		this.actors = actors;
		this.premiere = premiere;
		this.poster_name = poster_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getPremiere() {
		return premiere;
	}

	public void setPremire(String premiere) {
		this.premiere = premiere;
	}

	public String getPoster_name() {
		return poster_name;
	}

	public void setPoster_name(String poster_name) {
		this.poster_name = poster_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPremiere(String premiere) {
		this.premiere = premiere;
	}
	
	
	
	
	
	

}

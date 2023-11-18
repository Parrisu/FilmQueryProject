package com.skilldistillery.filmquery.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	private static final String USER = "student";
	private static final String PW = "student";

	public DatabaseAccessorObject() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) throws SQLException {
		Connection conn = DriverManager.getConnection(URL, USER, PW);

		String sql = "SELECT * FROM film f WHERE f.id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);

		ResultSet rs = stmt.executeQuery();
		Film film = null;
		while (rs.next()) {
			int filmId1 = rs.getInt("id");
			String title = rs.getString("title");
			String desc = rs.getString("description");
			short releaseYear = rs.getShort("release_year");
			int langId = rs.getInt("language_id");
			int rentDur = rs.getInt("rental_duration");
			double rate = rs.getDouble("rental_rate");
			int length = rs.getInt("length");
			double repCost = rs.getDouble("replacement_cost");
			String rating = rs.getString("rating");
			String features = rs.getString("special_features");
			List<Actor> fActors = findActorsByFilmId(filmId1);
			film = new Film(filmId1, title, releaseYear, desc, langId, rentDur, rate, length, repCost, rating, features,
					fActors);
		}
		rs.close();
		stmt.close();
		conn.close();
		return film;
	}

	@Override
	public List<Film> findFilmByKeyword(String keyword) throws SQLException {
		Connection conn = DriverManager.getConnection(URL, USER, PW);
		List<Film> films = new ArrayList<>();

		String sql = "SELECT * FROM film f WHERE f.title LIKE ? OR f.description LIKE ?";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, "%" + keyword + "%");
		stmt.setString(2, "%" + keyword + "%");

		ResultSet rs = stmt.executeQuery();
		Film film = null;
		while (rs.next()) {
			int filmId1 = rs.getInt("id");
			String title = rs.getString("title");
			String desc = rs.getString("description");
			short releaseYear = rs.getShort("release_year");
			int langId = rs.getInt("language_id");
			int rentDur = rs.getInt("rental_duration");
			double rate = rs.getDouble("rental_rate");
			int length = rs.getInt("length");
			double repCost = rs.getDouble("replacement_cost");
			String rating = rs.getString("rating");
			String features = rs.getString("special_features");
			List<Actor> fActors = findActorsByFilmId(filmId1);
			film = new Film(filmId1, title, releaseYear, desc, langId, rentDur, rate, length, repCost, rating, features,
					fActors);
			films.add(film);

		}
		rs.close();
		stmt.close();
		conn.close();
		return films;
	}

	@Override
	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
		Connection conn = DriverManager.getConnection(URL, USER, PW);

		String sql = "SELECT * FROM actor WHERE id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {

			String fn = rs.getString("first_name");
			String ln = rs.getString("last_name");
			int id = rs.getInt("id");
			actor = new Actor(id, fn, ln);
			actor.setFilms(findFilmsByActorId(actorId));

		}
		rs.close();
		conn.close();
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PW);
			String sql = "SELECT * FROM actor a JOIN film_actor fa ON a.id = fa.actor_id JOIN film f ON fa.film_id = f.id WHERE f.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String fn = rs.getString("first_name");
				String ln = rs.getString("last_name");
				int id = rs.getInt("id");
				Actor actor = new Actor(id, fn, ln);
				actors.add(actor);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}

	@Override
	public List<Film> findFilmsByActorId(int actorId) {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PW);

			String sql = "SELECT * FROM film f JOIN film_actor fa ON f.id = fa.film_id WHERE fa.actor_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int filmId1 = rs.getInt("id");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				short releaseYear = rs.getShort("release_year");
				int langId = rs.getInt("language_id");
				int rentDur = rs.getInt("rental_duration");
				double rate = rs.getDouble("rental_rate");
				int length = rs.getInt("length");
				double repCost = rs.getDouble("replacement_cost");
				String rating = rs.getString("rating");
				String features = rs.getString("special_features");
				Film film = new Film(filmId1, title, releaseYear, desc, langId, rentDur, rate, length, repCost, rating,
						features);
				films.add(film);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	@Override
	public String findLanguagebyFilmID(int filmID) {
		String language = null;
		try {
			Connection conn = DriverManager.getConnection(URL, USER, PW);

			String sql = "SELECT name FROM language l JOIN film f ON l.id = f.language_id WHERE f.id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmID);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				language = rs.getString("name");
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return language;
	}

}

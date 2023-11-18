package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		try {
//			app.test();
			app.launch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void test() throws SQLException {
		Film film = db.findFilmById(565);
		System.out.println(film);
		Actor actor = db.findActorById(1);
		System.out.println(actor);
		List<Actor> actors = new ArrayList<>(db.findActorsByFilmId(1));
		System.out.println(actors);

	}

	private void launch() throws SQLException {
		Scanner sc = new Scanner(System.in);

		startUserInterface(sc);

		sc.close();
	}

	private void startUserInterface(Scanner sc) throws SQLException {
		boolean checker = true;
		System.out.println("Welcome to the Film Looker Upper!");
		System.out.println();

		while (checker) {
			System.out.println();
			System.out.println("What would you like to do?");
			System.out.println("[1] Look up a film by its ID");
			System.out.println("[2] Look up a film by a search keyword");
			System.out.println("[3] Exit");
			System.out.println();

			int option = sc.nextInt();
			sc.nextLine();

			switch (option) {
			case 1:
				System.out.print("Please enter the film ID: ");

				try {
					Film film = db.findFilmById(sc.nextInt());
					sc.nextLine();
					if (film.equals(null)) {
					} else {
						String language = db.findLanguagebyFilmID(film.getId());
						System.out.println(film);
						List<Actor> copy = new ArrayList<>(film.getActors());
						System.out.print("Cast: ");
						for (Actor actor : copy) {
							System.out.print(actor + " ");
						}

						System.out.println("\nLanguage: " + language);
					}

				} catch (NullPointerException e) {
					System.out.println();
					System.out.println("There are no films with that ID.");
					break;
				}
				break;
			case 2:
				System.out.print("Please enter keyword: ");

				try {
					List<Film> film = db.findFilmByKeyword(sc.next());
					if (film.isEmpty()) {
						System.out.println("No Films Found.");
					} else {
						int counter = 0;
						for (Film film2 : film) {
							String language = db.findLanguagebyFilmID(film2.getId());
							System.out.println(film2);
							List<Actor> copy = new ArrayList<>(film2.getActors());
							System.out.print("Cast: ");
							for (Actor actor : copy) {
								System.out.print(actor + " ");
							}

							System.out.println("\nLanguage: " + language + "\n");
							counter++;
						}
						System.out.println("Total Films: " + counter);
					}

				} catch (NullPointerException e) {
					System.out.println("No Films Found.");
					break;
				}

				break;
			case 3:
				System.out.println("Thanks for coming! Goodbye.");
				System.exit(0);
				break;
			default:
				System.out.println("invalid choice.");
			}
		}
	}

}

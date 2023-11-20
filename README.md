# FilmQueryProject

## Description

This project is a Java program that uses a JDBC API to run SQL commands. The FilmQueryProject takes input from the user in the form of a film's unique ID or a keyword in order to find films within the database.

## Techonlogies Used
Java, OOP, Eclipse, mySQL, MAMP, JDBC,

## Lessons Learned

The biggest lesson I learned in this project was binding the `%` character inside of the `stmt.setString` method. At first I was placing the `%` inside of the SQL query, but it always resulted in a runtime error.


I also increased my understanding of `Exception`s by seeing the impact of throwing those exceptions to other methods, which resulted in having to deal with them further up the chain. This made me implement `try` `catch` blocks to handle them.

An interesting challenge was trying to obtain the Language from a different table within the database. At first I thought that would require adding it to the query but instead I was able to create a separate method to achieve the end goal. I made the `findLanguagebyFilmID` method that would take in the found film's ID whenever the user looks up a film, and the language is returned for every title that returns as well.

package test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBAuthentification;
import database.DBStatic;


public class Test {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		boolean ok = DBAuthentification.createUser("tomo", "1234567", "tomo@tomo.tomo");
		System.out.println(ok);
		
		/*
		try {
			Connection connection = DBStatic.getPostgreSQLConnection();
			Statement state = connection.createStatement();
			state.executeQuery(psql_create_users);
			state.executeQuery(psql_create_sessions);
			state.executeQuery(psql_create_images);
			state.executeQuery(psql_create_library);
			state.executeQuery(psql_create_friendship);
			state.executeQuery(psql_create_messages);
			state.executeQuery(psql_create_comments);
			
			state.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}
	
	
	
	
	public static void postgreSQLTableCreationRequest() {

		String psql_drop_tables = "DROP TABLE IF EXISTS users, user_session, images, library, friendship, messages, comments";
		
		/*
		 * USER TABLE
		 */
		String psql_create_users = 
				"CREATE TABLE users("
				+ " id SERIAL PRIMARY KEY,"
				+ " username VARCHAR(32) NOT NULL,"
				+ " email VARCHAR(255) NOT NULL,"
				+ " password VARCHAR(32) NOT NULL,"
				+ " since TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP);";
		 
		/*
		 * USER_SESSION TABLE
		 */
		String psql_create_sessions = "DROP TABLE IF EXISTS user_session;\r\n" + 
				"CREATE TABLE user_session(" + 
				" sessionkey VARCHAR(32) PRIMARY KEY," + 
				" userId INTEGER REFERENCES users ON DELETE CASCADE," + 
				" since timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP);";
		
		/*
		 * IMAGES TABLE
		 */
		String psql_create_images = "DROP TABLE IF EXISTS images;\r\n" +
				"CREATE TABLE images(" +
				" id SERIAL PRIMARY KEY," + 
				" path TEXT NOT NULL);";
		
		/*
		 * LIBRARY TABLE
		 */
		String psql_create_library = "DROP TABLE IF EXISTS library;\r\n" +
				"CREATE TABLE library(" + 
				" id SERIAL PRIMARY KEY," + 
				" userId INTEGER REFERENCES users ON DELETE CASCADE," + 
				" imgId INTEGER REFERENCES images ON DELETE CASCADE);"; 
		
		/*
		 * FRIENDSHIP TABLE
		 */
		String psql_create_friendship = "DROP TABLE IF EXISTS friendship;\r\n" +
				"CREATE TABLE friendship(" + 
				" id SERIAL PRIMARY KEY," + 
				" userId INTEGER REFERENCES users ON DELETE CASCADE," + 
				" friendId INTEGER REFERENCES users ON DELETE CASCADE);";
		
		/*
		 * MESSAGES TABLE
		 */
		String psql_create_messages = "DROP TABLE IF EXISTS messages;\r\n"+
				"CREATE TABLE messages(" + 
				" id BIGSERIAL PRIMARY KEY," + 
				" authorId INTEGER REFERENCES users ON DELETE CASCADE," + 
				" destinationId INTEGER REFERENCES users ON DELETE CASCADE," + 
				" text TEXT NOT NULL," + 
				" sendtime TIMESTAMP NOT NULL);";
		
		/*
		 * COMMENTS TABLE
		 */
		String psql_create_comments = "DROP TABLE IF EXISTS comments;\r\n"
				+ "CREATE TABLE comments(" + 
				" id BIGSERIAL PRIMARY KEY," + 
				" libraryId INTEGER REFERENCES library ON DELETE CASCADE," + 
				" authorId INTEGER REFERENCES users ON DELETE CASCADE," + 
				" text TEXT NOT NULL," + 
				" date TIMESTAMP NOT NULL);";
		
		System.out.println(psql_create_users);
		System.out.println(psql_create_sessions);
		System.out.println(psql_create_images);
		System.out.println(psql_create_library);
		System.out.println(psql_create_friendship);
		System.out.println(psql_create_messages);
		System.out.println(psql_create_comments);
	}

}

package test;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Session;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

import database.DBAuthentification;
import database.DBStatic;
import utils.Persist;


public class Test {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		String filePath = "desktop_wallpaper.jpg";
		int ok = Test.uploadImagesAmazonAPI(filePath);
		System.out.println(ok);
		*/
		
			Session session = DBStatic.getHibernateSession();
			session.beginTransaction();
			session.createQuery(psql_create_friendship).executeUpdate();
			session.getTransaction().commit();
			session.close();
			System.out.println("OK");
			
			/*
		try {
			Connection connection = DBStatic.getPostgreSQLConnection();
			Statement state = connection.createStatement();
		//	state.executeQuery(psql_create_users);
		//	state.executeQuery(psql_create_sessions);
		//	state.executeQuery(psql_create_images);
		//	state.executeQuery(psql_create_library);
		//	state.executeQuery(psql_create_friendship);
		//	state.executeQuery(psql_create_messages);
		//	state.executeQuery(psql_create_comments);
			
			state.executeQuery("DROP TABLE IF EXISTS friendship;");
			state.close();
			connection.close();
			System.out.println("OK");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("KO");
		}*/
	}
	
	
	
	
	public static void postgreSQLTableCreationRequest() {
		System.out.println(psql_create_users);
		System.out.println(psql_create_sessions);
		System.out.println(psql_create_images);
		System.out.println(psql_create_library);
		System.out.println(psql_create_friendship);
		System.out.println(psql_create_messages);
		System.out.println(psql_create_comments);
	}
	
	String psql_drop_tables = "DROP TABLE IF EXISTS users, user_session, images, library, friendship, messages, comments";
	
	/*
	 * USER TABLE
	 */
	static String psql_create_users = 
			"CREATE TABLE users("
			+ " id SERIAL PRIMARY KEY,"
			+ " username VARCHAR(32) NOT NULL,"
			+ " email VARCHAR(255) NOT NULL,"
			+ " password VARCHAR(32) NOT NULL,"
			+ " since TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP);";
	 
	/*
	 * USER_SESSION TABLE
	 */
	static String psql_create_sessions = "DROP TABLE IF EXISTS user_session;\r\n" + 
			"CREATE TABLE user_session(" + 
			" sessionkey VARCHAR(32) PRIMARY KEY," + 
			" userId INTEGER REFERENCES users ON DELETE CASCADE," + 
			" since timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP);";
	
	/*
	 * IMAGES TABLE
	 */
	static String psql_create_images = "DROP TABLE IF EXISTS images;\r\n" +
			"CREATE TABLE images(" +
			" id SERIAL PRIMARY KEY," + 
			" path TEXT NOT NULL);";
	
	/*
	 * LIBRARY TABLE
	 */
	static String psql_create_library = "DROP TABLE IF EXISTS library;\r\n" +
			"CREATE TABLE library(" + 
			" id SERIAL PRIMARY KEY," + 
			" userId INTEGER REFERENCES users ON DELETE CASCADE," + 
			" imgId INTEGER REFERENCES images ON DELETE CASCADE);"; 
	
	/*
	 * FRIENDSHIP TABLE
	 */
	static String psql_create_friendship = /*"DROP TABLE IF EXISTS friendship;\r\n" +*/
			"CREATE TABLE friendship(" + 
			" id SERIAL PRIMARY KEY," + 
			" userId INTEGER REFERENCES users ON DELETE CASCADE," + 
			" friendId INTEGER REFERENCES users ON DELETE CASCADE," +
			" state INTEGER NOT NULL);";
	
	/*
	 * MESSAGES TABLE
	 */
	static String psql_create_messages = "DROP TABLE IF EXISTS messages;\r\n"+
			"CREATE TABLE messages(" + 
			" id BIGSERIAL PRIMARY KEY," + 
			" authorId INTEGER REFERENCES users ON DELETE CASCADE," + 
			" destinationId INTEGER REFERENCES users ON DELETE CASCADE," + 
			" text TEXT NOT NULL," + 
			" sendtime TIMESTAMP NOT NULL);";
	
	/*
	 * COMMENTS TABLE
	 */
	static String psql_create_comments = "DROP TABLE IF EXISTS comments;\r\n"
			+ "CREATE TABLE comments(" + 
			" id BIGSERIAL PRIMARY KEY," + 
			" imgId INTEGER REFERENCES library ON DELETE CASCADE," + 
			" authorId INTEGER REFERENCES users ON DELETE CASCADE," + 
			" text TEXT NOT NULL," + 
			" date TIMESTAMP NOT NULL);";
	
	private static final String BUCKET_NAME = "mozaigram";

	public static int uploadImagesAmazonAPI(String filePath) {
        TransferManager tm = new TransferManager(new ProfileCredentialsProvider());
        System.out.println("Hello");
        // TransferManager processes all transfers asynchronously, 
        // so this call will return immediately.
        File toUpload = Paths.get(filePath).toFile();
        Upload upload = tm.upload(BUCKET_NAME, toUpload.getName(), toUpload);
        System.out.println("Hello2");

        try {
        	// Or you can block and wait for the upload to finish
        	upload.waitForCompletion();
        	System.out.println("Upload complete.");
        	return Persist.SUCCESS;
        } catch (AmazonClientException amazonClientException) {
        	System.out.println("Unable to upload file, upload was aborted.");
        	amazonClientException.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return Persist.ERROR;
	}

}

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import hibernate_entity.*;

public class DBStatic {
	
	//private static String mysql_host = "132.227.201.129:3306";	// from home: 132.227.201.129
	private static String mysql_host="localhost:3307";
	private static String mysql_db= "mozaik_generator";
	private static String mysql_username = "stldar";
	private static String mysql_password = "stldar$";
	private static boolean mysql_pooling = false;
	private static Database database = null;
	
	
	public static Connection getMySQLConnection() throws SQLException {
		try {
			System.out.println("1");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("2");
			if(DBStatic.mysql_pooling == false)
				return (DriverManager.getConnection("jdbc:mysql://" + DBStatic.mysql_host + "/" + DBStatic.mysql_db,DBStatic.mysql_username,DBStatic.mysql_password));
			else{
				if(database == null)
					database = new Database("jdbc/db");
				return (database.getConnection());
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (DriverManager.getConnection("jdbc:mysql://" + DBStatic.mysql_host + "/" + DBStatic.mysql_db,DBStatic.mysql_username,DBStatic.mysql_password));
	}
	
	public static Session getHibernateSession() {
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(User.class)
				.addAnnotatedClass(Image.class)
				.addAnnotatedClass(Library.class)
				.addAnnotatedClass(UserSession.class)
				.buildSessionFactory();
		
		return factory.getCurrentSession();
	}
}

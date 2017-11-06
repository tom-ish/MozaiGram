package database;

import java.util.List;

import org.hibernate.Session;

import hibernate_entity.User;
import utils.Persist;

public class DBAuthentification {
	
	public static int getIdByUsername(String username) {
		String hql = "from User u where u.username='"+username+"'";
		Session session = DBStatic.getHibernateSession();
		if(session != null) {
			session.beginTransaction();
			List<User> users = session.createQuery(hql).getResultList();
			session.close();
			for(User user : users)
				if(user.getUsername().equals(username))
					return user.getId();
			
		}
		return -1;
	}
	
	public static boolean existeLogin(String username) {
		String hql = "from User";
		Session session = DBStatic.getHibernateSession();
		if(session != null) {
			session.beginTransaction();
			List<User> users = session.createQuery(hql).getResultList();
			session.close();
			for(User user : users) {
				if(user.getUsername().equalsIgnoreCase(username))
					return true;
			}
		}
		return false;
	}
	
	public static boolean createUser(String username, String password, String email ) {
		User newUser = new User(username, password, email);
		Session session = DBStatic.getHibernateSession();
		if(session != null) {			
			session.beginTransaction();
			session.save(newUser);
			session.getTransaction().commit();
			session.close();
			return Persist.OK;
		}
		return Persist.KO;
	}
	
	public static boolean isPasswordExact(String username, String password) {
		String hql = "from User u where u.username='"+username+"'";
		
		Session session = DBStatic.getHibernateSession();
		if(session != null) {
			session.beginTransaction();
			List<User> users = session.createQuery(hql).getResultList();
			session.close();
			for(User user : users)
				if(user.getUsername().equals(username) && user.getPassword().equals(password))
					return true;
		}
		return false;
	}
	
	public static String connect(String username) {
		return DBSessionKey.addSessionKey(username);
	}
	
	public static int logout(int userId, String sessionkey) {
		String hql = "delete from UserSession where sessionkey = :sessionkey and userId = :userId";
		
		Session session = DBStatic.getHibernateSession();
		session.beginTransaction();
		
		int nb_row = session.createQuery(hql)
				.setParameter("sessionkey", sessionkey)
				.setParameter("userId", userId)
				.executeUpdate();
		session.getTransaction().commit();
		session.close();
		
		if(nb_row > 0)
			return Persist.SUCCESS;
		else if(nb_row == 0)
			return Persist.ERROR_DB_NO_ROW_AFFECTED;
		return Persist.ERROR_DB_LOGOUT_IMPOSSIBLE;
	}
}

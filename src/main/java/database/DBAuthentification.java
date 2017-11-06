package database;

import java.util.List;

import org.hibernate.Session;

import hibernate_entity.User;
import utils.Persist;

public class DBAuthentification {

	public static int getIdByUsername(String username) {
		String hql = "from User u where u.username='"+username+"'";
		if(Persist.OPENED_SESSION != null && Persist.OPENED_SESSION.isOpen()) {
			Persist.OPENED_SESSION.beginTransaction();
			List<User> users = Persist.OPENED_SESSION.createQuery(hql).getResultList();
			for(User user : users)
				if(user.getUsername().equals(username))
					return user.getId();

		}
		return -1;
	}

	public static boolean existeLogin(String username) {
		String hql = "from User";
		if(Persist.OPENED_SESSION != null && Persist.OPENED_SESSION.isOpen()) {
			Persist.OPENED_SESSION.beginTransaction();
			List<User> users = Persist.OPENED_SESSION.createQuery(hql).getResultList();
			for(User user : users) {
				if(user.getUsername().equalsIgnoreCase(username))
					return true;
			}
		}
		return false;
	}

	public static boolean createUser(String username, String password, String email ) {
		User newUser = new User(username, password, email);
		if(Persist.OPENED_SESSION != null && Persist.OPENED_SESSION.isOpen()) {			
			Persist.OPENED_SESSION.beginTransaction();
			Persist.OPENED_SESSION.save(newUser);
			Persist.OPENED_SESSION.getTransaction().commit();
			return Persist.OK;
		}
		return Persist.KO;
	}

	public static boolean isPasswordExact(String username, String password) {
		String hql = "from User u where u.username='"+username+"'";

		if(Persist.OPENED_SESSION != null && Persist.OPENED_SESSION.isOpen()) {
			Persist.OPENED_SESSION.beginTransaction();
			List<User> users = Persist.OPENED_SESSION.createQuery(hql).getResultList();
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

		if(Persist.OPENED_SESSION != null && Persist.OPENED_SESSION.isOpen()) {
			Persist.OPENED_SESSION.beginTransaction();

			int nb_row = Persist.OPENED_SESSION.createQuery(hql)
					.setParameter("sessionkey", sessionkey)
					.setParameter("userId", userId)
					.executeUpdate();
			Persist.OPENED_SESSION.getTransaction().commit();

			if(nb_row > 0)
				return Persist.SUCCESS;
			else if(nb_row == 0)
				return Persist.ERROR_DB_NO_ROW_AFFECTED;
		}
		return Persist.ERROR_DB_LOGOUT_IMPOSSIBLE;
	}
}

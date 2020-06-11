package database;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import hibernate_entity.User;
import utils.HibernateUtil;
import utils.Persist;

public class DBAuthentification {

	public static int getIdByUsername(String username) {
		String hql = "from User as u where u.username=:username";
		Session session = HibernateUtil.currentSession();
		if(session != null) {
			try {
				List<User> users = session.createQuery(hql).setParameter("username", username).getResultList();
				for(User user : users) {
					if(user.getUsername().equals(username)) {
						HibernateUtil.closeSession();
						return user.getId();
					}
				}
			}
			finally {
				HibernateUtil.closeSession();
			}
		}
		return -1;
	}
	
	public static User getUserByUsername(String username) {
		String hql = "from User as u where u.username=:username";
		Session session = HibernateUtil.currentSession();
		if(session != null) {
			System.out.println("SESSION OPENED");
			try {
				List<User> users = session.createQuery(hql).setParameter("username", username).getResultList();
				for(User user : users) {
					if(user.getUsername().equals(username)) {
						System.out.println("SESSION CLOSED");
						HibernateUtil.closeSession();
						return user;
					}
				}
			}
			finally {
				HibernateUtil.closeSession();
			}

		}
		return null;
	}
	
	public static User getUserById(int id) {
		String hql = "from User as u where u.id=:id";
		Session session = HibernateUtil.currentSession();
		if(session != null) {
			try {
				List<User> users = session.createQuery(hql).setParameter("id", id).getResultList();
				for(User user : users) {
					if(user.getId() == id) {
						HibernateUtil.closeSession();
						return user;
					}
				}
			}
			finally {
				HibernateUtil.closeSession();
			}
		}
		return null;
	}
	
	public static ArrayList<String> getUserNames(){
		String hql="from User";
		ArrayList<String> result = new ArrayList<String>();
		Session session = HibernateUtil.currentSession();
		if (session !=null){
			try {
				List<User> users= session.createQuery(hql).getResultList();
				for (User user:users){
					result.add(user.getUsername());
				}
			}
			finally {
				HibernateUtil.closeSession();
			}
		}
		return result;
	}

	public static boolean existeLogin(String username) throws HibernateException {
		String hql = "from User as u where u.username=:username";
		Session session = HibernateUtil.currentSession();
		if(session != null) {
			try {
				List<User> users = session.createQuery(hql).setParameter("username", username).getResultList();
				for(User user : users) {
					if(user.getUsername().equalsIgnoreCase(username)) {
						HibernateUtil.closeSession();
						return true;
					}
				}
			}
			finally {
				HibernateUtil.closeSession();
			}
		}
		return false;
	}
	
	public static boolean existeUserId(int userId) {
		String hql = "from User as u where u.id=:id";
		Session session = HibernateUtil.currentSession();
		if(session != null) {
			try {
				List<User> users = session.createQuery(hql).setParameter("id", userId).getResultList();
				for(User user : users) {
					if(user.getId() == userId) {
						HibernateUtil.closeSession();
						return true;
					}
				}
			}
			finally {
				HibernateUtil.closeSession();
			}

		}
		return false;
	}

	public static boolean createUser(String username, String password, String email ) {
		User newUser = new User(username, password, email);
		Session session = HibernateUtil.currentSession();
		Transaction tx = null;
		if(session != null) {
			try {
				tx = session.beginTransaction();
				session.save(newUser);
				session.getTransaction().commit();
				HibernateUtil.closeSession();
				return Persist.OK;
			}
			catch(HibernateException e) {
				if (tx!=null) tx.rollback();
				e.printStackTrace();
			}
			finally {
				HibernateUtil.closeSession();
			}
		}
		return Persist.KO;
	}

	public static boolean isPasswordExact(String username, String password) {
		String hql = "from User as u where u.username=:username";
		Session session = HibernateUtil.currentSession();
		if(session != null) {
			try {
				System.out.println("OPENED_SESSION in isPasswordExact");
				List<User> users = session.createQuery(hql)
						.setParameter("username", username)
						.getResultList();
				for(User user : users) {
					System.out.println(user);
					if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
						HibernateUtil.closeSession();
						return true;
					}
				}
			}
			catch(HibernateException e) {
				e.printStackTrace();
			}
			finally {
				HibernateUtil.closeSession();
			}
		}
		return false;
	}

	public static String connect(User user) {
		return DBSessionKey.addSessionKey(user);
	}

	public static int logout(int userId, String sessionkey) {
		String hql = "delete UserSession where sessionkey = :sessionkey and userId = :userId";

		Session session = HibernateUtil.currentSession();
		Transaction tx = null;
		if(session != null) {
			try {
				tx = session.beginTransaction();
				int nb_row = session.createQuery(hql)
						.setParameter("sessionkey", sessionkey)
						.setParameter("userId", userId)
						.executeUpdate();
				tx.commit();
				HibernateUtil.closeSession();
				if(nb_row > 0)
					return Persist.SUCCESS;
				else if(nb_row == 0)
					return Persist.ERROR_DB_NO_ROW_AFFECTED;
			}
			catch(HibernateException e) {
				if (tx!=null) tx.rollback();
				e.printStackTrace();
			}
			finally {
				HibernateUtil.closeSession();
			}
		}
		return Persist.ERROR_DB_LOGOUT_IMPOSSIBLE;
	}
}

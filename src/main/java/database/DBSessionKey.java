
package database;

import java.util.List;

import org.hibernate.Session;

import hibernate_entity.UserSession;

public class DBSessionKey {
	
	public static String generateKey() {
		return DBSessionKey.generateKey(32);
	}
	
	public static String generateKey(int length) {
		String rslt = "";
		String dictionnary = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		
		for(int i = 0; i < length; i++)
			rslt += dictionnary.charAt((int) (Math.random()*dictionnary.length()));
		
		if(getUsernameByKey(rslt) != -1)
			return generateKey();
		else
			return rslt;
	}
		
	public static int getUsernameByKey(String key) {
		String hql = "from UserSession";
		
		Session session = DBStatic.getHibernateSession();
		if(session != null) {
			session.beginTransaction();
			List<UserSession> userSessions = session.createQuery(hql).getResultList();
			session.close();
			for(UserSession userSession : userSessions) {
				if(userSession.getSessionkey().equals(key))
					return userSession.getUserId();
			}
		}
		return -1;
	}
	
	public static String getSessionkeyByUsername(String username) {
		String hql = "from UserSession";
		
		Session session = DBStatic.getHibernateSession();
		if(session != null) {
			session.beginTransaction();
			List<UserSession> userSessions = session.createQuery(hql).getResultList();
			session.close();
			for(UserSession userSession : userSessions) {
				if(userSession.getUserId() == DBAuthentification.getIdByUsername(username))
					return userSession.getSessionkey();		
			}
		}
		return null;
	}
	
	public static String addSessionKey(String username) {
		return addSessionKey(username, generateKey());
	}
	
	public static String addSessionKey(String username, String sessionkey) {
		int userId = DBAuthentification.getIdByUsername(username);
		if(userId == -1) return null;
		UserSession newSession = new UserSession(sessionkey, userId);
		Session session = DBStatic.getHibernateSession();

		if(session != null) {			
			session.beginTransaction();
			session.save(newSession);
			session.getTransaction().commit();
			session.close();
			return sessionkey;
		}	
		return null;
	}
	
	public static boolean isSessionKeyExpired(String sessionkey) {
		String hql = "from UserSession";

		Session session = DBStatic.getHibernateSession();
		if(session != null) {
			session.beginTransaction();
			List<UserSession> userSessions = session.createQuery(hql).getResultList();
			session.close();
			for(UserSession userSession : userSessions) {
				if(userSession.getSessionkey() == sessionkey)
					return true;		
			}
		}
		return false;
	}

}

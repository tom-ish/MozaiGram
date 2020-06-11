package database;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import hibernate_entity.UserSession;
import hibernate_entity.UserTask;
import utils.HibernateUtil;
import utils.Persist;

public class DBUserTask {

	public static boolean isUserTaskCompleted(UserSession userSession) {
		String hql = "from UserTask usertask where usertask.userSession=:userSession and usertask.status=:status";
		Session session = HibernateUtil.currentSession();
		if(session != null) {
			try {
				List<UserTask> userTasks = session.createQuery(hql)
						.setParameter("userSession", userSession)
						.setParameter("status", Persist.PROCESS_COMPLETE)
						.getResultList();
				if(userTasks.isEmpty())
					return Persist.KO;
				for(UserTask userTask : userTasks) {
					if(userTask.getUserSession().equals(userSession) && userTask.getStatus() == Persist.PROCESS_COMPLETE) {
						HibernateUtil.closeSession();
						return Persist.OK;
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
		return Persist.KO;
	}

	public static UserTask createUserTask(String sessionkey) {
		UserSession userSession = DBSessionKey.getUserSessionFromSessionKey(sessionkey);
		UserTask userTask = new UserTask(userSession, Persist.PROCESS_NOT_COMPLETED_YET, "");

		System.out.println("NEW USER TASK CREATED WITH sessionkey  " + userTask.getUserSession().getSessionkey());
		Session session = HibernateUtil.currentSession();
		Transaction tx = null;
		if(session != null) {
			try {
				tx = session.beginTransaction();
				session.save(userTask);
				session.flush();
				tx.commit();
				HibernateUtil.closeSession();
				return userTask;
			}
			catch(HibernateException e) {
				e.printStackTrace();
			}
			finally {
				HibernateUtil.closeSession();
			}
		}
		return null;
	}


	public static UserTask notifyUserTaskComplete(UserSession userSession, String path) {
		String hql = "from UserTask as usertask where usertask.userSession=:usersession";

		Session session = HibernateUtil.currentSession();
		Transaction tx = null;
		if(session != null) {
			try {
				tx = session.beginTransaction();
				List<UserTask> userTasks = session.createQuery(hql)
						.setParameter("usersession", userSession)
						.getResultList();
				for(UserTask userTask : userTasks) {
					if(userTask.getUserSession().equals(userSession)) {
						session.update(userTask);
						userTask.setStatus(Persist.PROCESS_COMPLETE);
						userTask.setPath(path);
						session.flush();
						tx.commit();
						System.out.println("USER TASK COMPLETED");
						HibernateUtil.closeSession();
						return userTask;
					}
				}
			}
			catch(HibernateException e) {
				if(tx != null) tx.rollback();
				e.printStackTrace();
			}
			finally {
				HibernateUtil.closeSession();
			}
		}
		return null;
	}

	public static String getImgPath(UserSession userSession) {
		String hql = "from UserTask as usertask where usertask.userSession=:userSession and usertask.status=:status";
		Session session = HibernateUtil.currentSession();
		if(session != null) {
			try { 
				List<UserTask> userTasks = session.createQuery(hql)
						.setParameter("userSession", userSession)
						.setParameter("status", Persist.PROCESS_COMPLETE)
						.getResultList();

				for(UserTask userTask : userTasks) {
					if(userTask.getUserSession().getSessionkey().equals(userSession)
							&& userTask.getStatus() == Persist.PROCESS_COMPLETE
							&& !userTask.getPath().isEmpty()) {
						HibernateUtil.closeSession();
						return userTask.getPath();
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
		return "";
	}

	public static UserTask getUserTask(UserSession userSession) {
		String hql = "from UserTask as usertask where usertask.userSession=:userSession";

		Session session = HibernateUtil.currentSession();
		if(session != null) {
			try {
				List<UserTask> userTasks = session.createQuery(hql)
						.setParameter("userSession", userSession)
						.getResultList();
				for(UserTask userTask : userTasks) {
					if(userTask.getUserSession().equals(userSession)) {
						HibernateUtil.closeSession();
						return userTask;
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
		return null;
	}

	public static boolean removeUserTask(UserSession userSession) {
		String hql = "delete UserTask where userSession=:userSession";

		Session session = HibernateUtil.currentSession();
		if(session != null) {
			try {
				if(session.createQuery(hql).setParameter("userSession", userSession).executeUpdate() > 0) {
					session.flush();
					HibernateUtil.closeSession();
					return true;
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

}

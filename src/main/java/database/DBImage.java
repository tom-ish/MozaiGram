package database;

import java.util.List;

import org.hibernate.Session;

import hibernate_entity.Image;
import hibernate_entity.Library;

public class DBImage {
	
	public static int addImage(String imgPath) {
		Image img = new Image(imgPath);
		Session session = DBStatic.getHibernateSession();
		if(session != null) {
			session.beginTransaction();
			session.save(img);
			session.getTransaction().commit();
			session.close();
		}
		return img.getId();
	}

	public static int addImageToLibrary(int userId, int imgId) {
		Library library = new Library(userId, imgId);
		Session session = DBStatic.getHibernateSession();
		if(session != null) {
			session.beginTransaction();
			session.save(library);
			session.getTransaction().commit();
			session.close();
		}
		return library.getId();
	}
	
	public static String getPathFromImgId(int id) {
		String hql = "from Image";
		
		Session session = DBStatic.getHibernateSession();
		if(session != null) {
			session.beginTransaction();
			List<Image> images = session.createQuery(hql).getResultList();
			session.close();
			for(Image img : images) {
				if(img.getId() == id)
					return img.getLink();
			}
		}
		return null;
	}
	
}

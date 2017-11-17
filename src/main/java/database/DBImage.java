package database;

import java.util.ArrayList;
import java.util.List;

import hibernate_entity.Comment;
import hibernate_entity.Image;
import hibernate_entity.Library;
import utils.Persist;

public class DBImage {
	
	public static Image getImageById(int imgId) {
		String hql = "from Image";
		if(Persist.OPENED_SESSION != null) {
			List<Image> imgs = Persist.OPENED_SESSION.createQuery(hql).getResultList();
			for(Image img : imgs) {
				if(img.getId() == imgId)
					return img;
			}
		}
		return null;
	}
	
	public static List<Image> getImageFromUserId(int userid){
		String hql = "from Image image where image.id in "
				+ "(select images_id from library_images where library_id = "
				+ "(select id from library where userid ='" + userid +"'))";
		
		if(Persist.OPENED_SESSION != null) {
			return Persist.OPENED_SESSION.createQuery(hql).getResultList();
		}
		return null;
	}
	
	public static boolean existeImageId(int imgId) {
		String hql = "from Image";
		if(Persist.OPENED_SESSION != null) {
			List<Image> imgs = Persist.OPENED_SESSION.createQuery(hql).getResultList();
			for(Image img : imgs) {
				if(img.getId() == imgId)
					return true;
			}
		}
		return false;
	}
	
	public static int addComment(Image img, String txt) {
		Comment comment = new Comment(img, txt);
		
		if(Persist.OPENED_SESSION != null) {			
			Persist.OPENED_SESSION.beginTransaction();
			Persist.OPENED_SESSION.save(comment);
			Persist.OPENED_SESSION.getTransaction().commit();
			return Persist.SUCCESS;
		}
		return Persist.ERROR;
	}
	
	public static int addImage(String imgPath) {
		Image img = new Image(imgPath);
		if(Persist.OPENED_SESSION != null) {
			Persist.OPENED_SESSION.beginTransaction();
			Persist.OPENED_SESSION.save(img);
			Persist.OPENED_SESSION.getTransaction().commit();
			Persist.OPENED_SESSION.close();
		}
		return img.getId();
	}

	public static int addImageToLibrary(int userId, int imgId) {
		Library library = new Library();
		if(Persist.OPENED_SESSION != null) {
			Persist.OPENED_SESSION.beginTransaction();
			Persist.OPENED_SESSION.save(library);
			Persist.OPENED_SESSION.getTransaction().commit();
			Persist.OPENED_SESSION.close();
		}
		return library.getId();
	}
	
	public static String getPathFromImgId(int id) {
		String hql = "from Image";
		
		if(Persist.OPENED_SESSION != null) {
			List<Image> images = Persist.OPENED_SESSION.createQuery(hql).getResultList();
			Persist.OPENED_SESSION.close();
			for(Image img : images) {
				if(img.getId() == id)
					return img.getLink();
			}
		}
		return null;
	}
	
	
	
}

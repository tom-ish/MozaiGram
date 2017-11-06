package database;

import java.util.List;

import hibernate_entity.Image;
import hibernate_entity.Library;
import utils.Persist;

public class DBImage {
	
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
		Library library = new Library(userId, imgId);
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

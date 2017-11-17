package database;

import java.util.ArrayList;
import java.util.List;

import hibernate_entity.Image;
import hibernate_entity.Library;
import hibernate_entity.User;
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
		User user = DBAuthentification.getUserById(userId);
		Image img = DBImage.getImgFromId(imgId);
		if(user != null && img != null) {
			Library library = new Library();
			library.setUser(user);
			library.getImages().add(img);
			if(Persist.OPENED_SESSION != null) {
				Persist.OPENED_SESSION.beginTransaction();
				Persist.OPENED_SESSION.save(library);
				Persist.OPENED_SESSION.getTransaction().commit();
				Persist.OPENED_SESSION.close();
			}
			return library.getId();
		}
		return -1;
	}

	public static ArrayList<String> getPathsfromUser (String username) {
		String hql="from Image";
		ArrayList<String> results=new ArrayList<String>();
		if (Persist.OPENED_SESSION != null) {
			List<Image> images = Persist.OPENED_SESSION.createQuery(hql).getResultList();
			Persist.OPENED_SESSION.close();
			for (Image img : images) {
				if (img.getUser().getUsername()==username) {
					results.add(img.getLink());
				}
					
			}
		}
		return results;
		
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

	public static Image getImgFromId(int id) {
		String hql = "from Image";

		if(Persist.OPENED_SESSION != null) {
			List<Image> images = Persist.OPENED_SESSION.createQuery(hql).getResultList();
			Persist.OPENED_SESSION.close();
			for(Image img : images) {
				if(img.getId() == id)
					return img;
			}
		}
		return null;
	}

}

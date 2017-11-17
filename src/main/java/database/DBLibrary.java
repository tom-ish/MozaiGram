package database;

import java.util.List;

import hibernate_entity.Image;
import hibernate_entity.Library;
import hibernate_entity.User;
import utils.Persist;

public class DBLibrary {
	
	public static int addImageToLibrary(User user, Image img, Library library) {
		if(user != null && img != null && library != null) {
			library.getImages().add(img);
			if(Persist.OPENED_SESSION != null) {
				Persist.OPENED_SESSION.beginTransaction();
				Persist.OPENED_SESSION.save(library);
				Persist.OPENED_SESSION.getTransaction().commit();
				Persist.OPENED_SESSION.close();
			}
			return Persist.SUCCESS;
		}
		return Persist.ERROR;
	}
	
	public static Library getUserLibraryFromName(User user, String name) {
		String hql = "from Library l where l.user='"+user+"'";
		if(Persist.OPENED_SESSION != null) {
			List<Library> libraries = Persist.OPENED_SESSION.createQuery(hql).getResultList();
			for(Library library : libraries)
				if(library.getUser().equals(user) && library.getName().equals(name))
					return library;
		}
		return null;
	}
	
	public static Library getUserDefaultLibrary(User user) {
		return getUserLibraryFromName(user, user.getUsername()+Persist.DEFAULT_LIBRARY);
	}
	
	public static Library createDefaultLibrary(User user) {
		Library defaultLibrary = new Library(user, user.getUsername()+Persist.DEFAULT_LIBRARY); 
		if(Persist.OPENED_SESSION != null) {
			Persist.OPENED_SESSION.beginTransaction();
			Persist.OPENED_SESSION.save(defaultLibrary);
			Persist.OPENED_SESSION.getTransaction().commit();
			Persist.OPENED_SESSION.close();
		}
		return defaultLibrary;
	}
	
}

package services;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import database.DBAuthentification;
import database.DBImage;
import database.DBSessionKey;
import hibernate_entity.User;
import utils.Persist;
import utils.Tools;

public class ServicesImage {
	
	public static SimpleEntry<Integer, Integer> addImage(String sessionkey, String imgPath) {
		if(Tools.isNullParameter(sessionkey) || Tools.isNullParameter(imgPath))
			return new SimpleEntry<Integer, Integer>(Persist.ERROR_NULL_PARAMETER, -1);
		else if(DBSessionKey.isSessionKeyExpired(sessionkey))
			return new SimpleEntry<Integer, Integer>(Persist.ERROR_SESSION_KEY_NOT_FOUND, -1);
		else {
			int userId = DBSessionKey.getUserIdByKey(sessionkey);
			// On stocke l'adresse de l'image dans la DB Images
			int imgId = DBImage.addImage(imgPath, DBAuthentification.getUserById(userId));
			// si l'id de l'image == 0, alors c'est que la requete precedente n'a pas fonctionne correctement
			if(imgId != 0)
				return new SimpleEntry<Integer, Integer>(Persist.SUCCESS, imgId);
		}
		return new SimpleEntry<Integer, Integer>(Persist.ERROR_DB_IMAGE_CANNOT_ADD_NEW_INSTANCE, -1);
	}
	
	public static SimpleEntry<Integer, Integer> addImageToLibrary(String sessionkey, int imgId) {
		if(Tools.isNullParameter(sessionkey))
			return new SimpleEntry<Integer, Integer>(Persist.ERROR_NULL_PARAMETER, -1);
		if(imgId == 0)
			return new SimpleEntry<Integer, Integer>(Persist.ERROR_DB_IMAGE_NOT_FOUND, -1);
		
		int userId = DBSessionKey.getUserIdByKey(sessionkey);
		if(userId == -1)
			return new SimpleEntry<Integer, Integer>(Persist.ERROR_SESSION_KEY_NOT_FOUND, -1);
		int imgIdInLibrary = DBImage.addImageToLibrary(userId, imgId);
		if(imgIdInLibrary != 0)
			return new SimpleEntry<Integer, Integer>(Persist.SUCCESS, imgIdInLibrary);
		return new SimpleEntry<Integer, Integer>(Persist.ERROR_DB_LIBRARY_CANNOT_ADD_NEW_INSTANCE, -1);
	}
	
	public static String getPathFromImgId(int imgId) {
		if(imgId <= 0) {
			return null;
		}
		return DBImage.getPathFromImgId(imgId);
	}
	
	public static ArrayList<String> getPathsfromUser (String username){
		return DBImage.getPathsfromUser(username);
	}
	
	public static Image getImageFromPath(String path) {
		try {
			Image image = ImageIO.read(new File(path));
			return image;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IOException in getBufferedImageFromPath() - cannot read File : " + path);
			e.printStackTrace();
		}
		return null;
	}

}

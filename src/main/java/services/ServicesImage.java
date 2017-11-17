package services;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.json.JSONObject;

import database.DBImage;
import database.DBLibrary;
import database.DBSessionKey;
import hibernate_entity.Library;
import hibernate_entity.User;
import utils.Persist;
import utils.Tools;

public class ServicesImage {
	
	public static SimpleEntry<Integer, String> addImage(String sessionkey, String imgPath) {
		if(Tools.isNullParameter(sessionkey) || Tools.isNullParameter(imgPath))
			return new SimpleEntry<Integer, String>(Persist.ERROR_NULL_PARAMETER, "");
		else if(DBSessionKey.isSessionKeyExpired(sessionkey))
			return new SimpleEntry<Integer, String>(Persist.ERROR_SESSION_KEY_NOT_FOUND, "");
		else {
			User user = DBSessionKey.getUserByKey(sessionkey);
			// On stocke l'adresse de l'image dans la DB Images
			hibernate_entity.Image img = DBImage.addImage(imgPath, user);

			// si l'id de l'image != 0, c'est que l'ajout s'est bien deroule
			if(img != null) {
				//  => on l'ajoute au default_library de l'user
				Library defaultLibrary = DBLibrary.getUserDefaultLibrary(user);		
				if(defaultLibrary == null)
					defaultLibrary = DBLibrary.createDefaultLibrary(user);
				if(DBLibrary.addImageToLibrary(user, img, defaultLibrary) == Persist.SUCCESS)
					return new SimpleEntry<Integer, String>(Persist.SUCCESS, img.getLink());
				else
					return new SimpleEntry<Integer, String>(Persist.ERROR_DB_LIBRARY_CANNOT_ADD_NEW_INSTANCE, "");
			}
		}
		return new SimpleEntry<Integer, String>(Persist.ERROR_DB_IMAGE_CANNOT_ADD_NEW_INSTANCE, "");
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
	
	public static int getImagesFromUserId(String sessionkey, JSONObject json){
		if(Tools.isNullParameter(sessionkey))
			return Persist.ERROR_NULL_PARAMETER;
		else if(DBSessionKey.isSessionKeyExpired(sessionkey))
			return Persist.ERROR_SESSION_KEY_NOT_FOUND;
		else {
			List<hibernate_entity.Image> imgs = new ArrayList<>();
			User user = DBSessionKey.getUserByKey(sessionkey);
			imgs = DBImage.getImageFromUserId(user.getId());
			
			if(imgs != null) {
				for(hibernate_entity.Image img : imgs) {
					json.put(String.valueOf(img.getId()), img.getLink());	
				}
				return Persist.SUCCESS;
			} else return Persist.ERROR;
		}
	}
	
	public static int addComment(String sessionkey, String comment, int imgid, JSONObject json) {
		if(Tools.isNullParameter(sessionkey) || Tools.isNullParameter(comment))
			return Persist.ERROR_NULL_PARAMETER;
		else if(DBSessionKey.isSessionKeyExpired(sessionkey))
			return Persist.ERROR_SESSION_KEY_NOT_FOUND;
		else if(!DBImage.existeImageId(imgid))
			return Persist.ERROR_FRIEND_NOT_FOUND;
		else {
			User user = DBSessionKey.getUserByKey(sessionkey);
			hibernate_entity.Image img = DBImage.getImageById(imgid);
			int rslt = DBImage.addComment(img, comment);
			if(rslt==Persist.SUCCESS) {
				json.put("authorId", user.getId());
				json.put("text", comment);
				json.put("imgId", imgid);
				
				return Persist.SUCCESS;
			} else {
				return Persist.ERROR;
			}
		}
	}
}

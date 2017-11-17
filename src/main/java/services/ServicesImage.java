package services;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.json.JSONObject;

import database.DBAuthentification;
import database.DBImage;
import database.DBSessionKey;
import utils.Persist;
import utils.Tools;

public class ServicesImage {
	
	public static SimpleEntry<Integer, Integer> addImage(String sessionkey, String imgPath) {
		if(Tools.isNullParameter(sessionkey) || Tools.isNullParameter(imgPath))
			return new SimpleEntry<Integer, Integer>(Persist.ERROR_NULL_PARAMETER, -1);
		
		// On stocke l'adresse de l'image dans la DB Images
		int imgId = DBImage.addImage(imgPath); 
		
		// si l'id de l'image == 0, alors c'est que la requete precedente n'a pas fonctionne correctement
		if(imgId != 0)
			return new SimpleEntry<Integer, Integer>(Persist.SUCCESS, imgId);
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
			int userId = DBSessionKey.getUserIdByKey(sessionkey);
			imgs = DBImage.getImageFromUserId(userId);
			
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
			int userId = DBSessionKey.getUserIdByKey(sessionkey);
			hibernate_entity.Image img = DBImage.getImageById(imgid);
			int rslt = DBImage.addComment(img, comment);
			if(rslt==Persist.SUCCESS) {
				json.put("authorId", userId);
				json.put("text", comment);
				json.put("imgId", imgid);
				
				return Persist.SUCCESS;
			} else {
				return Persist.ERROR;
			}
		}
	}
}

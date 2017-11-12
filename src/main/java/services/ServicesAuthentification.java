package services;

import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import database.DBAuthentification;
import database.DBFriendship;
import hibernate_entity.User;
import utils.Persist;
import utils.Tools;

public class ServicesAuthentification {

	public static int createNewUser(String username, String password, String password2, String email) {
		if(Tools.isNullParameter(username) || Tools.isNullParameter(password) || Tools.isNullParameter(password2) || Tools.isNullParameter(email))
			return Persist.ERROR_NULL_PARAMETER;
		else if(!password.equals(password2))
			return Persist.ERROR_PASSWORDS_DIFFERENT;
		else if(!Tools.isPasswordValid(password))
			return Persist.ERROR_PASSWORD_NOT_VALID;
		else {
			if(DBAuthentification.existeLogin(username))
				return Persist.ERROR_DB_USERNAME_ALREADY_USED;
		
			// DB
			if(DBAuthentification.createUser(username, password, email))
				return Persist.SUCCESS;
		}
		return Persist.ERROR;
	}
	
	public static int connectUser(String username, String password, JSONObject json) throws JSONException {
		if(Tools.isNullParameter(username) || Tools.isNullParameter(password))
			return Persist.ERROR_NULL_PARAMETER;
		else {
			if(!DBAuthentification.existeLogin(username))
				return Persist.ERROR_USER_NOT_FOUND;
			if(!DBAuthentification.isPasswordExact(username, password))
				return Persist.ERROR_USER_PASSWORD_NOT_MATCH;
			
			// DB
			int userId = DBAuthentification.getIdByUsername(username);
			String sessionkey = DBAuthentification.connect(username);
			String friends = DBFriendship.getStringFromUsersSet(DBFriendship.getAllFriends(userId));
			String friendRequest = DBFriendship.getStringFromUsersSet(DBFriendship.getAllFriendRequests(userId));
			
			if(sessionkey != null){
				json.put("username", username);
				json.put("sessionKey", sessionkey);
				json.put("friends", friends);
				json.put("friendRequests", friendRequest);
				return Persist.SUCCESS;
			}
		}
		return Persist.ERROR;
	}

	public static int logoutUser(String username, String sessionkey) {
		if(Tools.isNullParameter(username) || Tools.isNullParameter(sessionkey))
			return Persist.ERROR_NULL_PARAMETER;
		else {
			if(!DBAuthentification.existeLogin(username))
				return Persist.ERROR_USER_NOT_FOUND;
			else {
				int userId = DBAuthentification.getIdByUsername(username);
				if(userId == -1)
					return Persist.ERROR_DB_USER_NOT_FOUND;
				else
					return DBAuthentification.logout(userId, sessionkey);
			}
		}
	}
	
}

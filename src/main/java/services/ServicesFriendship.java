package services;

import java.util.List;

import org.json.JSONObject;

import database.DBAuthentification;
import database.DBFriendship;
import database.DBSessionKey;
import utils.Persist;
import utils.Tools;

public class ServicesFriendship {
	
	public static int addFriend(String sessionkey, int friendId, JSONObject json) {
		if(Tools.isNullParameter(sessionkey))
			return Persist.ERROR_NULL_PARAMETER;
		else if(DBSessionKey.isSessionKeyExpired(sessionkey))
			return Persist.ERROR_SESSION_KEY_NOT_FOUND;
		else if(!DBAuthentification.existeUserId(friendId))
			return Persist.ERROR_FRIEND_NOT_FOUND;
		else {
			int userId = DBSessionKey.getUserIdByKey(sessionkey);
			
			int status = DBFriendship.getFriendshipRequestStatus(userId, friendId);
			switch(status) {
			case Persist.STATUS_FRIENDSHIP_REQUEST_SENT :
			case Persist.STATUS_FRIENDSHIP_REQUEST_ACCEPTED :
				return status;
			// on ajoute un friend uniquement si on n'a pas envoyé de demande, donc seulement quand
			// il n'y a pas de relation friendship
			case Persist.ERROR_FRIENDSHIP_NOT_FOUND :
				int rslt = DBFriendship.addFriend(userId, friendId);
				if(rslt == Persist.SUCCESS) {
					json.put("userId", ""+userId);
					return Persist.SUCCESS;
				}
				else
					return rslt;
			default:
				return Persist.ERROR;
			}
		}
	}
	
	
	public static int getAllFriends(String sessionkey, JSONObject json) {
		if(Tools.isNullParameter(sessionkey))
			return Persist.ERROR_NULL_PARAMETER;
		else if(DBSessionKey.isSessionKeyExpired(sessionkey))
			return Persist.ERROR_SESSION_KEY_NOT_FOUND;
		else {
			int userId = DBSessionKey.getUserIdByKey(sessionkey);
			
			//List<Integer> friends = DBFriendship.getAllFriends(userId);
		}
		return -1;
	}

}

package services;

import database.DBAuthentification;
import database.DBFriendship;
import database.DBSessionKey;
import utils.Persist;
import utils.Tools;

public class ServicesFriendship {
	
	public static int addFriend(String sessionkey, int friendId) {
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
			
			case Persist.ERROR_FRIENDSHIP_NOT_FOUND :
				return DBFriendship.addFriend(userId, friendId);
			default:
				return Persist.ERROR;
				
			}
		}
	}
	

}

package database;

import java.util.ArrayList;
import java.util.List;

import hibernate_entity.Friendship;
import utils.Persist;

public class DBFriendship {
	
	public static int addFriend(int userId, int friendId) {
		Friendship friendship = new Friendship(userId, friendId, Persist.STATUS_FRIENDSHIP_REQUEST_SENT);
		if(Persist.OPENED_SESSION != null) {
			Persist.OPENED_SESSION.beginTransaction();
			Persist.OPENED_SESSION.save(friendship);
			Persist.OPENED_SESSION.getTransaction().commit();
			return Persist.SUCCESS;
		}
		return Persist.ERROR;
	}
	
	public static int getFriendshipRequestStatus(int userId, int friendId) {
		String hql = "from Friendship f where f.userId='"+userId+"'";
		
		if(Persist.OPENED_SESSION != null) {
			List<Friendship> friendships = Persist.OPENED_SESSION.createQuery(hql).getResultList();
			for(Friendship friendship : friendships)
				if(friendship.getUserId() == userId && friendship.getFriendId() == friendId)
					return friendship.getState();
		}
		return Persist.ERROR_FRIENDSHIP_NOT_FOUND;
	}
	
	public static boolean isFriend(int userId1, int userId2) {
		List<Integer> friendsOfUser1 = getAllFriends(userId1);
		
		for(Integer friendIdFromUser1 : friendsOfUser1) {
			if(friendIdFromUser1.intValue() == userId2)
				return true;
		}
		
		List<Integer> friendsOfUser2 = getAllFriends(userId2);
		for(Integer friendIdFromUser2 : friendsOfUser2) {
			if(friendIdFromUser2.intValue() == userId1)
				return true;
		}	
		return false;
	}
	
	public static List<Integer> getAllFriends(int userId) {
		String hql = "from Friendship f where f.userId='"+userId+"'";
		ArrayList<Integer> rslt = new ArrayList<Integer>();
		if(Persist.OPENED_SESSION != null) {
			List<Friendship> friendships = Persist.OPENED_SESSION.createQuery(hql).getResultList();
			for(Friendship friendship : friendships)
				if(friendship.getUserId() == userId)
					rslt.add(new Integer(friendship.getFriendId()));
		}
		return rslt;
	}

}

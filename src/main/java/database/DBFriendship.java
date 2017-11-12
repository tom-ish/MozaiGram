package database;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hibernate_entity.Friendship;
import hibernate_entity.User;
import utils.Persist;

public class DBFriendship {
	
	public static int addFriend(int userId, int friendId) {
		if(userId == friendId)
			return Persist.ERROR_FRIENDSHIP_SAME_USER_ID;
		
		User user = DBAuthentification.getUserById(userId);
		User friend = DBAuthentification.getUserById(friendId);
		
		if(user != null && friend != null) {
			Friendship friendship = new Friendship();
			friendship.setFriend(friend);
			friendship.setUser(user);
			friendship.setState(Persist.STATUS_FRIENDSHIP_REQUEST_SENT);
			if(Persist.OPENED_SESSION != null) {
				Persist.OPENED_SESSION.beginTransaction();
				Persist.OPENED_SESSION.save(friendship);
				Persist.OPENED_SESSION.getTransaction().commit();
				return Persist.SUCCESS;
			}
		}
		return Persist.ERROR;
	}
	
	public static int getFriendshipRequestStatus(int userId, int friendId) {
		String hql = "from Friendship f where f.user='"+userId+"'";
		
		if(Persist.OPENED_SESSION != null) {
			List<Friendship> friendships = Persist.OPENED_SESSION.createQuery(hql).getResultList();
			for(Friendship friendship : friendships)
				if(friendship.getUser().getId() == userId && friendship.getFriend().getId() == friendId)
					return friendship.getState();
		}
		return Persist.ERROR_FRIENDSHIP_NOT_FOUND;
	}
	
	public static boolean isFriend(int userId1, int userId2) {
		List<Integer> friendsOfUser1 = getAllFriendsIds(userId1);
		
		for(Integer friendIdFromUser1 : friendsOfUser1) {
			if(friendIdFromUser1.intValue() == userId2)
				return true;
		}
		
		List<Integer> friendsOfUser2 = getAllFriendsIds(userId2);
		for(Integer friendIdFromUser2 : friendsOfUser2) {
			if(friendIdFromUser2.intValue() == userId1)
				return true;
		}	
		return false;
	}
	
	public static List<Integer> getAllFriendsIds(int userId) {
		String hql = "from Friendship f where f.user='"+userId+"'";
		ArrayList<Integer> rslt = new ArrayList<Integer>();
		if(Persist.OPENED_SESSION != null) {
			List<Friendship> friendships = Persist.OPENED_SESSION.createQuery(hql).getResultList();
			for(Friendship friendship : friendships)
				if(friendship.getUser().getId() == userId)
					rslt.add(new Integer(friendship.getFriend().getId()));
		}
		return rslt;
	}
	
	public static Set<User> getAllFriends(int userId) {
		String hql = "from Friendship f where f.user='"+userId+"'";
		Set<User> rslt = new HashSet<User>();
		
		if(Persist.OPENED_SESSION != null) {
			List<Friendship> friendships = Persist.OPENED_SESSION.createQuery(hql).getResultList();
			for(Friendship friendship : friendships)
				if(friendship.getUser().getId() == userId)
					for(Friendship f : friendship.getUser().getAllFriends())
						if(f.getState() == Persist.STATUS_FRIENDSHIP_REQUEST_ACCEPTED)
							rslt.add(f.getFriend());
		}
		return rslt;
	}
	
	public static Set<User> getAllFriendsRequests(int userId) {
		String hql = "from Friendship f where f.user='"+userId+"'";
		Set<User> rslt = new HashSet<User>();
		
		if(Persist.OPENED_SESSION != null) {
			List<Friendship> friendships = Persist.OPENED_SESSION.createQuery(hql).getResultList();
			for(Friendship friendship : friendships)
				if(friendship.getUser().getId() == userId)
					for(Friendship f : friendship.getUser().getAllFriends())
						if(f.getState() == Persist.STATUS_FRIENDSHIP_REQUEST_SENT)
							rslt.add(f.getFriend());
		}
		return rslt;
	}

}

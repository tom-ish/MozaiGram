package hibernate_entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="friendship")
public class Friendship {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="userid")
	private int userId;
	
	@Column(name="friendid")
	private int friendId;
	
	@Column(name="state")
	private int state;
	
	public Friendship() {}
	
	public Friendship(int userId, int friendId, int state) {
		this.userId = userId;
		this.friendId = friendId;
		this.state = state;
	}
	
	public int getUserId() { return this.userId; }
	public int getFriendId() { return this.friendId; }
	public int getState() { return this.state; }
	
	public void setUserId(int userId) { this.userId = userId; }
	public void setFriendId(int friendId) { this.friendId = friendId; }
	public void setState(int state) { this.state = state; }
	
	@Override
	public String toString() {
		return "Friendship [id=" + id + ", userId=" + userId + ", friendId=" + friendId + "]";
	}
}

package hibernate_entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="friendship")
public class Friendship {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="userid", nullable=false)
	private User user;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="friendid", nullable=false)
	private User friend;
	
	@Column(name="state")
	private int state;
	
	public Friendship() {}
	
	public Friendship(User friend, int state) {
		this.friend = friend;
		this.state = state;
	}
	
	public User getFriend() { return this.friend; }
	public int getState() { return this.state; }
	public User getUser() { return this.user; }
	
	public void setFriend(User friend) { this.friend = friend; }
	public void setState(int state) { this.state = state; }
	public void setUser(User user) { this.user = user; }
	
	@Override
	public String toString() {
		return "Friendship [id=" + id + ", userId=" + user.getId() + ", friendId=" + friend.getId() + "]";
	}
}

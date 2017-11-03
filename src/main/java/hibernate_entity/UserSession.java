package hibernate_entity;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="user_session")
public class UserSession {
	
	@Id
	@Column(name="sessionkey")
	private String sessionkey;
	
	@Column(name="userId")
	private int userId;
	
	@Column(name="since")
	@CreationTimestamp
	private Date since;
	
	public UserSession() {}
	
	public UserSession(String sessionkey, int userId) {
		this.sessionkey = sessionkey;
		this.userId = userId;
	}
	
	public String getSessionkey() { return this.sessionkey; }
	public int getUserId() { return this.userId; }
	public Date getSince() { return this.since; }
	
	public void setSessionkey(String sessionkey) { this.sessionkey = sessionkey; }
	public void setUserId(int userId) { this.userId = userId; }
	public void setSince(Date created) { this.since = created; }

	@Override
	public String toString() {
		return "UserSession [sessionkey=" + sessionkey + ", userId=" + userId + ", since=" + since + "]";
	}

}

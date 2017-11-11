package hibernate_entity;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="user_session")
public class UserSession {
	
	@Id
	@Column(name="sessionkey")
	private String sessionkey;
	
	@GeneratedValue(generator="newGenerator")
	@GenericGenerator(name="newGenerator", strategy="foreign", parameters= { @Parameter(value="user", name="property")})
	private int userId;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="userId")
	private User user;
	
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
	public User getUser() { return this.user; }
	
	public void setSessionkey(String sessionkey) { this.sessionkey = sessionkey; }
	public void setUserId(int userId) { this.userId = userId; }
	public void setSince(Date created) { this.since = created; }
	public void setUser(User user) { this.user = user; }

	@Override
	public String toString() {
		return "UserSession [sessionkey=" + sessionkey + ", userId=" + userId + ", since=" + since + "]";
	}

}

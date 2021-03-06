package hibernate_entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="images")
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String link;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="userid", nullable=false)
	private User user;
	
	@Column(name="creation_date")
	@CreationTimestamp
	private Date creationDate;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="img")
	private Set<Comment> comments;
	
	
	public Image() {}
	
	public Image(String link, User user) {
		this.link = link;
		this.user = user;
		this.comments = new HashSet<Comment>();
	}
	
	public int getId() { return this.id; }
	public String getLink() { return this.link; }
	public Set<Comment> getComments() { return this.comments; }
	public Date getCreationDate() { return this.creationDate; }
	public User getUser() { return this.user; }
	
	public void setId(int id) { this.id = id; }
	public void setLink(String link) { this.link = link; }
	public void setComments(Set<Comment> comments) { this.comments = comments; }
	public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
	public void setUser(User user) { this.user = user; }

	@Override
	public String toString() {
		return "Image [id=" + id + ", link=" + link + ", user=" + user + ", creationDate=" + creationDate
				+ ", comments=" + comments + "]";
	}
	
	

}

package hibernate_entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="images")
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String link;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="img")
	private Set<Comment> comments;
	
	
	public Image() {}
	
	public Image(String link) {
		this.link = link;
		this.comments = new HashSet<Comment>();
	}
	
	public int getId() { return this.id; }
	public String getLink() { return this.link; }
	public Set<Comment> getComments() { return this.comments; }
	
	public void setId(int id) { this.id = id; }
	public void setLink(String link) { this.link = link; }
	public void setComments(Set<Comment> comments) { this.comments = comments; }

}

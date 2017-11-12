package hibernate_entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="library")
public class Library {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="userid", nullable=false)
	private User user;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="imgid")
	private Set<Image> images;
	
	public Library() {}
	
	public Library(User user) {
		this.user = user;
		this.images = new HashSet<Image>();
	}
	
	public int getId() { return this.id; }
	public User getUser() { return this.user; }
	public Set<Image> getImages() { return this.images; }
	
	public void setId(int id) { this.id = id; }
	public void setUser(User user) { this.user = user; }
	public void setImages(Set<Image> images) { this.images = images; }

}

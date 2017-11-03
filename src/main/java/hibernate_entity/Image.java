package hibernate_entity;

import javax.persistence.*;

@Entity
@Table(name="images")
public class Image {
	
	@Id
	@Column(name="id")
	private int id;
	private String link;
	
	public Image() {}
	
	public Image(String link) {
		this.link = link;
	}
	
	public int getId() { return this.id; }
	public String getLink() { return this.link; }
	
	public void setId(int id) { this.id = id; }
	public void setLink(String link) { this.link = link; }

}

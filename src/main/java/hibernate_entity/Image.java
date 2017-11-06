package hibernate_entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="images")
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

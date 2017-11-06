package hibernate_entity;

import javax.persistence.*;

@Entity
@Table(name="library")
public class Library {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="userId")
	private int userId;
	
	@Column(name="imgId")
	private int imgId;
	
	public Library() {}
	
	public Library(int userId, int imgId) {
		this.userId = userId;
		this.imgId = imgId;
	}
	
	public int getId() { return this.id; }
	public int getUserId() { return this.userId; }
	public int getImgId() { return this.imgId; }
	
	public void setId(int id) { this.id = id; }
	public void setUserId(int userId) { this.userId = userId; }
	public void setImgId(int imgId) { this.imgId = imgId; }

}

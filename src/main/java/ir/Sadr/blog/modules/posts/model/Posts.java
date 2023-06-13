package ir.Sadr.blog.modules.posts.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ir.Sadr.blog.modules.users.model.Users;
//import javax.validation.constraints.Size;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Component
@Table(name = "posts_tbl")  
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Posts {

	@Id
	@GeneratedValue
	private Long id ;

	//@Size(min = 3, max = 10, message = "Title must be between 3 and 10 characters")
	@NotBlank(message = "Must not be blank")
	private String title ;
	
	//@Lob  -> for when we want save a string longer than 255 characters or any data in general
	@NotBlank(message = "Must not be blank")
	private String body ;
	private String cover ;
	
	// A field in this table is created 
	// from the target entity, because 
	// we are on the side of many, 
	// and an instance of the target 
	// table must be stored here.
	//@NotNull
	@ManyToOne 
	//@JoinColumn(name = "user_fk" , referencedColumnName = "created_at" /* name in database*/)
	@JoinColumn(name = "user_fk") // reference to primary key of Users Entity
	@JsonIgnore // Dont show this proprty in Json response
	//@JsonManagedReference // -- 1 -- in post entity go to user entity
	private Users users ;
	
	@NotEmpty(message = "Must not be Empty") 
	@ManyToMany
	@JoinTable(name = "post_category")
	private List<Category> categories ;
	
	@Column(name = "created_at" , updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt ;
	
	@Transient // don`t save to database
	@JsonIgnore
	private MultipartFile file ;
	
	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt ;
	
	// Constructors  
	
	public Posts() { 
		
	}
	public Posts(String title , String body) {
		this.title = title ;
		this.body = body ;
	}
	public Posts(String title , String body , String cover) {
		super();
		this.title = title ;
		this.body = body ;
		this.cover = cover ;
	}
	
	// Setters 
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public void setFile(MultipartFile file) {
		this.file = file ;
	}

	// Getters
	
	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getBody() {
		return body;
	}
	public String getCover() {
		return cover;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public Users getUsers() {
		return users;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public MultipartFile getFile() {
		return file ;
	}
	
}

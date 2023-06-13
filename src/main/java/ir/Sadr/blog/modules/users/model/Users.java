package ir.Sadr.blog.modules.users.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ir.Sadr.blog.enums.Roles;
import ir.Sadr.blog.modules.posts.model.Posts;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Component
@Table(name = "users_tbl")
@SuppressWarnings("serial")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id"/*property = "email"*/)
public class Users implements Serializable{ 

	@Id
	@GeneratedValue
	private Long id ;
	
	@Email(message = "Follow the email format")
	@NotBlank(message = "Must not be blank")
	@Column(unique = true)
	private String email ;
	
	@NotBlank(message = "Must not be blank")
	private String name ; 
	
	@JsonIgnore
	//@NotBlank
	private String password ;
	private String cover ;
	private boolean enable = true ;  
	
	@OneToMany(mappedBy = "users") 
	//@JoinTable(joinColumns = @JoinColumn(referencedColumnName = "id") ,inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
	//@JsonBackReference // dont show posts in entity
	private List<Posts> posts ;
	
	@Column(name = "created_at" , updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt ;
	
	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt ;
	
	@NotEmpty(message = "Must not be empty")
	@ElementCollection(targetClass = Roles.class)
	@CollectionTable(name = "authorities" , 
	joinColumns = @JoinColumn(name = "email" , referencedColumnName = "email"))
	@Enumerated(EnumType.STRING)
	@JsonIgnore
	private List<Roles> roles ;
	
	@Transient
	@JsonIgnore
	private MultipartFile file ;

	// Constructors 
	
	public Users() {
		this("","","","");
	}
	public Users(String email, String name, String password, String cover) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
		this.cover = cover;
	}
	
	// Setters 
	
	public void setEmail(String email) {
		this.email = email;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public void setId(Long id) {
		this.id = id;
	}
	public void setPosts(List<Posts> post) {
		this.posts = post;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
		
	// Getters 
	
	public Long getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
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
	public List<Posts> getPosts() {
		return posts;
	}
	public boolean isEnable() {
		return enable;
	}
	public List<Roles> getRoles() {
		return roles;
	}
	public MultipartFile getFile() {
		return file;
	}
	
}

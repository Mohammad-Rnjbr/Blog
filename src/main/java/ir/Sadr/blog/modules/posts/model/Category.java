package ir.Sadr.blog.modules.posts.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Component
@Table(name = "category_tbl")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Category {

	@Id
	@GeneratedValue
	private Long id ; 
	
	@NotBlank/*(message = "NOT BLANK")*/ 
	private String title ;
	
	@Column(name = "created_at" , updatable = false)
	@CreationTimestamp
	private LocalDateTime createdAt ;
	
	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	@ManyToMany(mappedBy = "categories")
	@JsonIgnore // will fetch posts in category don`t show posts
	//We must put this annotation property, 
	//otherwise it will create an 
	//intermediate table again
	private List<Posts> posts;
	
	// Constructors 
	
	public Category() {
		this("");
	}
	public Category(String title) {
		this.title = title ;
	}
	
	// Setters 
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public void setPosts(List<Posts> posts) {
		this.posts = posts;
	}
	
	// Getters 
	
	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
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
	
}

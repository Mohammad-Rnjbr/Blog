package ir.Sadr.blog.modules.posts.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ir.Sadr.blog.MyBeanCopy;
import ir.Sadr.blog.modules.posts.model.Posts;
import ir.Sadr.blog.modules.posts.repository.PostsRepository;
//import java.time.LocalDateTime;

@Service
public class PostsService {

	private PostsRepository postsRepository ;
	
	@Autowired
	public PostsService(PostsRepository postsRepository) {
		this.postsRepository = postsRepository ;
	}
	
	@Transactional
	public Posts registerPosts(Posts post) throws IOException, IllegalAccessException, InvocationTargetException {
		if(!post.getFile().isEmpty()) {
			//post.setCreatedAt(LocalDateTime.now()); // Once Approach 
			String path = ResourceUtils.getFile("classpath:static/img").getAbsolutePath() ;
			byte[] bytes = post.getFile().getBytes(); // image*0*.gng*1*  // It is likely to be null
			String name = UUID.randomUUID() + "." + Objects.requireNonNull(post.getFile().getContentType()).split("/")[1];
			//Files.write(Paths.get(path + File.separator + post.getFile().getOriginalFilename()), bytes);
			Files.write(Paths.get(path + File.separator + name), bytes);
			post.setCover(name);
		}
		if(post.getId() != null) {
			Posts exist = this.getPostById(post.getId());
			MyBeanCopy myBeanCopy = new MyBeanCopy();
			myBeanCopy.copyProperties(exist,post);
			return postsRepository.save(exist); 
		}
		return postsRepository.save(post);
	}
	
	public List<Posts> findAllPost(){ 
		return postsRepository.findAll();
	}
	
	public Page<Posts> findAllPost(Pageable pageable){
		return postsRepository.findAll(pageable);
	}

	public Posts getPostById(Long id) {
		Optional<Posts> post = postsRepository.findById(id);
		if(post.isPresent()) {
			return post.get();
		}
		return null ;
	}
	
	@Transactional
	public void deletePostById(Long id) {
		postsRepository.deleteById(id); 
	}

	public List<Posts> getBySearch(Posts post) {
		return postsRepository.findByTitleContaining(post.getTitle());
	}
	
	public Page<Posts> getPostsBySearch(Posts post , Pageable pageable){
		return postsRepository.findBySearch(post,post.getCategories() != null ? (long) post.getCategories().size() : 0 , pageable, post.getTitle());
	}

}

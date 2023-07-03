package ir.Sadr.blog.modules.posts.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ir.Sadr.blog.modules.posts.model.Posts;
import ir.Sadr.blog.modules.posts.service.CategoryService;
import ir.Sadr.blog.modules.posts.service.PostsService;
import ir.Sadr.blog.modules.users.service.UsersService;
//import ir.Sadr.blog.modules.users.model.Users;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;

//@RestController
@Controller
@RequestMapping("/posts") 
public class PostsController {
	
	private Posts post ;
	private PostsService postsService ;
	private CategoryService categoryService ;
	private UsersService usersService ;
	
	@Autowired
	public PostsController(PostsService postsService , CategoryService categoryService , Posts post , UsersService usersService) {
		this.post = post ;
		this.postsService = postsService ;
		this.categoryService = categoryService ;
		this.usersService = usersService ;
	}
	
	@RequestMapping(value = "/rest/registerPost" , method = RequestMethod.POST)
	public @ResponseBody Posts restRegisterPosts(@RequestBody Posts post) throws IOException, IllegalAccessException, InvocationTargetException {
		return postsService.registerPosts(post);
	}
	
	/*@PostMapping({"","/"})
	public Posts registerPosts(Posts post) {
		return postsService.registerPosts(post);
	}*/
	
	@RequestMapping(value = "/rest/getPosts" , method = RequestMethod.GET)
	public @ResponseBody List<Posts> getPosts(){ // for show rest
		return postsService.findAllPost();
	}
	
	/*@GetMapping({"/",""})
	public List<Posts> getPosts(){
		return postsService.findAllPost();
	}*/

	@GetMapping("")
	public String showPosts(@ModelAttribute("p") Posts posts , Model model , @PageableDefault(size = 5 , sort = {"createdAt","title","id"}) Pageable pageable) {  
		model.addAttribute("categories",categoryService.findAllCategories());
		//model.addAttribute("posts",postsService.findAllPost(pageable));
		model.addAttribute("posts",postsService.getPostsBySearch(posts,pageable));
		return "posts/posts";
	}
	
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("post",post); 
		model.addAttribute("categories",categoryService.findAllCategories());
		return "posts/registerPosts";
	}
	
	@PostMapping("/register")                 // forward model name not forward model attribute name
	public String registerPosts(@ModelAttribute(name = "post") @Valid Posts posts, 
            BindingResult bindingResult, Principal principal , Model model) throws IllegalAccessException , IOException, InvocationTargetException{
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories",categoryService.findAllCategories()); 
			return "posts/registerPosts";
        }
		posts.setUsers(usersService.getUserByEmail(principal.getName())); 
		postsService.registerPosts(posts);
		return "redirect:/posts"; 
	}
	
	@GetMapping("/edit/{id}")
	public String editPosts(@PathVariable Long id , Model model) {
		model.addAttribute("post",postsService.getPostById(id));
		model.addAttribute("categories",categoryService.findAllCategories());
		return "posts/registerPosts";
	}
	
	@GetMapping("/delete/{id}")
	public String deletePosts(@PathVariable Long id) {
		postsService.deletePostById(id);
		return "redirect:/posts"; 
	}
	
	@GetMapping("/viewPost/{id}")
	public String viewPosts(@PathVariable Long id , Model model) {
		model.addAttribute("post",postsService.getPostById(id)); 
		return "/posts/showPost";
	}
	
	/*@GetMapping("/search")
	public @ResponseBody List<Posts> searchByJpa(@ModelAttribute Posts post){
		return postsService.getBySearch(post);
	}*/
	
	/*@GetMapping("/search")
	public @ResponseBody List<Posts> search(@ModelAttribute Posts post){
		return postsService.getPostsBySearch(post);
	}*/
	
}

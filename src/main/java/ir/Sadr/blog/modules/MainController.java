package ir.Sadr.blog.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ir.Sadr.blog.modules.posts.service.PostsService;
import ir.Sadr.blog.modules.users.model.Users;

@Controller
public class MainController {

	private Users user ;
	private PostsService postsService ;
	
	@Autowired
	public MainController(PostsService postsService , Users user) {
		this.postsService = postsService ;
		this.user = user ;
	}
	
	@GetMapping(value = "")
	public String index(Model model , @PageableDefault(size = 15) Pageable pageable) {
		model.addAttribute("posts",postsService.findAllPost(pageable));
		return "index";
	}
	
	@GetMapping(value = "/login")
	public String login(Model model) {
		model.addAttribute("user",user); 
		return "login"; 
	}
	
	@GetMapping("/403")
	public String accessDeniedError() {
		return "403";
	}
	
	@GetMapping("/member/register")
	public String register(Model model) {
		model.addAttribute("user",user);
		return "register";
	}
	
}

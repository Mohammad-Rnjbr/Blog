package ir.Sadr.blog.modules.users.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import ir.Sadr.blog.modules.users.model.Users;
import ir.Sadr.blog.modules.users.service.UsersService;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/users")
public class UsersController {

	private UsersService usersService ;
	private Users user ;
	
	@Autowired
	public UsersController(UsersService usersService , Users user) {
		this.usersService = usersService ;
		this.user = user ;
	}
	
	@RequestMapping(value = "/resr/getUsers" , method = RequestMethod.GET)
	public @ResponseBody List<Users> getUsres(){
		return usersService.findAllUsers();
	}
	
	/*@GetMapping("/")
	public List<Users> getUsres(){
		return usersService.findAllUsers();
	}*/
	
	/* 
	localhost8080:/users
	and
	localhost8080:/users/
	*/
	@RequestMapping(value = "/rest/registerUser" , method = RequestMethod.POST)
	public @ResponseBody Users restRegisterUser(@RequestBody Users users) throws IOException, IllegalAccessException, InvocationTargetException {
		return usersService.registerUser(users);
	}
	
	/*@PostMapping("/")
	public Users registerUser(@RequestBody Users user) {
		return usersService.registerUser(user);
	}*/
	
	@GetMapping("")
	public String showUsers(Model model,Principal principal) {
		model.addAttribute("users",usersService.findAllUsers());
		model.addAttribute("u",usersService.getUserByEmail(principal.getName()));
		return "users/users";
	}
	
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("users",user);
		return "users/registerUsers";
	}
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute(name = "users") @Valid Users user , BindingResult bindingResult) throws IOException, IllegalAccessException, InvocationTargetException {
		if (bindingResult.hasErrors())
            return "users/registerUsers";
		
		usersService.registerUser(user);
		return "redirect:/users"; 
	}

	@GetMapping("/edit/{id}")
	public String editUser(@PathVariable Long id , Model model) {
		model.addAttribute("users",usersService.getUserById(id));
		return "users/registerUsers";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		usersService.deleteUserById(id); 
		return "redirect:/users";
	}
	
}

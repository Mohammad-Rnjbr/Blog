package ir.Sadr.blog.modules.posts.controller;

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
import ir.Sadr.blog.modules.posts.model.Category;
import ir.Sadr.blog.modules.posts.service.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	private CategoryService categoryService ;
	private Category category ;
	
	@Autowired
	public CategoryController(CategoryService categoryService , Category category) {
		this.categoryService = categoryService ;
		this.category = category ; 
	}
	
	/*
	localhost8080:/categories
	and
	localhost8080:/categories/
	*/
	@RequestMapping(value = "/rest/registerCategory" , method = RequestMethod.POST)
	public @ResponseBody Category restRegisterCategory(@RequestBody Category category) {
		return categoryService.registerCategory(category);
	}
	
	@RequestMapping(value = "/rest/getCategories" , method = RequestMethod.GET)
	public @ResponseBody List<Category> retsGetCategories(){
		return categoryService.findAllCategories();
	}
	
	@GetMapping("")
	public String showCategories(Model model) {
		model.addAttribute("categories",categoryService.findAllCategories());
		return "categories/categories";
	}
	
	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("category",category); 
		return "categories/registerCategories";
	}
	
	@PostMapping("/register")
	public String registerCategory(@ModelAttribute @Valid Category category , BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "categories/registerCategories";
		}
		categoryService.registerCategory(category); 
		return "redirect:/categories";
	}
	
	@GetMapping("/edit/{id}")
	public String editCategory(@PathVariable Long id , Model model) {
		model.addAttribute("category",categoryService.findById(id));
		return "categories/registerCategories";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable Long id) {
		categoryService.deleteCategoryById(id);
		return "redirect:/categories";
	}
	
}

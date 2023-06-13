package ir.Sadr.blog.modules.posts.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ir.Sadr.blog.modules.posts.model.Category;
import ir.Sadr.blog.modules.posts.repository.CategoryRepository;

@Service
public class CategoryService {

	private CategoryRepository categoryRepository ;
	
	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository ;
	}
	
	@Transactional
	public Category registerCategory(Category category) {
		return categoryRepository.save(category);
	}
	
	public List<Category> findAllCategories(){
		return categoryRepository.findAll();
	}

	public Category findById(Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		if(category.isPresent()) {
			return category.get();
		}
		return null ;
	}
	
	@Transactional
	public void deleteCategoryById(Long id) {
		categoryRepository.deleteById(id); 
	}
	
}

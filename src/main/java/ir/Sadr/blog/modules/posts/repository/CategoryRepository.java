package ir.Sadr.blog.modules.posts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ir.Sadr.blog.modules.posts.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{

}

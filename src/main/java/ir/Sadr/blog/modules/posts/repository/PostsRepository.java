package ir.Sadr.blog.modules.posts.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ir.Sadr.blog.modules.posts.model.Posts;

@Repository
public interface PostsRepository extends JpaRepository<Posts,Long>{

	// It is not necessary that the whole string matches,
	// a part of it is enough
	public List<Posts> findByTitleContaining(String title);
	
	//Search by title with query
	//@Query("select p from Posts p where :#{#posts.title} is null or p.title like concat('%',:#{#posts.title},'%')")
	//public List<Posts> findBySearch(Posts posts);
	
	//We do not have direct access to the collection, we must use join
	//@Query("select p from Posts p join p.categories as pc where :#{#posts.categories} is null or pc in (:#{#posts.categories})")
	//public List<Posts> findBySearch(Posts posts);
	
	//To check if the collection is null 
	//@Query("select p from Posts p join p.categories as pc where coalesce(:#{#posts.categories},null) is null or"
	//		+ " pc in (:#{#posts.categories}) group by p.id having count (p.id) = :num")
	//public List<Posts> findBySearch(Posts posts,@Param("num") Long size);
	
	/*@Query("select p from Posts p join p.categories as pc where"
			+ "(:#{#posts.title} is null or p.title like concat('%',:#{#posts.title},'%')) "
			+ "and (coalesce(:#{#posts.categories},null) is null or"
			+ " pc in (:#{#posts.categories})) group by p.id having count (p.id) >= :num")
	public List<Posts> findBySearch(Posts posts,@Param("num") Long size);*/
	
	@Query("select p from Posts p join p.categories pc where (:#{#posts.title} is null or " +
            "p.title like %:keyword% ) and " +
            "(coalesce(:#{#posts.categories},null) is null or " +
            "pc in (:#{#posts.categories})) " +
            "group by p.id having count (p.id) >= :num")
    Page<Posts> findBySearch(Posts posts, @Param("num") Long size, Pageable pageable , @Param("keyword") String keyword);
    
}

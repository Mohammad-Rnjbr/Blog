package ir.Sadr.blog.modules.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ir.Sadr.blog.modules.users.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long>{

	public Users findByEmail(String email);
	
	/*@Query(nativeQuery = true , value = "select * from users_tbl")
	public Users findUserByQuery();*/
	
	/*@Query("select u from Users u") // JPQL language -> u object with all property
	public Users findUserByQuery();*/
	
	/*@Query("select u.email,u.name from Users u") // JPQL language -> u object with name and email property
	public Users findUserByQuery();*/
	
	@Query("select u from Users u where u.email = :email")
	public Users findUserByQuery(@Param("email") String email);

}

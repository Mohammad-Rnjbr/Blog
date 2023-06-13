package ir.Sadr.blog.modules.users.service;

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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ir.Sadr.blog.MyBeanCopy;
import ir.Sadr.blog.modules.users.model.Users;
import ir.Sadr.blog.modules.users.repository.UsersRepository;
//import ir.Sadr.blog.MyBeanCopy;

@Service
public class UsersService {

	private UsersRepository usersRepository ;
	
	@Autowired
	public UsersService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository ;
	}
	
	@Transactional
	public Users registerUser(Users users) throws IOException, IllegalAccessException, InvocationTargetException{
		if(!users.getFile().isEmpty()){
			String path = ResourceUtils.getFile("classpath:static/img/").getAbsolutePath() ;
			byte[] bytes = users.getFile().getBytes(); // image*0*.gng*1*  // It is likely to be null
			String name = UUID.randomUUID() + "." + Objects.requireNonNull(users.getFile().getContentType()).split("/")[1];
			//Files.write(Paths.get(path + File.separator + post.getFile().getOriginalFilename()), bytes);
			Files.write(Paths.get(path + File.separator + name), bytes);
			users.setCover(name);
		}
		if(!users.getPassword().isEmpty()) {
			users.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
		}
		if(users.getId() != null) {
			Users exist = this.getUserById(users.getId());
			MyBeanCopy myBeanCopy = new MyBeanCopy();
			myBeanCopy.copyProperties(exist,users);
			return usersRepository.save(exist);
		}
		return usersRepository.save(users); 
	}
	
	public List<Users> findAllUsers(){
		return usersRepository.findAll();
	}

	public Users getUserById(Long id) {
		Optional<Users> user = usersRepository.findById(id);
		if(user.isPresent()) {
			return user.get();
		}
		return null ;
	}

	@Transactional
	public void deleteUserById(Long id) {
		usersRepository.deleteById(id); 
	}

	public Users getUserByEmail(String email) {
		return usersRepository.findByEmail(email);
	}
	
}

package ir.Sadr.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		//System.out.println(new BCryptPasswordEncoder().encode("12345"));
		
		SpringApplication.run(BlogApplication.class, args);
		
	}  
 
}
  
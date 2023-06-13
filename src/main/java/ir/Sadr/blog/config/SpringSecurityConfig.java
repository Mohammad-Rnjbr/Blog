package ir.Sadr.blog.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource ;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(new BCryptPasswordEncoder()) 
			//.passwordEncoder(NoOpPasswordEncoder.getInstance()) 
			.usersByUsernameQuery("select email,password,enable from users_tbl where email=?")
			.authoritiesByUsernameQuery("select email,roles from authorities where email=?"); 
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests() 
			.antMatchers("/posts/search","/","/index","/img/**","/css/**","/js/**","/posts/viewPost/{id}") 
			.permitAll()
			.and()
			.authorizeRequests().antMatchers("/users/**","/categories/**")
			//.hasRole("ADMIN") // .hasRole("ROLE_ADMIN")
			.hasAuthority("ADMIN") 
			.and()
			.authorizeRequests().antMatchers("/posts/**")
			.hasAnyAuthority("ADMIN","USER")
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login")
			.usernameParameter("email").permitAll()
			.and()
			.logout().logoutSuccessUrl("/").permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/403"); 
	}

}

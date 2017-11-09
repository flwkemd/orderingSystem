package demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private static final String REMEMBER_ME_KEY = "Key";
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()   
			.antMatchers("/admin/**").hasRole("ADMIN")      
			.antMatchers("/user/**").hasRole("USER");                 
		
		http.formLogin()
			.loginPage("/login").permitAll();
		
		//http.rememberMe().key(REMEMBER_ME_KEY).rememberMeServices(tokenBasedRememberMeServices()); //토큰 방식
		http.rememberMe().key(REMEMBER_ME_KEY).rememberMeServices(persistentTokenBasedRememberMeServices()); //영속화 방식
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//토큰 방식
	@Bean
	public TokenBasedRememberMeServices tokenBasedRememberMeServices(){
		TokenBasedRememberMeServices tokenBasedRememberMeServices =
				new TokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService);
		tokenBasedRememberMeServices.setCookieName("Cookie");
		return tokenBasedRememberMeServices;
	}
	
	//영속화 방식
	@Bean
	public PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices(){
		PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices
				= new PersistentTokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService, jdbcTokenRepository());
		return persistentTokenBasedRememberMeServices;
	}
	
	//jdbc token
	@Bean
	public JdbcTokenRepositoryImpl  jdbcTokenRepository(){
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setCreateTableOnStartup(false);
		jdbcTokenRepository.setDataSource(dataSource);
		return jdbcTokenRepository;
	}
	
}
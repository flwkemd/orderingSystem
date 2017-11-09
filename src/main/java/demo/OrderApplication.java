package demo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import demo.domain.Account;
import demo.repository.AccountRepository;

@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Bean
	InitializingBean insertFixtureUsers() {
		return () ->{
			 Account admin = new Account();
	            admin.setUserid("admin");
	            admin.setPassword(passwordEncoder.encode("1234"));
	            admin.setRole("ROLE_ADMIN");
	            admin.setNick("관리자");
	            accountRepository.save(admin);

	            Account user = new Account();
	            user.setUserid("user");
	            user.setPassword(passwordEncoder.encode("1234"));
	            user.setRole("ROLE_USER");
	            user.setNick("유저");
	            accountRepository.save(user);
		};
	}
	
}

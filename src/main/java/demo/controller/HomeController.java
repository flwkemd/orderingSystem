package demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import demo.domain.Account;
import demo.repository.AccountRepository;
import demo.security.UserDetailsImpl;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String indexQuery() {
		return "index";
	}
	
	@RequestMapping("/admin")
	public void admin()	{
	}

	@RequestMapping("/login")
	public void login()	{
	}

	@RequestMapping("/user")
	public void user()	{
	}

	@RequestMapping("/registrationForm")
	public void registrationForm()	{
	}
	
	// 필요한 부분은 Service 계층으로 옮겨줘야 합니다.
	@Autowired 
	AccountRepository accountRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(Account account){
		// 회원정보 데이터베이스 저장
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		accountRepository.save(account);
		
		// SecurityContextHolder에서 Context를 받아 인증 설정
		UserDetailsImpl userDetails = new UserDetailsImpl(account);
		Authentication authentication = 
				new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
		return "redirect:/";
	}
	
	
	
}

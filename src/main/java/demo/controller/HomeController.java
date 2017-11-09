package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	
}

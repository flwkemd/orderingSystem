package demo.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import demo.domain.Account;

public class UserDetailsImpl extends User {

	private String nick;
	
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public UserDetailsImpl(Account account) {
		super(account.getUserid(), account.getPassword(), authorities(account));
		this.nick = account.getNick();
	}
	
	private static Collection<? extends GrantedAuthority> authorities(Account account) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(account.getRole()));
		return authorities;

	}
	
}

package com.example.leo.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;//
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.leo.entity.Role;
import com.example.leo.repository.UserRepository;




@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
     @Autowired
    UserRepository userRepository; 	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	
		
		com.example.leo.entity.User appUser = userRepository.findUserByEmail( email).orElseThrow(() -> new UsernameNotFoundException("Login email details Invalido")); 
		Set <GrantedAuthority> grantList = new HashSet<GrantedAuthority>(); 
		for (Role role : appUser.getRoles() ) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getDescription()); 
		    grantList.add(grantedAuthority);
		}

		UserDetails user = (UserDetails) new User(email, appUser.getPassword(), grantList);
		return user;	
		
	}
	//verificar los transaccional su import



}

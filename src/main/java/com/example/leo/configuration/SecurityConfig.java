package com.example.leo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;




@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

 
  	String[] resources = new String[]{ 
            "/include/**","/css/**","/images/**","/productImages/**","/js/**","/layer/**"
    };
 
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	    	http 
	        .authorizeRequests()  
	        .antMatchers(resources).permitAll() 
	        .antMatchers("/","/shop/**","/home/**","/cart/**","/forgot_password","/reset_password","/register").permitAll()
	            .anyRequest().authenticated() 
	            .and()                      
	        .formLogin()
	            .loginPage("/login") 
	            .permitAll()  
	            .defaultSuccessUrl("/") 
	            .failureUrl("/login?error=true")
	            .usernameParameter("email")
	            .passwordParameter("password") 
	            .and()
	            .csrf().disable() 
	            .logout()
	                .permitAll()
	                .logoutSuccessUrl("/login?logout");
	    }
	
	
	  BCryptPasswordEncoder bCryptPasswordEncoder;

	    @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
			bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
	        return bCryptPasswordEncoder;
	    }
	    
	    @Autowired
	    UserDetailsService userDetailsService;
	    //configure
	    @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 	
	        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()); 
	    }
	
}

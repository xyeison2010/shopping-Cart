package com.example.leo.service;

import com.example.leo.Exception.UserNotFoundException;
import com.example.leo.Exception.UsernameOrIdNotFound;
import com.example.leo.dto.ChangePasswordForm;
import com.example.leo.entity.User;

//to esta en userserviceImpl
public interface UserService  {
	public User findByEmail(String email);
    public Iterable<User> getAllUsers();//1 traigo usuarios

	public User createUser( User user) throws Exception;//2	

	public User getUserById(Long id) throws UsernameOrIdNotFound;//3 

	public User updateUser(User user) throws Exception;//4

	public void deleteUser(Long id) throws UsernameOrIdNotFound;//5

	public User changePassword(ChangePasswordForm form) throws Exception;//6
	
	public User save(User user);
	
	//public User fetchByIdWithVentas(Long id);
	//forgotpassword
	public void updateResetPasswordToken(String token ,String email) throws UserNotFoundException;
	
	public User get(String resetPasswordToken) ;//verifica
		
	public void updatePassword(User user, String newPassword) ;
		
}

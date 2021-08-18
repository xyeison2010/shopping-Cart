package com.example.leo.service.impl;


import java.util.Arrays;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.leo.Exception.CustomeFieldValidationException;
import com.example.leo.Exception.UserNotFoundException;
import com.example.leo.Exception.UsernameOrIdNotFound;
import com.example.leo.dto.ChangePasswordForm;
import com.example.leo.entity.User;
import com.example.leo.repository.UserRepository;
import com.example.leo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder ; //security encriptar password
	
	@Autowired
	 PasswordEncoder passwordEncoder; //security


	@Override
	@Transactional(readOnly = true)
	public Iterable<User> getAllUsers() {//todos 
		return userRepository.findAll();
	}
	//ENCONTRAMOS POR EMAIL 
	private boolean checkEmailAvailable(User user) throws Exception {
		Optional<User> emailFound = userRepository.findUserByEmail(user.getEmail());
		if (emailFound.isPresent()) {//dentro de su opcional esta presente?
			throw new CustomeFieldValidationException("Email no disponible","email");
			
		}
		return true;//si el username no existe return true
	}

	private boolean checkPasswordValid(User user) throws Exception {
		if (user.getConfirmPassword() == null || user.getConfirmPassword().isEmpty()) {
			throw new CustomeFieldValidationException("Confirm Password es obligatorio","confirmPassword");
		}
		
		if ( !user.getPassword().equals(user.getConfirmPassword())) {
			throw new CustomeFieldValidationException("Password y Confirm Password no son iguales","password");
		}
		return true;
	}

	
	@Override //agrege matches ,por defecto se le asignar role user
	public User createUser(User user) throws Exception {
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		
		if (checkEmailAvailable(user) && checkPasswordValid(user)) {
	
		//	modificar el password para que sea seguro
			   user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); //#11cuando se cree user la contra este encriptada en la bd 
			//   user.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
			   user = userRepository.save(user);
		}
		return user;
	}
//agrege transactional
	@Override
	@Transactional(readOnly = true)
	public User getUserById(Long id) throws UsernameOrIdNotFound {//este es del package exception
		return userRepository.findById(id).orElseThrow(() -> new UsernameOrIdNotFound("El Id del usuario no existe."));
		//nuestro findById q  opcional , si no encuentra que devuelva excepcion
	}

	@Override
	public User updateUser(User fromUser) throws Exception {
		User toUser = getUserById(fromUser.getId());//mapeo es,pasar los valores del user q esta en formulario al user de la bd 
		mapUser(fromUser, toUser);
		return userRepository.save(toUser);
	}
	
	/**
	 * Map everything but the password.
	 * @param from
	 * @param to
	 */
	protected void mapUser(User from,User to) {//este mapeo , de q usuario a cual usuario para un futuro
		to.setUsername(from.getUsername());
		to.setFirstName(from.getFirstName());
		to.setLastName(from.getLastName());
		to.setEmail(from.getEmail());
		to.setCountry(from.getCountry());
		to.setRoles(from.getRoles());
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")//security ,si alguien quiere borrar a un usuario, por  metodo rest ,q tenga el rol de admin 
	public void deleteUser(Long id) throws UsernameOrIdNotFound {
		User user = getUserById(id);
		userRepository.delete(user);
	}

	
	
	@Override
	public User changePassword(ChangePasswordForm form) throws Exception {
		User user = getUserById(form.getId());//primero traer este objeto de la bd 
		
		//agrege matches con passwordEncoder 
	
		if ( !isLoggedUserADMIN() && !passwordEncoder.matches(form.getCurrentPassword(), user.getPassword())) {
			throw new Exception ("Current Password invalido.");
		}
		
		if( passwordEncoder.matches(form.getNewPassword(),user.getPassword())) {
			throw new Exception ("Nuevo debe ser diferente al password actual.");
		}
		
		if( !form.getNewPassword().equals(form.getConfirmPassword())) {
			throw new Exception ("Nuevo Password y Confirm Password no coinciden.");
		}
		//security ,paso el password en encod como en la clase (passgenerator )de la misma manera
		String encodePassword = bCryptPasswordEncoder.encode(form.getNewPassword());
		user.setPassword(encodePassword);//vms asignarle 
		return userRepository.save(user);//el save dice si no existe creelo ,si no actualiza
	}
	
	//security
		public boolean isLoggedUserADMIN(){
			 return loggedUserHasRole("ROLE_ADMIN");
			}
		//le pregunto si ese objeto principal del navegardor sesion ,es instancia de userdetails y le hago casting 
		public boolean loggedUserHasRole(String role) {
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 UserDetails loggedUser = null;
		 Object roles = null; 
		 if (principal instanceof UserDetails) {
		  loggedUser = (UserDetails) principal;//casting
		 
		  roles = loggedUser.getAuthorities().stream()  //paso a autoriades y verifico si hay una autoriada de admin 
		    .filter(x -> role.equals(x.getAuthority() ))       //si hay es ok funciona si no retorna false 
		    .findFirst().orElse(null); // si no hay admin entonces =loggedUser = null; 
		 }
		 return roles != null ?true :false;
		}
//agrege
		@Override
		public User save(User user) {
			// TODO Auto-generated method stub
			return userRepository.save(user);
		}
		

		
//FORGOT PASSWORD		
		@Override
		@Transactional
		public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
	
			User user = userRepository.finByEmail(email);
			if(user != null) {
				user.setResetPasswordToken(token);
				userRepository.save(user);
			}else {
				throw new UserNotFoundException("no se pudo encontrar el email del usuario" +email);
			}
			
		}
		@Override
		@Transactional
		public User get(String resetPasswordToken) {//verfifica encuentra
			
			return userRepository.findByResetPasswordToken(resetPasswordToken);
		}
		@Override
		@Transactional
		public void updatePassword(User user, String newPassword) {//actualiza
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodePassword = passwordEncoder.encode(newPassword);
			
			user.setPassword(encodePassword);//establecemos el nuevo password
			user.setResetPasswordToken(null);
			userRepository.save(user);
		}
		@Override
		public User findByEmail(String email) {
			  return userRepository.finByEmail(email);
		}
	
		

	
		
}

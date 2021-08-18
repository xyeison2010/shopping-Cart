package com.example.leo.controller;
import java.util.List;
import java.util.stream.Collectors;

//ctrl+z para volver atras 
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.leo.Exception.CustomeFieldValidationException;
import com.example.leo.Exception.UsernameOrIdNotFound;
import com.example.leo.dto.ChangePasswordForm;
import com.example.leo.entity.Country;
import com.example.leo.entity.User;
import com.example.leo.repository.RoleRepository;
import com.example.leo.service.CountryService;
import com.example.leo.service.UserService;

@Controller
public class AdminUserController {

	@Autowired
	UserService userService;
	@Autowired
	RoleRepository roleRepository;//esto sera pa consultar nada mas
	@Autowired
	private CountryService countryService;

	//ESTA ES LA LISTA DE USUARIOS Q HAY
	@GetMapping("/admin/users")
	public String getListaUser(Model model) {

		model.addAttribute("userList", userService.getAllUsers());
		return "manager/userView";
	}

	//ACA EL FORMULARIO
		@GetMapping("/admin/user/add")
		public String getAddUser(Model model) {
			model.addAttribute("roles", roleRepository.findAll());
			model.addAttribute("userForm",  new User());//ese user esta en th object, y el url es en action
			// Get countries list
			List<Country> countryList = countryService.findAll();
			model.addAttribute("countries", countryList);
			return "manager/userAdd";
		}
		

		@PostMapping("/admin/user/add")
		public String postAddUser(@Valid Model model , @ModelAttribute("userForm")User user ,BindingResult result) {
			//Get countries list
		    List<Country> countryList = countryService.findAll();
		    model.addAttribute("countries", countryList);
			if(result.hasErrors()) {
				model.addAttribute("userForm",  user);
				model.addAttribute("roles", roleRepository.findAll());
				return "manager/userAdd";
			}else {
				try {				
					userService.createUser(user);
					model.addAttribute("userForm",  new User());//si toda va bien usar redirect
				} catch (CustomeFieldValidationException cfve) {
					result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());	
				
					model.addAttribute("userForm",  user);
					model.addAttribute("roles", roleRepository.findAll());
				return "manager/userAdd";
				}
			
				catch (Exception e) {
					model.addAttribute("formErrorMessage",e.getMessage());
					model.addAttribute("userForm",  user);
					model.addAttribute("roles", roleRepository.findAll());
					return "manager/userAdd";
				}
			
			}
			return "redirect:/admin/users";//mejor usar redirect
			
		}
	
		@GetMapping("/editUser/{id}")
		public String getEditUserForm(Model model, @PathVariable(name ="id")Long id ,User user)throws Exception{
			User userToEdit = userService.getUserById(id);
			// Get countries list
			List<Country> countryList = countryService.findAll();
			model.addAttribute("countries", countryList);	
            model.addAttribute("userForm", userToEdit);		//envez de nuevo usuario ,userForm pq esta en th:object
            model.addAttribute("roles", roleRepository.findAll());
			//edit mode
            model.addAttribute("editMode","true");
			model.addAttribute("passwordForm",new ChangePasswordForm(id)); 
			
			return "manager/userAdd";
		}
		@PostMapping("/editUser")
		public String postEditUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, Model model) {
			if(result.hasErrors()) {
				model.addAttribute("userForm",  user);
				model.addAttribute("roles", roleRepository.findAll());
				model.addAttribute("editMode","true");
	   			model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));//cuando estoy en esta accion se muestra
			}else {
				try {
					userService.updateUser(user);
					model.addAttribute("userForm", new User());
					
				} catch (Exception e) {
					model.addAttribute("formErrorMessage",e.getMessage());
					
					model.addAttribute("userForm",  user);
					model.addAttribute("roles", roleRepository.findAll());
					model.addAttribute("editMode","true");
					model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
				}
			}
			return "redirect:/admin/users";			
		}
		
		@GetMapping("/userForm/cancel")
		public String cancelEditUser(ModelMap model) {
			return "redirect:/admin/users";
		}
	
	//delete y changePassword
		@GetMapping("/deleteUser/{id}")//esta url delete user debe estar igual en el JS,WINdowlocation
		public String deleteUser(Model model, @PathVariable(name="id")Long id) {
			try {
				userService.deleteUser(id);
			} 
			catch (UsernameOrIdNotFound uoin) {
				model.addAttribute("listErrorMessage",uoin.getMessage());
			}
			return "redirect:/admin/users"; //este lo cambie,pa q borre mas de dos return userForm(model);
		}
		//5
		//url de la peticion json en change.js
		@PostMapping("/editUser/changePassword")// esta dentro del modo edit 
		public ResponseEntity<String> postEditUseChangePassword(@Valid @RequestBody ChangePasswordForm form, Errors errors) {
			try {
				//If error, just return a 400 bad request, along with the error message
				if( errors.hasErrors()) {
					String result = errors.getAllErrors()
	                        .stream().map(x -> x.getDefaultMessage())
	                        .collect(Collectors.joining(""));

					throw new Exception(result);
				}
				userService.changePassword(form);
			} catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());//badrequest el viene al js
			}
			return ResponseEntity.ok("Success");
		}
}

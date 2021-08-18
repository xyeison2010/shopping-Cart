package com.example.leo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.leo.Exception.CustomeFieldValidationException;
import com.example.leo.entity.Country;
import com.example.leo.entity.Role;
import com.example.leo.entity.User;
import com.example.leo.global.GlobalData;
import com.example.leo.repository.RoleRepository;
import com.example.leo.service.CountryService;
import com.example.leo.service.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userService;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	private CountryService countryService;

	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();
		return "login";
	}

	@GetMapping("/register")
	public String registerGet(Model model) {

		model.addAttribute("user", new User());
		// Get countries list
		List<Country> countryList = countryService.findAll();
		model.addAttribute("countries", countryList);

		return "/auth/register";
	}

	@PostMapping("/register")
	public String registerPost(@Valid @ModelAttribute("user") User user, BindingResult result, ModelMap model) {
        //por defecto sera rol de user
		user.setRoles(Arrays.asList(roleRepository.findByName("USER")));
		model.addAttribute("user", user);
		List<Country> countryList = countryService.findAll();
		model.addAttribute("countries", countryList);

		if (result.hasErrors()) {
			return "register";
		} else {
			try {
				userService.createUser(user);// si crea bien retorna a index
				model.addAttribute("message", "USUARIO REGISTRADO!!");
			} catch (CustomeFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
			} catch (Exception e) {
				model.addAttribute("formErrorMessage", e.getMessage());
			}
		}

		return login();
	}

}

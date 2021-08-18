package com.example.leo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.leo.Exception.CustomeFieldValidationException;
import com.example.leo.dto.ChangePasswordForm;
import com.example.leo.entity.Country;
import com.example.leo.entity.Order;
import com.example.leo.entity.User;
import com.example.leo.repository.RoleRepository;
import com.example.leo.service.CountryService;
import com.example.leo.service.OrderService;
import com.example.leo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MyAccountController {
  /*  @Autowired
    CustomerService customerService;*/
    @Autowired
    UserService userService;
    
    @Autowired
    CountryService countryService;

    @Autowired
    OrderService orderService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
	RoleRepository roleRepository;

    //--------------- MY ACCOUNT ---------------------//
   @GetMapping("/my-account")
    public String myAccount(Model model) {
        model.addAttribute("classActiveMyAccount", "home active");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        //Get logged in customer
        User user = userService.findByEmail(currentUserName);
        model.addAttribute("user", user);

        //Get countries list
        List<Country> countryList = countryService.findAll();
        model.addAttribute("countries", countryList);
        
        
		
        //model.addAttribute("editMode","true");
		model.addAttribute("passwordForm",new ChangePasswordForm(user.getId())); 
        return "/my-account/my-account";
    }

    @PostMapping("/my-account")
    public String myAccountSave(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) throws Exception {
        model.addAttribute("classActiveMyAccount", "home active");
        //Get countries list
	    List<Country> countryList = countryService.findAll();
	    model.addAttribute("countries", countryList);
        //Get logged in customer
        model.addAttribute("user", user);
        user.setRoles(Arrays.asList(roleRepository.findByName("USER")));

		if(result.hasErrors()) {
			model.addAttribute("user",  user);
   			model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));//cuando estoy en esta accion se muestra
   			return "/my-account/my-account";
		}
	userService.save(user);

		         return "redirect:/my-account?success";
		 
    } 
	@PostMapping("/update/changePassword")// esta dentro del modo edit 
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
    //--------------- END MY ACCOUNT ---------------------//

    @GetMapping("/order-history")
    public String orderHistory(Model model, Principal principal) {
        model.addAttribute("classActiveMyAccount", "home active");

        User user = userService.findByEmail(principal.getName());

        //customer orders
        model.addAttribute("orders", user.getOrders());

        return "/my-account/order-history";
    }
    @GetMapping("/order-details")
    public String orderDetails(@RequestParam("id") Long id, Principal principal, Model model) {
        model.addAttribute("classActiveMyAccount", "home active");

        User user = userService.findByEmail(principal.getName());

        Order order;
        try {
            order = orderService.get(id);

            //invalid order id
            if(order == null){
                model.addAttribute("error", "Order does not exits.");
                return "/my-account/order-details";
            }
            if(!order.getUser().equals(user)){
                model.addAttribute("error", "Order did not belongs to you.");
                return "/my-account/order-details";
            }

            model.addAttribute("order", order);
            return "/my-account/order-details";

        }catch (Exception ex){
            model.addAttribute("error", ex.getMessage());
            return "/my-account/order-details";
        }
    }

/*
    @GetMapping("/change-password")
    public String changePassword(Model model) {
        model.addAttribute("classActiveMyAccount", "home active");

        return "/my-account/change-password";
    }
    @PostMapping("/change-password")
    public String saveChangePassword(@Valid
                                         @ModelAttribute("old_password") String oldPassword
                                        , @ModelAttribute("new_password") String newPassword
                                        , @ModelAttribute("confirm_password") String confirm_password
                                        , Principal principal
                                        , BindingResult result
                                        , Model model) {
        model.addAttribute("classActiveMyAccount", "home active");

        Customer customer = customerService.findByUsername(principal.getName());

        if(bCryptPasswordEncoder.matches(oldPassword, customer.getPassword())){
            //match successfully
            String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
            customer.setPassword(encodedPassword);

            customerService.save(customer);
        }else {
            return "redirect:/change-password?error";
        }

        return "redirect:/change-password?success";
    }*/
}

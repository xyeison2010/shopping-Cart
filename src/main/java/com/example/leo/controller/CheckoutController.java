package com.example.leo.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.leo.Exception.CustomeFieldValidationException;
import com.example.leo.entity.Country;
import com.example.leo.entity.Order;
import com.example.leo.entity.Product;
import com.example.leo.entity.ShoppingCart;
import com.example.leo.entity.User;
import com.example.leo.global.GlobalData;
import com.example.leo.service.CountryService;
import com.example.leo.service.EmailService;
import com.example.leo.service.OrderService;
import com.example.leo.service.ProductService;
import com.example.leo.service.ShoppingCartService;
import com.example.leo.service.UserService;


@Controller
public class CheckoutController {

	@Autowired
	ProductService productService;
	@Autowired
	UserService userService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderService orderService;

    //@Autowired
   // private EmailService emailService;	
    //--------------- CHECKOUT ---------------------//
    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("classActiveCheckout", "home active");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        User user = userService.findByEmail(currentUserName);

        //Get shopping cart
        model.addAttribute("shoppingCart", user.getShoppingCart());

        //Get countries list
        List<Country> countryList = countryService.findAll();
        model.addAttribute("countries", countryList);

        return "/client/checkout";
    }
    @PostMapping("/checkout")
    public String myAccountSave(@Valid
                                @ModelAttribute("shoppingCart") ShoppingCart shoppingCart
            , Principal principal
            , BindingResult result
            , HttpServletRequest request
            , Model model) {
        model.addAttribute("classActiveCheckout", "home active");

        //Get countries list
        List<Country> countryList = countryService.findAll();

        //set countries
        model.addAttribute("countries", countryList);

        //Get full object
        ShoppingCart shoppingCart1 = shoppingCartService.findById(shoppingCart.getId());
        shoppingCart1.setShippingMethod(shoppingCart.getShippingMethod());
        shoppingCart1.setPaymentMethod(shoppingCart.getPaymentMethod());
        shoppingCart1.setDescription(shoppingCart.getDescription());

        //set shopping cart
        model.addAttribute("shoppingCart", shoppingCart1);

        //----------- validation ---------//
        if(result.hasErrors()){
            return "/client/checkout";
        }

        if(shoppingCart1 == null || shoppingCart1.getCartItemList() == null){
            //return "/client/checkout?cartEmpty";
            return "redirect:/checkout?cartEmpty";
        }
        //----------- end validation ---------//

        User user = shoppingCart1.getUser();

        //save customer information
        userService.save(user);

        //Save order
        Order newOrder= orderService.saveOrder(shoppingCart1);

        //make cart empty
        shoppingCartService.emptyShoppingCart(user);

        String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

        //Send email
       // emailService.orderCreation(appUrl, newOrder);


        //redirect to order-history page
        return "redirect:/order-history?success";
    }
    //--------------- END CHECKOUT ---------------------//

	
}

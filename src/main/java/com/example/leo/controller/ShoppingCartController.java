package com.example.leo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ShoppingCartController {

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
    //private EmailService emailService;

    //----------------- CART ---------------//
    @GetMapping("/view-cart")
    public String viewCart(Model model, Principal principal) {
        model.addAttribute("classActiveViewCart", "home active");
        model.addAttribute("cartCount", GlobalData.cart.size());
        User user = userService.findByEmail(principal.getName());//get logged in user
        ShoppingCart shoppingCart = user.getShoppingCart();

        model.addAttribute("shoppingCart", shoppingCart);

        return "/client/view-cart";
    }

	//geTMAPPING
	@PostMapping("/add-to-cart")
	    public String addToCart( @ModelAttribute("id") Long id, @ModelAttribute("quantity") Long quantity
	       , Model model, Principal principal) {
	        model.addAttribute("classActiveViewCart", "home active");
	       
	        //Find product
	        Product product = productService.getProductById(id);

	        //Find customer
	        User user = userService.findByEmail(principal.getName());//get logged in user

	        //Add item to shopping cart
	        shoppingCartService.addItemToCart(product, quantity, user);
	        //GlobalData.cart.add(productService.getProductById(id).get());//solo contabiliza
	        return "redirect:/shop?id="+product.getId()+"&addtocart";
	    }
	
	@GetMapping("/empty-cart")
    public String emptyCart(Model model, Principal principal) {
        model.addAttribute("classActiveViewCart", "home active");

        User user = userService.findByEmail(principal.getName());//get logged in user

        shoppingCartService.emptyShoppingCart(user);

        model.addAttribute("shoppingCart", user.getShoppingCart());

        //Set message
        //model.addAttribute("removeCartMessage", "Cart has been empty successfully.");

        return "redirect:/view-cart?emptyCart";
    }

    @PostMapping(value="/update-cart", params="action=update")
    public String updateCart(
            @ModelAttribute("id") Long id
            , @ModelAttribute("quantity") String quantity
            , Model model
            , Principal principal) {
        model.addAttribute("classActiveViewCart", "home active");

        //Check if valid quantity then use it otherwise default value is 1
        Long qty=1L;
        try{
            qty = Long.parseLong(quantity);
        }catch (NumberFormatException ex) {
            qty = 1L;
        }

        //Find product
        Product product = productService.getProductById(id);

        User user = userService.findByEmail(principal.getName());//get logged in user

        shoppingCartService.updateItemFromCart(product, qty, user);

        model.addAttribute("shoppingCart", user.getShoppingCart());

        return "redirect:/view-cart?updateCart";
    }

    @PostMapping(value="/update-cart", params="action=delete")
    public String removeCart(
            @ModelAttribute("id") Long id
            , Model model
            , Principal principal) {
        model.addAttribute("classActiveViewCart", "home active");
       
        //Find product
        Product product = productService.getProductById(id);
        //GlobalData.cartItem.remove(productService.getProductById(id).get());//descuenta contabilizacion
        User user = userService.findByEmail(principal.getName());//get logged in user

        shoppingCartService.removeItemFromCart(product, user);
  
        model.addAttribute("shoppingCart", user.getShoppingCart());

        return "redirect:/view-cart?removeCart";
    }
    //----------------- END CART ---------------//



}

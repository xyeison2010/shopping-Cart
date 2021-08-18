package com.example.leo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import com.example.leo.global.GlobalData;
import com.example.leo.service.CategoryService;
import com.example.leo.service.ProductService;
import com.example.leo.service.ShoppingCartService;
import com.example.leo.service.UserService;

@Controller
public class HomeController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@Autowired
	UserService userService;
	@Autowired
	ShoppingCartService shoppingCartService;

	@GetMapping({ "/", "/home" })
	public String home(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());

		return "index";
	}

	@GetMapping("/shop")
	public String shop(Model model) {

		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProduct());
		model.addAttribute("cartCount", GlobalData.cart.size());// carrito contabiliza

		return "shop";
	}

	@GetMapping("/shop/category/{id}")
	public String shopByCategory(Model model, @PathVariable int id) {
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("cartCount", GlobalData.cart.size());               
		model.addAttribute("products", productService.getAllProductsByCategoryId(id));
		return "shop";
	}

	// view es ver
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(Model model, @PathVariable long id) {
																	
		model.addAttribute("product", productService.getProductById(id)); 
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "viewProduct";
	}

	
	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable long id) {

		GlobalData.cart.add(productService.getProductById(id));

		return "redirect:/shop";
	}

}

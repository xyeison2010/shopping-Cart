package com.example.leo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.example.leo.dto.ProductDTO;
import com.example.leo.entity.Category;
import com.example.leo.entity.Product;
import com.example.leo.entity.Role;
import com.example.leo.entity.User;
import com.example.leo.entity.ShoppingCart;
import com.example.leo.repository.RoleRepository;
import com.example.leo.service.CategoryService;
import com.example.leo.service.ProductService;
import com.example.leo.service.UserService;




@Controller
public class AdminController {
	
//importante el static ,sirve q cuando se suba imagenes nuevas se guarden aca 
	public static String uploadDir= System.getProperty("user.dir")+"/src/main/resources/static/productImages";
	

@Autowired
CategoryService categoryService;
@Autowired
ProductService productService;

@Autowired
UserService userService;

@Autowired
private MessageSource messageSource;

@GetMapping("/admin")
public String adminHome() {
	return "manager/adminHome";
}
///

////
@GetMapping("/admin/categories")
public String getCat(Model model) {
	model.addAttribute("categories", categoryService.getAllCategory());//este categories es del th:object
	return "manager/categories";
}
//agregar categorias
@GetMapping("/admin/categories/add")
public String getCatAdd(Model model) {
	model.addAttribute("category", new Category());
	return "manager/categoriesAdd";
}

@PostMapping("/admin/categories/add")
public String postCatAdd(@ModelAttribute("category") Category category) {
	categoryService.addCategory(category);
	return "redirect:/admin/categories";//aki

}
//delete
@GetMapping("admin/categories/delete/{id}")
public String deleteCat(@PathVariable(name="id")int id) {
	categoryService.removeCategoryById(id);
	
	return "redirect:/admin/categories";
}
//update
@GetMapping("admin/categories/update/{id}")
public String updateCat(@PathVariable(name="id")int id, Model model) {
	Optional<Category> category = categoryService.getCategoryById(id);
	if(category.isPresent()) {
		model.addAttribute("category", category.get());
		return "manager/categoriesAdd";
	}else	
	return "404";//<!--  hasta aki la parde editar y eliminar con las categorieas>}
  }


//PRODUCT SECTION
@GetMapping("/admin/products")
public String products (Model model ) {
	model.addAttribute("products", productService.getAllProduct());
	return "manager/products";
}

@GetMapping("/admin/products/add")
public String productAddGet (Model model ) {
	model.addAttribute("productDTO", new ProductDTO());//el q esta en "" debe estar igual q esta en el object
	model.addAttribute("categories", categoryService.getAllCategory());//select del html trae todo catg..
	return "manager/productsAdd";
}
//parte q carga imagen IMG, el post e en productsAdd.html
@PostMapping("/admin/products/add")
public String productAddPost (@ModelAttribute("productDTO")ProductDTO productDTO, @RequestParam("productImage")MultipartFile file,
@RequestParam("imgName")String imgName)throws IOException {
	//multipartfile es función de carga de archivos en forma de formulario.
	
	Product product = new Product();
	product.setId(productDTO.getId());
	product.setName(productDTO.getName());
	product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());//get de optional
	product.setCode(productDTO.getCode());//codigo
	product.setPrice(productDTO.getPrice());
	//existencia
	product.setExistencia(productDTO.getExistencia());
	product.setWeight(productDTO.getWeight());
	product.setDescription(productDTO.getDescription());
	String imageUUID;//empty = vacio
	if(!file.isEmpty()) {//si la fila no esta vacia 
		imageUUID = file.getOriginalFilename(); //en el inicio se declara
		Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
		Files.write(fileNameAndPath,file.getBytes());
	}else {//si esta vacia 
		imageUUID = imgName;
	}
	product.setImageName(imageUUID);
	productService.addProduct(product);
	
	return "redirect:/admin/products";
}
//DELETE , UPDATE
@GetMapping("/admin/product/delete/{id}")
public String deleteProduct(@PathVariable long id) {
	productService.removeProductById(id);
	return "redirect:/admin/products";
	
}
@GetMapping("/admin/product/update/{id}")
public String updateProductGet(@PathVariable long id,Model model) {
	Product product  = productService.getProductById(id);
	ProductDTO productDTO = new ProductDTO();
	productDTO.setId(product.getId());
	productDTO.setName(product.getName());
	productDTO.setCategoryId(product.getCategory().getId());
	productDTO.setCode(product.getCode());
	productDTO.setPrice(product.getPrice());
	//existencia
	productDTO.setExistencia(product.getExistencia());
	productDTO.setWeight(product.getWeight());
	productDTO.setDescription(product.getDescription());
	productDTO.setImageName(product.getImageName());
	
	model.addAttribute("categories", categoryService.getAllCategory());
	model.addAttribute("productDTO", productDTO);
	return "manager/productsAdd";
}
/*
@Autowired
RoleRepository roleRepository;
@Autowired
VentaService ventaService;
@GetMapping(value = "/ver/{id}")
public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash, Locale locale
		,Principal principal) {

	//User user = userService.fetchByIdWithVentas(id);
	User userValidar= userService.findByEmail(principal.getName());
	
	Object user;
	if (user == null) {
		flash.addFlashAttribute("error", messageSource.getMessage("text.user.flash.db.error", null, locale));
		return "redirect:/shop";
	}

	if ( id != userValidar.getId() ) {
		flash.addFlashAttribute("error", messageSource.getMessage("text.user.flash.db.error", null, locale));
		return "redirect:/shop";
	}


	model.put("user", user);
	model.put("titulo", messageSource.getMessage("text.user.detalle.titulo", null, locale).concat(": ").concat(user.getFirstName()));
	return "ver";

}*/
}

package com.example.leo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.leo.entity.Product;
import com.example.leo.entity.ShoppingCart;
import com.example.leo.repository.ProductRepository;
import com.example.leo.repository.ShoppingCartRepository;

@Service
public class ProductService {
	
@Autowired
ProductRepository productRepository;


public List<Product> getAllProduct(){
	return productRepository.findAll();
}
public void addProduct(Product product) {
	productRepository.save(product);
}
public void removeProductById(long id) {
	productRepository.deleteById(id);
}

public List<Product> getAllProductsByCategoryId(int id ){
	return productRepository.findAllByCategory_Id(id);
}
//agrege encontrar por id
@Transactional(readOnly=true) 
public Product getProductById(long id) {
    return productRepository.findById(id).get();
}

@Transactional(readOnly = true)
public List<Product> findByName(String term) {//buscar por atajo
	
	return productRepository.findByNameLikeIgnoreCase("%"+term+"%");
}

//
}

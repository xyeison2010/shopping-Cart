package com.example.leo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leo.entity.Category;
import com.example.leo.repository.CategoryRepositoty;

@Service
public class CategoryService {
	
@Autowired
CategoryRepositoty categoryRepository;

public List<Category> getAllCategory(){//guardar todas la categorias con list, yo usaba iterable pero esigual por ahora
	
	return categoryRepository.findAll();
}

public void addCategory(Category category) {//agregar categoria y guardamelo
	categoryRepository.save(category);//guardar
}

public void removeCategoryById (int id ) {
	categoryRepository.deleteById(id);
}
//esto fue mas para update
public Optional<Category> getCategoryById(int id ){
return	categoryRepository.findById(id);
}

}

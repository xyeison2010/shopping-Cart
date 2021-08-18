package com.example.leo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.leo.entity.Category;


@Repository
public interface CategoryRepositoty extends JpaRepository<Category ,Integer > {

}

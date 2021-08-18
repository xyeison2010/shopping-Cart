package com.example.leo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.leo.entity.Country;
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByName(String name);
}
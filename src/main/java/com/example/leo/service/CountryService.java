package com.example.leo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.leo.entity.Country;
import com.example.leo.repository.CountryRepository;

import java.util.List;

@Service
@Transactional
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<Country> findAll() {
        return (List<Country>) countryRepository.findAll();
    }

    public Country findByName(String name) {
        return countryRepository.findByName(name);
    }

    public void save(Country product) {
        countryRepository.save(product);
    }

    public Country get(long  id) {
        return countryRepository.findById(id).get();
    }

    public void delete(long id) {
        countryRepository.deleteById(id);
    }
}
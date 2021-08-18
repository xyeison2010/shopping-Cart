package com.example.leo.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.leo.entity.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
package com.example.leo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.leo.entity.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
/*	@Query("select v from Venta v join fetch v.user u join fetch v.items l join fetch l.product where v.id=?1")
	public ShoppingCart fetchVentaByIdWithUserWhithCartItemWithProduct(Long id);
*/

}

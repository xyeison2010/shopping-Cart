package com.example.leo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.leo.entity.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
Optional<User> findUserByEmail (String email ); //por el momento solo valida con el email

/*@Query("select u from User u left join fetch u.ventas v where u.id=?1")
public User fetchByIdWithVentas(Long id);*/

//estos solo servira para forgotpassword 
public User findByResetPasswordToken(String token);
@Query("select u from User u  where u.email = ?1")
public User finByEmail(String email); //esto tm me servira para validar el usuario solo tenga acceso a su id 
}

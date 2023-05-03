package com.gildas.gestionstock.auth.repository;


import com.gildas.gestionstock.auth.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByTelephone(String telephone);

    User findByResetPasswordToken(String token);
    Page<User> findByNomLikeAndGenreLikeAndRole(String nom, String genre, String role, Pageable pageable);


}

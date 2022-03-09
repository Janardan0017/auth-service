package com.stockmarket.authservice.repository;

import com.stockmarket.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByEmail(String email);

    @Query("select count(*) > 0 from User u where u.email = :email")
    boolean emailExist(@Param("email") String email);
}

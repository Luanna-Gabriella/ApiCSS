package com.css.repository;

import com.css.entity.Login;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Integer>{
    Optional<Login> findByEmail(String email);
}

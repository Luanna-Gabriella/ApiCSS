package com.css.repository;

import com.css.entity.Funcionario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer>{
    @Query("SELECT f FROM Funcionario f WHERE f.fk_id_tipop = 2")
    List<Funcionario> findAllFuncionarios();
}

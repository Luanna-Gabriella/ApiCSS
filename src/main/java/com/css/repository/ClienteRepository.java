package com.css.repository;

import com.css.entity.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
    @Query("SELECT c FROM Pessoa c WHERE c.fk_id_tipop = 3")
    List<Cliente> findAllClientes();
}

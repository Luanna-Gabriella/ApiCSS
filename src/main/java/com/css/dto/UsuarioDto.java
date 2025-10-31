package com.css.dto;

import com.css.entity.Login;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Data;

@Data
public class UsuarioDto {
    private int id;
    
    private Login login;
    private String nome;
    private int fk_id_tipop;
    private int ativo;
}

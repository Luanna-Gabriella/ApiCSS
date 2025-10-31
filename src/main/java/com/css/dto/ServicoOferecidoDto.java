package com.css.dto;

import com.css.entity.Login;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

@Data
public class ServicoOferecidoDto {
    private int id;
    private String nome;
    private boolean ativo;
}

package com.css.dto;

import com.css.entity.Login;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class FuncionarioDto {
    private int id;
    
    private Login login;
    
    private String nome;
    private String cpf;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate data_nasc;
    
    private String telefone;
    private int fk_id_tipop;
    private int ativo;
    
    private List<ServicoOferecidoDto> servicosOferecidos;
}

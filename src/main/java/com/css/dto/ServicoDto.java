package com.css.dto;

import lombok.Data;

@Data
public class ServicoDto {

    private int id;
    
    private String nome;
    private float preco;
    private TempoDto tempo;
    private int ativo;
}

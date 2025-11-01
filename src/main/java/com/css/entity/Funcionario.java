package com.css.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pessoa") 
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Funcionario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa") 
    private int id;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_id_login") 
    private Login login;
    
    private String nome;
    private String cpf;
    private LocalDate data_nasc;
    private String telefone;
    private int fk_id_tipop;
    private int ativo;
        
    @ManyToMany
    @JoinTable(
        name = "FuncionarioServicoOferecido",
        joinColumns = @JoinColumn(name = "fk_id_funcionario"),
        inverseJoinColumns = @JoinColumn(name = "fk_id_servicoo")
    )
    private List<ServicoOferecido> servicosOferecidos = new ArrayList<>();
    
    private int tentativas;
    
}

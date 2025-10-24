package com.css.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tempo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tempo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tempo") 
    private Integer id;

    private String duracao;
    
    @Override
    public String toString() {
        return  id + " " + duracao;
    }

}

package com.css.mapper;

import com.css.dto.FuncionarioDto;
import com.css.entity.Funcionario;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FuncionarioMapper {
     private final ModelMapper modelMapper;
    
    public FuncionarioDto toFuncionarioDto(Funcionario funcionario){
        return modelMapper.map(funcionario, FuncionarioDto.class);
    }
    
    public Funcionario toFuncionario(FuncionarioDto funcionarioDto){
        return modelMapper.map(funcionarioDto, Funcionario.class);
    }
}

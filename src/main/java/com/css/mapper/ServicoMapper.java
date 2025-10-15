package com.css.mapper;

import com.css.dto.ServicoDto;
import com.css.entity.Servico;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServicoMapper {
     private final ModelMapper modelMapper;
    
    public ServicoDto toServicoDto(Servico servico){
        return modelMapper.map(servico, ServicoDto.class);
    }
    
    public Servico toServico(ServicoDto servicoDto){
        return modelMapper.map(servicoDto, Servico.class);
    }
}

package com.css.mapper;

import com.css.dto.ServicoDto;
import com.css.dto.ServicoOferecidoDto;
import com.css.entity.Servico;
import com.css.entity.ServicoOferecido;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServicoOferecidoMapper {
     private final ModelMapper modelMapper;
    
    public ServicoOferecidoDto toServicoOferecidoDto(ServicoOferecido servicoo){
        return modelMapper.map(servicoo, ServicoOferecidoDto.class);
    }
    
    public ServicoOferecido toServicoOferecido(ServicoOferecidoDto servicooDto){
        return modelMapper.map(servicooDto, ServicoOferecido.class);
    }
}

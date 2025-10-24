package com.css.mapper;

import com.css.dto.TempoDto;
import com.css.entity.Tempo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TempoMapper {
     private final ModelMapper modelMapper;
    
     
    public TempoDto toTempoDto(Tempo tempo){
        return modelMapper.map(tempo, TempoDto.class);
    }
    
    public Tempo toTempo(TempoDto tempoDto){
        return modelMapper.map(tempoDto, Tempo.class);
    }
}

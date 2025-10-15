package com.css.mapper;

import com.css.dto.ClienteDto;
import com.css.entity.Cliente;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteMapper {
     private final ModelMapper modelMapper;
    
    public ClienteDto toClienteDto(Cliente cliente){
        return modelMapper.map(cliente, ClienteDto.class);
    }
    
    public Cliente toCliente(ClienteDto clienteDto){
        return modelMapper.map(clienteDto, Cliente.class);
    }
}

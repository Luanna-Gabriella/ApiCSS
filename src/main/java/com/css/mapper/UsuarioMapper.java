package com.css.mapper;

import com.css.dto.UsuarioDto;
import com.css.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioMapper {
     private final ModelMapper modelMapper;
    
     
    public UsuarioDto toUsuarioDto(Usuario usuario){
        return modelMapper.map(usuario, UsuarioDto.class);
    }
    
    public Usuario toUsuario(UsuarioDto usuarioDto){
        return modelMapper.map(usuarioDto, Usuario.class);
    }
}

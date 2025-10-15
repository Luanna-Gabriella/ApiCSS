package com.css.service;

import com.css.dto.FuncionarioDto;
import com.css.entity.Funcionario;
import com.css.mapper.FuncionarioMapper;
import com.css.repository.FuncionarioRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;
   

     public List<FuncionarioDto> listarTodos() {
        return funcionarioRepository.findAll()
        .stream()
        .map(funcionarioMapper::toFuncionarioDto)
        .toList();
        
    }

    public Optional<FuncionarioDto> buscarPorId(int id) {
        return funcionarioRepository.findById(id)
        .map(funcionarioMapper::toFuncionarioDto);
    }
    
    public FuncionarioDto salvar(FuncionarioDto funcionarioDto) {


        Funcionario funcionario = funcionarioMapper.toFuncionario(funcionarioDto); 
        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);

        return funcionarioMapper.toFuncionarioDto(funcionarioSalvo);
    }
    
    public FuncionarioDto atualizar(int id, FuncionarioDto funcionarioAtualizadoDto) {
        return funcionarioRepository.findById(id)
            .map(funcionario -> {
                // Atualiza os dados simples
                funcionario.setNome(funcionarioAtualizadoDto.getNome());
                funcionario.setCpf(funcionarioAtualizadoDto.getCpf());
                funcionario.setData_nasc(funcionarioAtualizadoDto.getData_nasc());
                funcionario.setTelefone(funcionarioAtualizadoDto.getTelefone());
                if (funcionarioAtualizadoDto.getLogin() != null) {
                    funcionario.setLogin(funcionarioAtualizadoDto.getLogin());
                }
                
                Funcionario atualizado = funcionarioRepository.save(funcionario);
                return funcionarioMapper.toFuncionarioDto(atualizado);
            })
            .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));
    }

    
    
    
    public void excluir(int id) {
        funcionarioRepository.deleteById(id);
    }
}

package com.css.service;

import com.css.dto.FuncionarioDto;
import com.css.entity.Funcionario;
import com.css.mapper.FuncionarioMapper;
import com.css.repository.FuncionarioRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FuncionarioService {
    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;
    private final PasswordEncoder passwordEncoder; 
   

     public List<FuncionarioDto> listarTodos() {
        return funcionarioRepository.findAllFuncionarios()
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
        
        // Remove pontos e traços do CPF
        if (funcionario.getCpf() != null) {
            funcionario.setCpf(funcionario.getCpf().replaceAll("[.-]", ""));
        }
        
        if (funcionario.getTelefone() != null) {
            funcionario.setTelefone(funcionario.getTelefone().replaceAll("[() -]", ""));
        }
        
        if (funcionario.getLogin() == null || funcionario.getLogin().getSenha() == null) {
            throw new IllegalArgumentException("Senha não pode ser nula");
        }
        
        String senhaCriptografada = passwordEncoder.encode(funcionario.getLogin().getSenha());
        funcionario.getLogin().setSenha(senhaCriptografada);
        
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

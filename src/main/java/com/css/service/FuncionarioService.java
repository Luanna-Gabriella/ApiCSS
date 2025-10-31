package com.css.service;

import com.css.dto.FuncionarioDto;
import com.css.dto.ServicoOferecidoDto;
import com.css.entity.Funcionario;
import com.css.entity.ServicoOferecido;
import com.css.mapper.FuncionarioMapper;
import com.css.repository.FuncionarioRepository;
import com.css.repository.ServicoOferecidoRepository;
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
    private final ServicoOferecidoRepository servicoOferecidoRepository;
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

        if (funcionarioDto.getServicosOferecidos() != null && !funcionarioDto.getServicosOferecidos().isEmpty()) {
            List<ServicoOferecido> servicos = funcionarioDto.getServicosOferecidos().stream()
                    .map(servicoODto -> servicoOferecidoRepository.findById(servicoODto.getId())
                            .orElseThrow(() -> new RuntimeException("Serviço não encontrado: " + servicoODto.getId())))
                    .collect(Collectors.toList());

            funcionarioSalvo.setServicosOferecidos(servicos);
            funcionarioSalvo = funcionarioRepository.save(funcionarioSalvo); // salva o vínculo ManyToMany
        }

        
        return funcionarioMapper.toFuncionarioDto(funcionarioSalvo);
    }

    public FuncionarioDto atualizar(int id, FuncionarioDto funcionarioAtualizadoDto) {
        return funcionarioRepository.findById(id)
            .map(funcionario -> {
                // Atualiza dados simples
                funcionario.setNome(funcionarioAtualizadoDto.getNome());
                funcionario.setCpf(funcionarioAtualizadoDto.getCpf());
                funcionario.setData_nasc(funcionarioAtualizadoDto.getData_nasc());
                funcionario.setTelefone(funcionarioAtualizadoDto.getTelefone());
                funcionario.setFk_id_tipop(funcionarioAtualizadoDto.getFk_id_tipop());
                funcionario.setAtivo(funcionarioAtualizadoDto.getAtivo());

                // Atualiza apenas o email do login, sem alterar a senha
                if (funcionario.getLogin() != null && funcionarioAtualizadoDto.getLogin() != null) {
                    funcionario.getLogin().setEmail(funcionarioAtualizadoDto.getLogin().getEmail());
                }
                // Remove pontos e traços do CPF
                if (funcionario.getCpf() != null) {
                    funcionario.setCpf(funcionario.getCpf().replaceAll("[.-]", ""));
                }

                if (funcionario.getTelefone() != null) {
                    funcionario.setTelefone(funcionario.getTelefone().replaceAll("[() -]", ""));
                }

                // Atualiza serviços oferecidos
                if (funcionarioAtualizadoDto.getServicosOferecidos() != null) {
                    List<ServicoOferecido> servicos = funcionarioAtualizadoDto.getServicosOferecidos().stream()
                        .map(servicoDto -> servicoOferecidoRepository.findById(servicoDto.getId())
                            .orElseThrow(() -> new RuntimeException("Serviço não encontrado: " + servicoDto.getId())))
                        .collect(Collectors.toList());
                    funcionario.setServicosOferecidos(servicos);
                }

                // Salva as alterações
                Funcionario atualizado = funcionarioRepository.save(funcionario);
                return funcionarioMapper.toFuncionarioDto(atualizado);
            })
            .orElseThrow(() -> new RuntimeException("Funcionario não encontrado"));
    }

    
    
    
    public void excluir(int id) {
        funcionarioRepository.deleteById(id);
    }
}

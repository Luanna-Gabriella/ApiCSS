package com.css.service;

import com.css.dto.ClienteDto;
import com.css.entity.Cliente;
import com.css.mapper.ClienteMapper;
import com.css.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final PasswordEncoder passwordEncoder; 
   

     public List<ClienteDto> listarTodos() {
        return clienteRepository.findAllClientes()
        .stream()
        .map(clienteMapper::toClienteDto)
        .toList();
        
    }

    public Optional<ClienteDto> buscarPorId(int id) {
        return clienteRepository.findById(id)
        .map(clienteMapper::toClienteDto);
    }
    
    public ClienteDto salvar(ClienteDto clienteDto) {


        Cliente cliente = clienteMapper.toCliente(clienteDto);
        
        // Remove pontos e traços do CPF
        if (cliente.getCpf() != null) {
            cliente.setCpf(cliente.getCpf().replaceAll("[.-]", ""));
        }
        
        if (cliente.getTelefone() != null) {
            cliente.setTelefone(cliente.getTelefone().replaceAll("[() -]", ""));
        }
        
        if (cliente.getLogin() == null || cliente.getLogin().getSenha() == null) {
            throw new IllegalArgumentException("Senha não pode ser nula");
        }
        
        String senhaCriptografada = passwordEncoder.encode(cliente.getLogin().getSenha());
        cliente.getLogin().setSenha(senhaCriptografada);
    
        Cliente clienteSalvo = clienteRepository.save(cliente);

        return clienteMapper.toClienteDto(clienteSalvo);
    }
    
    public ClienteDto atualizar(int id, ClienteDto clienteAtualizadoDto) {
        return clienteRepository.findById(id)
            .map(cliente -> {
                // Atualiza os dados do cliente
                cliente.setNome(clienteAtualizadoDto.getNome());
                cliente.setCpf(clienteAtualizadoDto.getCpf());
                cliente.setData_nasc(clienteAtualizadoDto.getData_nasc());
                cliente.setTelefone(clienteAtualizadoDto.getTelefone());
                cliente.setAtivo(clienteAtualizadoDto.getAtivo());
                
                // Atualiza apenas o email do login, sem alterar a senha
                if (cliente.getLogin() != null && clienteAtualizadoDto.getLogin() != null) {
                    cliente.getLogin().setEmail(clienteAtualizadoDto.getLogin().getEmail());
                }
                // Remove pontos e traços do CPF
                if (cliente.getCpf() != null) {
                    cliente.setCpf(cliente.getCpf().replaceAll("[.-]", ""));
                }

                if (cliente.getTelefone() != null) {
                    cliente.setTelefone(cliente.getTelefone().replaceAll("[() -]", ""));
                }
                Cliente atualizado = clienteRepository.save(cliente);
                return clienteMapper.toClienteDto(atualizado);
            })
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }
    
    
    
    public void excluir(int id) {
        clienteRepository.deleteById(id);
    }
}

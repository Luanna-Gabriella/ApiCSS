package com.css.service;

import com.css.dto.ClienteDto;
import com.css.entity.Cliente;
import com.css.mapper.ClienteMapper;
import com.css.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
   

     public List<ClienteDto> listarTodos() {
        return clienteRepository.findAll()
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
        Cliente clienteSalvo = clienteRepository.save(cliente);

        return clienteMapper.toClienteDto(clienteSalvo);
    }
    
    public ClienteDto atualizar(int id, ClienteDto clienteAtualizadoDto) {
        return clienteRepository.findById(id)
            .map(cliente -> {
                // Atualiza os dados simples
                cliente.setNome(clienteAtualizadoDto.getNome());
                cliente.setCpf(clienteAtualizadoDto.getCpf());
                cliente.setData_nasc(clienteAtualizadoDto.getData_nasc());
                cliente.setTelefone(clienteAtualizadoDto.getTelefone());
                if (clienteAtualizadoDto.getLogin() != null) {
                    cliente.setLogin(clienteAtualizadoDto.getLogin());
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

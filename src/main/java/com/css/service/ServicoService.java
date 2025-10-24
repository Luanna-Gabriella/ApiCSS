package com.css.service;

import com.css.dto.ServicoDto;
import com.css.entity.Servico;
import com.css.mapper.ServicoMapper;
import com.css.repository.ServicoRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicoService {
    private final ServicoRepository servicoRepository;
    private final ServicoMapper servicoMapper;
   

    public List<ServicoDto> listarTodos() {
        return servicoRepository.findAll()
        .stream()
        .map(servicoMapper::toServicoDto)
        .toList();
        
    }

    public Optional<ServicoDto> buscarPorId(int id) {
        return servicoRepository.findById(id)
        .map(servicoMapper::toServicoDto);
    }
    
    public ServicoDto salvar(ServicoDto servicoDto) {


        Servico servico = servicoMapper.toServico(servicoDto); 
        Servico servicoSalvo = servicoRepository.save(servico);

        return servicoMapper.toServicoDto(servicoSalvo);
    }
    
    public ServicoDto atualizar(int id, ServicoDto servicoAtualizadoDto) {
        return servicoRepository.findById(id)
            .map(servico -> {
                // Atualiza os dados simples
                servico.setNome(servicoAtualizadoDto.getNome());
                servico.setPreco(servicoAtualizadoDto.getPreco());
                servico.setDuracao(servicoAtualizadoDto.getDuracao()); 
                servico.setAtivo(servicoAtualizadoDto.getAtivo()); 
                servico.setDescricao(servicoAtualizadoDto.getDescricao()); 
                Servico atualizado = servicoRepository.save(servico);
                return servicoMapper.toServicoDto(atualizado);
            })
            .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
    }
    
    public void excluir(int id) {
        servicoRepository.deleteById(id);
    }
}

package com.css.controller;

import com.css.dto.ServicoDto;
import com.css.service.ServicoService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ServicoController {
        @Autowired
    private ServicoService servicoService;

    
    @GetMapping
    public List<ServicoDto> listarTodos() {
        return servicoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoDto> buscarPorId(@PathVariable int id) {
        Optional<ServicoDto> servicoDto = servicoService.buscarPorId(id);
        return servicoDto.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<ServicoDto> criar(@RequestBody ServicoDto servicoDto) {
        ServicoDto servicoSalvo = servicoService.salvar(servicoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoDto> atualizar(@PathVariable int id, @RequestBody ServicoDto servicoAtualizado) {
        ServicoDto servicoDto = servicoService.atualizar(id, servicoAtualizado);
        return ResponseEntity.ok(servicoDto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) {
        servicoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

package com.css.controller;

import com.css.dto.FuncionarioDto;
import com.css.service.FuncionarioService;
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
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {
    
    @Autowired
    private FuncionarioService funcionarioService;

    
    @GetMapping
    public List<FuncionarioDto> listarTodos() {
        return funcionarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDto> buscarPorId(@PathVariable int id) {
        Optional<FuncionarioDto> funcionarioDto = funcionarioService.buscarPorId(id);
        return funcionarioDto.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<FuncionarioDto> criar(@RequestBody FuncionarioDto funcionarioDto) {
        FuncionarioDto funcionarioSalvo = funcionarioService.salvar(funcionarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioSalvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDto> atualizar(@PathVariable int id, @RequestBody FuncionarioDto funcionarioAtualizado) {
        FuncionarioDto funcionarioDto = funcionarioService.atualizar(id, funcionarioAtualizado);
        return ResponseEntity.ok(funcionarioDto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) {
        funcionarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}

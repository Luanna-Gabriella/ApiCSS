package com.css.controller;

import com.css.dto.UsuarioDto;
import com.css.entity.Usuario;
import com.css.repository.UsuarioRepository;
import com.css.security.JwtService;
import com.css.service.LoginUserDetailsService;
import com.css.service.UsuarioService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final LoginUserDetailsService loginUserDetailsService;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository; 
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            // Busca o usuário pelo e-mail
            Usuario usuario = usuarioRepository.findByLogin_Email(request.getEmail())
                    .orElse(null);

            // Se o usuário não existir
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
            }

            // Se o usuário estiver desativado
            if (usuario.getAtivo() == 0) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Conta desativada. Entre em contato com o administrador.");
            }

            // Tenta autenticar o usuário
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
            );

            // Se chegou até aqui, login bem-sucedido → zera tentativas
            usuario.setTentativas(0);
            usuarioRepository.save(usuario);

            // Gera o token JWT
            String token = jwtService.generateToken(request.getEmail());
            return ResponseEntity.ok(token);

        } catch (BadCredentialsException e) {
            // Busca novamente o usuário para atualizar tentativas
            Usuario usuario = usuarioRepository.findByLogin_Email(request.getEmail()).orElse(null);
            if (usuario != null) {
                int tentativas = usuario.getTentativas() + 1;

                if (tentativas >= 5) {
                    // Desativa o usuário após 5 tentativas erradas
                    usuario.setAtivo(0);
                    usuario.setTentativas(0); // reseta contador
                    usuarioRepository.save(usuario);

                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body("Conta bloqueada após 5 tentativas inválidas. Contate o administrador.");
                } else {
                    usuario.setTentativas(tentativas);
                    usuarioRepository.save(usuario);
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body("Credenciais inválidas. Tentativa " + tentativas + " de 5.");
                }
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar login: " + e.getMessage());
        }
    }
    
    @GetMapping("/forgot/{email}")
    public ResponseEntity<String> verificarEmail(@PathVariable String email) {
        try {
            loginUserDetailsService.loadUserByUsername(email); // tenta carregar o usuário
            return ResponseEntity.ok("E-mail encontrado");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body("E-mail não encontrado");
        }
    }
    
    @GetMapping("/user/{email}")
    public ResponseEntity<UsuarioDto> informacoesUsuarios(@PathVariable String email) {
        Optional<UsuarioDto> usuarioDto = usuarioService.buscarPorEmail(email);
        return usuarioDto.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @GetMapping("/reset/{email}")
    public ResponseEntity<String> redefinirSenha(@PathVariable String email) {
        try {
            usuarioService.enviarNovaSenha(email);
            return ResponseEntity.ok("Nova senha enviada para o e-mail informado.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao enviar nova senha: " + e.getMessage());
        }
    }
    
    
    
}

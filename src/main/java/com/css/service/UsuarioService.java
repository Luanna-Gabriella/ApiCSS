package com.css.service;

import com.css.dto.ClienteDto;
import com.css.dto.UsuarioDto;
import com.css.entity.Cliente;
import com.css.mapper.ClienteMapper;
import com.css.mapper.UsuarioMapper;
import com.css.repository.ClienteRepository;
import com.css.repository.UsuarioRepository;
import com.css.util.PasswordGenerator;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UsuarioMapper usuarioMapper;
   

    public Optional<UsuarioDto> buscarPorEmail(String email) {
        return usuarioRepository.findByLogin_Email(email)
        .map(usuarioMapper::toUsuarioDto);
    }

    public void enviarNovaSenha(String email) {
        // 1️⃣ Busca o usuário no banco pelo e-mail
        var usuario = usuarioRepository.findByLogin_Email(email)
                .orElseThrow(() -> new RuntimeException("E-mail não encontrado"));

        // 2️⃣ Gera nova senha (6 dígitos mistos)
        String novaSenha = PasswordGenerator.generatePassword();

        // 3️⃣ Envia por e-mail
        emailService.enviarEmail(
                email,
                "Nova senha de acesso",
                "Olá, sua nova senha é: " + novaSenha + "\n\nPor segurança, altere-a após o login."
        );
        
        //Criptografa e salva no banco
        String senhaCriptografada = passwordEncoder.encode(novaSenha);
        usuario.getLogin().setSenha(senhaCriptografada); // ← se a senha está dentro do objeto Login
        usuarioRepository.save(usuario);
    }
    
}

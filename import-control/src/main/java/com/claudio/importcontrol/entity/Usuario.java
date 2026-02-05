package com.claudio.importcontrol.entity;
import com.claudio.importcontrol.enums.PerfisAcesso;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    
    @Column(unique = true)
    private String email;
    
    private String senha;
    
    @Enumerated(EnumType.STRING)
    private PerfisAcesso role;

    public Usuario() {}

<<<<<<< HEAD
    public Long getId() { return id; }

    public void setRole(UserRole role){this.role = role;}

    public UserRole getRole() { return role; }

    public void setId(Long id) { this.id = id; }

=======
    public Long getIdUsuario() { return id; }
    public void setIdUsuario(Long idUsuario) { this.id = idUsuario; }
>>>>>>> 1979036 (feat(rastreio): implementa logica de eventos (entity, dto, service e repository))
    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }

    public void setSenha(String senha) { this.senha = senha; }
    public PerfisAcesso getRole() { return role; }

}
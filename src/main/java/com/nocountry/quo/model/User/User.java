package com.nocountry.quo.model.User;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "\"users\"")
@Entity(name = "User")
@Getter
@Setter  // Lombok se encarga de generar los getters y setters automáticamente
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_sequence", allocationSize = 1)
    private Long id;

    @NotBlank
    @Column(name = "username")
    private String username;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotBlank
    @Column(name = "mail")
    private String mail;

    // Campo añadido para almacenar el teléfono del usuario
    @Column(name = "phone")
    private String phone;

    // Campo añadido para almacenar la URL del avatar del usuario
    @Column(name = "avatar")
    private String avatar;

    // Campo para marcar la cuenta como activa o inactiva
    @Column(name = "active", nullable = false)
    private Boolean active = true;  // Valor por defecto: true (activo)



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active == null || active; // La cuenta estará habilitada si 'active' es null o true
    }
}

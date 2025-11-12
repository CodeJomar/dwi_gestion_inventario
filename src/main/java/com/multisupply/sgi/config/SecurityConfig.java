package com.multisupply.sgi.config;

import com.multisupply.sgi.usuarios.services.CustomUDS;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final CustomUDS userDetailsService;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/scripts/**", "/styles/**", "/img/**")
                .permitAll()
                
                .requestMatchers("/", "/registro", "/cambiar-password")
                .permitAll()
                
                .requestMatchers( "/auth/dashboard", "/auth/productos", "/auth/ordenes",  "/auth/perfil")
                .hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO")

                .requestMatchers(HttpMethod.GET, "/api/ordenes/**") // -----------------
                .hasAnyAuthority("ROLE_ADMINISTRADOR", "ROLE_EMPLEADO") //--------------

                .requestMatchers("/auth/usuarios", "/auth/reportes")
                .hasAnyAuthority("ROLE_ADMINISTRADOR")
                
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    response.sendRedirect("/auth/dashboard");
                })
                .failureUrl("/?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?logoutExitoso")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .userDetailsService(userDetailsService)
            .build();
    }

}

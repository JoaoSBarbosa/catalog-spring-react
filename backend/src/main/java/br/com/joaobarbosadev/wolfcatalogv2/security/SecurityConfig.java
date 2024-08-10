package br.com.joaobarbosadev.wolfcatalogv2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
/**
 * Classe de configuração de segurança da aplicação.
 * Configura a segurança usando Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Define a cadeia de filtros de segurança HTTP.
     *
     * @param http O objeto HttpSecurity usado para configurar a segurança.
     * @return A cadeia de filtros de segurança configurada.
     * @throws Exception Em caso de erro durante a configuração.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Permite todas as requisições sem necessidade de autenticação
                .authorizeRequests(auth -> auth.anyRequest().permitAll())
                // Habilita a autenticação básica (usuário/senha via popup no navegador)
                .httpBasic(Customizer.withDefaults());
        // Retorna a cadeia de filtros configurada
        return http.build();
    }

    /**
     * Define o customizador de segurança da web.
     *
     * Este método configura o Spring Security para ignorar todas as requisições,
     * ou seja, todas as URLs são permitidas sem passar pelas verificações de segurança.
     *
     * @return O customizador de segurança da web configurado.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/**");
    }
}

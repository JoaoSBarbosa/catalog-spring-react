package br.com.joaobarbosadev.wolfcatalogv2.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Configuração do servidor de recursos.
 * Esta classe define as regras de segurança para proteger as APIs da aplicação usando tokens JWT.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    // Injeta o token store que será usado para validar e analisar o token JWT nas requisições.
    @Autowired
    private JwtTokenStore tokenStore;
    // Define as rotas públicas que podem ser acessadas sem autenticação.
    private static final String[] PUBLIC = {"/oauth/token"};
    // Define as rotas que podem ser acessadas por operadores ou administradores.
    private static final String[] OPERATOR_OR_ADMIN = {"/products/**", "/categories/**"};
    // Define as rotas que podem ser acessadas apenas por administradores.
    private static final String[] ADMIN = {"/users/**"};


    /**
     * Configura o servidor de recursos para usar o token store JWT.
     * <p>
     * Este método garante que o servidor de recursos consiga decodificar e validar os tokens JWT
     * nas requisições feitas à aplicação.
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }

    /**
     * Configura as permissões de acesso às rotas da aplicação.
     * <p>
     * Este método define quais URLs podem ser acessadas por quais papéis (roles).
     * As rotas públicas não requerem autenticação, enquanto outras rotas exigem permissões específicas.
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(PUBLIC).permitAll() // Permite acesso irrestrito às rotas públicas (Array PUBLIC).
                .antMatchers(HttpMethod.GET, OPERATOR_OR_ADMIN).permitAll() // Permite GET para rotas do array OPERATOR_OR_ADMIN.
                .antMatchers(OPERATOR_OR_ADMIN).hasAnyRole("OPERATOR", "ADMIN") // Restringe acesso a rotas do array OPERATOR_OR_ADMIN.
                .antMatchers(ADMIN).hasAnyRole("ADMIN") // Restringe acesso apenas a administradores nas rotas configurada no array ADMIN.
                .anyRequest().authenticated();
        super.configure(http);
    }
}

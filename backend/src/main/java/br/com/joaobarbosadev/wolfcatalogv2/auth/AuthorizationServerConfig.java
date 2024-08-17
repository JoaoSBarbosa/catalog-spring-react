package br.com.joaobarbosadev.wolfcatalogv2.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Classe de configuração do servidor de autorização.
 * Esta classe define como o servidor de autorização OAuth2 deve funcionar,
 * incluindo detalhes de segurança, clientes autorizados e endpoints de token.
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;
    @Value("${jwt.duration}")
    private Integer jwtDuration;
    @Autowired
    private AuthenticationManager authenticationManager; // Gerenciador de autenticação que processa autenticações

    @Autowired
    @Qualifier("customAccessTokenConverter")
    private JwtAccessTokenConverter jwtAccessTokenConverter; // Conversor que manipula tokens JWT

    @Autowired
    private JwtTokenStore tokenStore; // Armazena tokens JWT gerados pelo servidor de autorização

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Codificador de senhas para clientes OAuth2

    /**
     * Configura a segurança do servidor de autorização.
     * Define as permissões de acesso para endpoints de token.
     *
     * @param security O objeto de configuração de segurança do servidor de autorização.
     * @throws Exception Em caso de erro na configuração de segurança.
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()") // Permite acesso público à chave de token
                .checkTokenAccess("isAuthenticated()"); // Requer autenticação para verificar tokens
    }

    /**
     * Configura os detalhes dos clientes que podem acessar o servidor de autorização.
     * Neste caso, a configuração é feita em memória, com um cliente específico.
     *
     * @param clients O objeto de configuração de clientes.
     * @throws Exception Em caso de erro na configuração dos clientes.
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId) // Define o ID do cliente
                .secret(passwordEncoder.encode(clientSecret)) // Define a senha secreta do cliente
                .authorizedGrantTypes("password") // Define o tipo de concessão "password" para autenticação
                .scopes("read", "write") // Define os escopos de acesso permitidos
                .accessTokenValiditySeconds(jwtDuration); // Define a validade do token de acesso para 24 horas
    }

    /**
     * Configura os endpoints do servidor de autorização.
     * Define como os tokens serão gerados e armazenados, e quem gerencia a autenticação.
     *
     * @param endpoints O objeto de configuração dos endpoints do servidor de autorização.
     * @throws Exception Em caso de erro na configuração dos endpoints.
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager) // Define quem gerencia a autenticação
                .tokenStore(tokenStore) // Define onde os tokens serão armazenados
                .accessTokenConverter(jwtAccessTokenConverter); // Define o conversor que processa os tokens JWT
    }
}

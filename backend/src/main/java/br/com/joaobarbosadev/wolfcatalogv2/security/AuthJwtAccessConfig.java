package br.com.joaobarbosadev.wolfcatalogv2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class AuthJwtAccessConfig {

    /**
     *
     * @return Objetos capazes de acesso um token JWT (LER, MODIFICAR, ETC)
     */
    @Bean(name = "customAccessTokenConverter")
    public JwtAccessTokenConverter accessTokenConverter(){
        // REGISTRANDO O TOKEN E RETORNANDO
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey("wolf-catalog-v2");
        return tokenConverter;
    }

    @Bean
    public JwtTokenStore tokenStore(){
        return new JwtTokenStore(accessTokenConverter());
    }
}

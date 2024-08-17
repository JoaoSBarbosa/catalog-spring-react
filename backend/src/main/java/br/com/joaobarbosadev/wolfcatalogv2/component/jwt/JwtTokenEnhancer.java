package br.com.joaobarbosadev.wolfcatalogv2.component.jwt;

import br.com.joaobarbosadev.wolfcatalogv2.entities.User;
import br.com.joaobarbosadev.wolfcatalogv2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Componente responsável por adicionar informações extras ao token JWT.
 * Este `TokenEnhancer` permite incluir dados adicionais no payload do token,
 * como o nome, ID e imagem do usuário autenticado.
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {
    // Repositório para buscar informações do usuário no banco de dados.
    @Autowired UserRepository userRepository;

    /**
     * Método responsável por adicionar informações extras ao token JWT.
     *
     * @param oAuth2AccessToken O token de acesso original.
     * @param oAuth2Authentication A autenticação do usuário.
     * @return O token de acesso modificado, com as informações adicionais.
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        // Busca o usuário no banco de dados pelo email (nome de usuário)
        User user = userRepository.findByEmail(oAuth2Authentication.getUserAuthentication().getName());

        // Cria um mapa para armazenar as informações adicionais
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("userFirstName", user.getFirstName());    // Adiciona o primeiro nome do usuário
        additionalInfo.put("userId", user.getId());                 // Adiciona o ID do usuário
        additionalInfo.put("userImage", user.getUriImage());       // Adiciona a URL da imagem do usuário

        // Converte o token original para adicionar as novas informações
        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) oAuth2AccessToken;
        token.setAdditionalInformation(additionalInfo); // Define as informações adicionais no token
        return oAuth2AccessToken;
    }
}

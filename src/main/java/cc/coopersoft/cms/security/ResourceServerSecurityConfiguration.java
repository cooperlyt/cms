package cc.coopersoft.cms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class ResourceServerSecurityConfiguration {

    @Bean
    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .authorizeExchange()
                .pathMatchers("/mgr/**").hasAuthority("SCOPE_CMS")
                .anyExchange().permitAll().and().csrf().disable()
                .oauth2ResourceServer().jwt();
        return http.build();
    }
}

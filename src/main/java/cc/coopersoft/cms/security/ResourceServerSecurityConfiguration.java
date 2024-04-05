package cc.coopersoft.cms.security;

import org.springframework.context.annotation.Bean;


//@EnableWebFluxSecurity
public class ResourceServerSecurityConfiguration {

//    @Bean
//    SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {
//        http
//                .authorizeExchange()
//                .pathMatchers("/mgr/**").hasAuthority("SCOPE_CMS")
//                .anyExchange().permitAll().and().csrf().disable()
//                .oauth2ResourceServer().jwt();
//        return http.build();
//    }
}

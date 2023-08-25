package kr.co.comes.intranet.infra.config;


import kr.co.comes.intranet.api.auth.AuthService;
import kr.co.comes.intranet.infra.resolver.AuthArgumentResolver;
import kr.co.comes.intranet.infra.resolver.UserSessionArgumentResolver;
import kr.co.comes.intranet.infra.resolver.UserSessionArgumentResolverMock;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@AllArgsConstructor
public class ArgumentResolverDefinition {

    private final AuthService authService;

    @Bean
    @Profile("!local")
    public AuthArgumentResolver userSessionArgumentResolver() {
        return new UserSessionArgumentResolver(authService);
    }

    @Bean
    @Profile("local")
    public AuthArgumentResolver userSessionArgumentResolverMock() {
        return new UserSessionArgumentResolverMock(authService);
    }

}

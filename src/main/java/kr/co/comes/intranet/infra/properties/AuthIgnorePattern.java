package kr.co.comes.intranet.infra.properties;

import kr.co.comes.intranet.common.dto.Parttern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(value = "auth")
@AllArgsConstructor
@Getter
@Setter
public class AuthIgnorePattern {
    List<Parttern> ignorePatterns;
}

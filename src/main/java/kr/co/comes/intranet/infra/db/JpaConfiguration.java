package kr.co.comes.intranet.infra.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Configuration
public class JpaConfiguration {

    @Bean
    public DateTimeProvider baseDateTimeProvider() {
        return () -> Optional.of(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }

    public static final String TRUNCATE_FRACTIONAL_SECONDS = "baseDateTimeProvider";
}

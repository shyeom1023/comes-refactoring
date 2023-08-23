package kr.co.comes.intranet.infra.properties;

import com.zaxxer.hikari.HikariConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(value = "rdb.hikari")
@Data
public class HikariProperties {
    HikariConfig master;
    HikariConfig slave;
}

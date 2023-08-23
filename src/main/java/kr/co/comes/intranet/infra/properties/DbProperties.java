package kr.co.comes.intranet.infra.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(value = "db")
@Data
public class DbProperties {

    DbProperty master;
    DbProperty slave;

    @Data
    public static class DbProperty {
        String url;
        String database;
        String username;
        String password;

    }
}

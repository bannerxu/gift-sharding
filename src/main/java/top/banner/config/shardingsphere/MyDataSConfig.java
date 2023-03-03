package top.banner.config.shardingsphere;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.shardingsphere.datasource.master")
public class MyDataSConfig {
    private String jdbcUrl;

    private String username;

    private String password;

    private String driverClassName;
}
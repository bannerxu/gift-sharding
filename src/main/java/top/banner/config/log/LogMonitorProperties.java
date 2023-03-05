package top.banner.config.log;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("log.monitor")
public class LogMonitorProperties {
    private String[] aopPatterns;
}
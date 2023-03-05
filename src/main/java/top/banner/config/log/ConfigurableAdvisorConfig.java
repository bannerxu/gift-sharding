package top.banner.config.log;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty("log.monitor.aop-patterns")
public class ConfigurableAdvisorConfig {

    @Bean
    public Advice advice() {
        return new LogAdvice();
    }

    @Bean
    public Advisor advisor(LogMonitorProperties logMonitorProperties) {
        return new RegexpMethodPointcutAdvisor(logMonitorProperties.getAopPatterns(), advice());
    }
}
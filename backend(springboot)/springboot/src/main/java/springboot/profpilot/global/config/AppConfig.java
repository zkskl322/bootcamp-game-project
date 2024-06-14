package springboot.profpilot.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {

    @Bean
    public ExecutorService executorService() {
        int numThreads = Runtime.getRuntime().availableProcessors();
        return Executors.newFixedThreadPool(numThreads);
    }

    @Bean
    public Map<String, ExecutorService> gameExecutors() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<String, Integer> gameToThreadMap() {
        return new ConcurrentHashMap<>();
    }
}

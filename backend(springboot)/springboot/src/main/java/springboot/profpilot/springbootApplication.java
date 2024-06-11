package springboot.profpilot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class springbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(springbootApplication.class, args);
	}
}

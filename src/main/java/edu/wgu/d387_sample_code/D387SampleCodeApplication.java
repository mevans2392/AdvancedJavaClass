package edu.wgu.d387_sample_code;

import edu.wgu.d387_sample_code.perfassess.TimeZonesService;
import edu.wgu.d387_sample_code.perfassess.WelcomeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

@SpringBootApplication
public class D387SampleCodeApplication {
	static ExecutorService messageExecutor = newFixedThreadPool(5);
	public static void main(String[] args) {
		SpringApplication.run(D387SampleCodeApplication.class, args);

	}

	@Bean
	CommandLineRunner run(WelcomeService welcomeService) {
		return args -> {
			messageExecutor.execute(() -> System.out.println(welcomeService.getLocalizedMessage("en", "US")));
			messageExecutor.execute(() -> System.out.println(welcomeService.getLocalizedMessage("fr", "CN")));
		};
	}

}

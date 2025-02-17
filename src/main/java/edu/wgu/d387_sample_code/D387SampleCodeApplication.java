package edu.wgu.d387_sample_code;

import edu.wgu.d387_sample_code.perfassess.WelcomeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.newFixedThreadPool;

@SpringBootApplication
public class D387SampleCodeApplication {
	static ExecutorService messageExecutor = newFixedThreadPool(5);
	public static void main(String[] args) {
		SpringApplication.run(D387SampleCodeApplication.class, args);


/*
		Properties properties = new Properties();

		messageExecutor.execute(() -> {
			try {
				InputStream streamEn = new ClassPathResource("welcome_en_US.properties").getInputStream();
				properties.load(streamEn);
				System.out.println(properties.getProperty("welcome"));
			} catch (Exception e) { e.printStackTrace(); }
		});


		messageExecutor.execute(() -> {
			try {
				InputStream streamFr = new ClassPathResource("welcome_fr_CN.properties").getInputStream();
				properties.load(streamFr);
				System.out.println(properties.getProperty("welcome"));
			} catch (Exception e) { e.printStackTrace(); }
		});

 */
	}

	@Bean
	CommandLineRunner run(WelcomeService welcomeService) {
		return args -> {
			messageExecutor.execute(() -> System.out.println(welcomeService.getLocalizedMessage("en", "US")));
			messageExecutor.execute(() -> System.out.println(welcomeService.getLocalizedMessage("fr", "CN")));
		};
	}

}

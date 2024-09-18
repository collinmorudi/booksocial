package com.collin.booksocial;

import com.collin.booksocial.repository.RoleRepository;
import com.collin.booksocial.role.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * The BooksocialApiApplication class is the entry point for the Booksocial API application.
 * It is marked as a Spring Boot application and enables JPA auditing and asynchronous operations.
 *
 * This class also includes a CommandLineRunner bean that initializes the database
 * with a default role if it does not already exist.
 *
 * Annotations:
 * - @SpringBootApplication: Indicates that this is a Spring Boot application.
 * - @EnableJpaAuditing: Enables JPA auditing and specifies the auditorAware bean.
 * - @EnableAsync: Enables Spring's asynchronous method execution capability.
 */
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
public class BooksocialApiApplication {

	/**
	 * The main method which serves as the entry point for the Booksocial API application.
	 *
	 * @param args an array of command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(BooksocialApiApplication.class, args);
	}

	/**
	 * CommandLineRunner bean that initializes the database with a default role if it does not already exist.
	 *
	 * @param roleRepository the repository used to access and modify roles in the database.
	 * @return a CommandLineRunner that checks for the existence of the "USER" role and creates it if necessary.
	 */
	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName("USER").isEmpty()) {
				roleRepository.save(
						Role.builder().name("USER").build()
				);
			}
		};
	}

}

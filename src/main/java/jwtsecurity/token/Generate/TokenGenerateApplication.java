package jwtsecurity.token.Generate;

import jwtsecurity.token.Generate.Entity.Role;
import jwtsecurity.token.Generate.Entity.User;
import jwtsecurity.token.Generate.Repository.RoleRepository;
import jwtsecurity.token.Generate.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableMethodSecurity
@AllArgsConstructor
public class TokenGenerateApplication {
 private final UserRepository userRepository;
 private  final PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(TokenGenerateApplication.class, args);

	}
		@Bean
		public CommandLineRunner runner(RoleRepository roleRepository) {

			return args -> {

				// Wait for schema creation
				try {
					// Checking if role exists, if not create them
					if (roleRepository.findByName("ROLE_USER").isEmpty()) {
						roleRepository.save(Role.builder().name("ROLE_USER").build());
					}
					if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
						roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
					}

					// Check if the admin user exists, if not create it
					if (!userRepository.existsByEmail("balaj6108@gmail.com")) {
						var userRole = roleRepository.findByName("ROLE_ADMIN")
								.orElseThrow(() -> new IllegalStateException("ROLE USER was not initiated"));

						var admin = User.builder()
								.firstname("balaji")
								.lastname("dk")
								.email("balaj6108@gmail.com")
								.password(passwordEncoder.encode("bala@1234"))
								.accountLocked(false)
								.enabled(true)
								.roles(List.of(userRole))
								.build();
						userRepository.save(admin);
					}
				} catch (Exception e) {
					System.err.println("Error during initialization: " + e.getMessage());
					e.printStackTrace();
				}
			};
		}

	}




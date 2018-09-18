package warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication

public class MagazynApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagazynApplication.class, args);
	}
}

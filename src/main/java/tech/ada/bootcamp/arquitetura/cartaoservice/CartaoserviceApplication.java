package tech.ada.bootcamp.arquitetura.cartaoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CartaoserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartaoserviceApplication.class, args);
	}

}

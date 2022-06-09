package ru.yandex.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ru.yandex.goods.dao")
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}

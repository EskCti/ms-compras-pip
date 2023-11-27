package com.eskcti.mscompra;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class MscompraApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscompraApplication.class, args);
	}

}

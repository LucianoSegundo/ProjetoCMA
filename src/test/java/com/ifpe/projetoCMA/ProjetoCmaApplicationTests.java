package com.ifpe.projetoCMA;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@ComponentScan(basePackages = "com.ifpe.projetoCMA")

@SpringBootTest
@ActiveProfiles("test")
class ProjetoCmaApplicationTests {

	@Test
	void contextLoads() {
	}

}

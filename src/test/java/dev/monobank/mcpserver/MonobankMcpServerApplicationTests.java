package dev.monobank.mcpserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"MONOBANK_API_TOKEN=dummy-test-token"
})
class MonobankMcpServerApplicationTests {

	@Test
	void contextLoads() {
	}

}

package com.rush.banking.api.gateway;

import com.rush.banking.api.gateway.constant.Constants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = CloudApiGatewayApplication.class)
class CloudApiGatewayApplicationTests {

	@Value("${local.server.port}")
	private int port;

	@Test
	@Order(1)
	void authorityLoads() {
		Assertions.assertThat(new TestRestTemplate().getForEntity("http://localhost:" + port + "/authority-service-fallBack", String.class).getBody()).isEqualTo(Constants.AUTHORITY_FALLBACK);
	}

	@Test
	@Order(2)
	void userLoads() {
		Assertions.assertThat(new TestRestTemplate().getForEntity("http://localhost:" + port + "/user-service-fallBack", String.class).getBody()).isEqualTo(Constants.USER_FALLBACK);
	}
}

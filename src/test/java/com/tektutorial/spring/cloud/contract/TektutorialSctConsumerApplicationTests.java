package com.tektutorial.spring.cloud.contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(
		ids = {"com.tektutorial.spring.cloud.contract:tekturial-sct-producer:+:stubs:8100"},
		stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class TektutorialSctConsumerApplicationTests {


/*	@Rule
	public StubRunnerRule stubRunnerRule = new StubRunnerRule()
			.downloadStub("com.tektutorial.spring.cloud.contract",
					"tekturial-sct-producer",
					"0.0.1-SNAPSHOT",
					"stubs")
			.withPort(8100)
			.stubsMode(StubRunnerProperties.StubsMode.LOCAL);*/

	@Test
	public void test()
			throws Exception {

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> personResponseEntity =
				restTemplate.getForEntity("http://localhost:8100/fraudcheck?loanAmount=99999", String.class);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = mapper.readValue(personResponseEntity.getBody(), Map.class);
		Assert.assertEquals(map.get("fraudCheckStatus"), "FRAUD");
	}

	@Test
	public void testPositive()
			throws Exception {

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> personResponseEntity =
				restTemplate.getForEntity("http://localhost:8100/fraudcheck?loanAmount=9999", String.class);

		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = mapper.readValue(personResponseEntity.getBody(), Map.class);
		Assert.assertEquals(map.get("fraudCheckStatus"), "Not-Fraud");

	}
}

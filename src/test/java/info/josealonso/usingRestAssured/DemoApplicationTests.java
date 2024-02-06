package info.josealonso.usingRestAssured;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeAll
	public static void initialiseRestAssuredMockMvcStandalone() {
		RestAssuredMockMvc.webAppContextSetup;
//		RestAssuredMockMvc.standaloneSetup(new TodosController());
	}

}

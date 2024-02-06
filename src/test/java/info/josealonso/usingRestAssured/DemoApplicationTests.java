package info.josealonso.usingRestAssured;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@BeforeAll
	public static void initialiseRestAssuredMockMvcStandalone() {
		RestAssuredMockMvc.standaloneSetup(new TodosController());
	}

}

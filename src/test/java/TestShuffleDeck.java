
import static com.jayway.restassured.RestAssured.expect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;

import groovyx.net.http.ContentType;
/**
 * Created by Alican on 8/15/17.
 */
public class TestShuffleDeck {
	private static final Log LOG = LogFactory.getLog(TestShuffleDeck.class);

	@Before
	public void setUp() {
		RestAssured.basePath = "http://localhost:8080/rest/player";
	}

	@Test
	public void ensurePutDeckWorks() {
		LOG.info("****Start****ensurePutDeckWorks");

		expect().statusCode(201).contentType(ContentType.JSON).when().put("/deck/vegas");
		LOG.info("Vegas deck created");
		expect().statusCode(201).contentType(ContentType.JSON).when().put("/deck/vegas2");
		LOG.info("Vegas2 deck created");
		LOG.info("****End****ensurePutDeckWorks");
	}

	@Test
	public void ensureGetDeckWorks() {
		LOG.info("****Start****ensureGetDeckWorks");
		expect().statusCode(200).contentType(ContentType.JSON).when().get("/deck/vegas");
		LOG.info("Vegas deck get works");
		LOG.info("****End****ensureGetDeckWorks");
	}

	@Test
	public void ensureShuffleDeckWorks() {
		LOG.info("****Start****ensureShuffleDeckWorks");
		expect().statusCode(200).contentType(ContentType.JSON).when().post("/shuffle/vegas");
		LOG.info("Vegas deck shuffle works");
		LOG.info("****End****ensureShuffleDeckWorks");
	}

	@Test
	public void ensureGetAllDecksWorks() {
		LOG.info("****Start****ensureGetAllDecksWorks");
		expect().statusCode(200).contentType(ContentType.JSON).when().get("/alldecks");
		LOG.info("****End****ensureGetAllDecksWorks");
	}

	@Test
	public void ensureDeleteDeckWorks() {
		LOG.info("****Start****ensureDeleteDeckWorks");
		expect().statusCode(200).contentType(ContentType.TEXT).when().delete("/deck/vegas2");
		LOG.info("Vegas2 deck deleted");
		expect().statusCode(404).contentType(ContentType.TEXT).when().get("/deck/vegas2");
		LOG.info("****End****ensureDeleteDeckWorks");
	}

}

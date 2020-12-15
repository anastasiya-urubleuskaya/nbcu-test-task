package epam.com.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ApiTests {

    private static final Logger logger = LogManager.getLogger(ApiTests.class);
    private static final String BASE_PATH = "https://jsonplaceholder.typicode.com";
    private Response response;
    private int postId = 3;

    @BeforeTest
    public void setUp() {
        RestAssured.baseURI = BASE_PATH;
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        logger.info("Checking \"" + method.getName() + "\" functionality");
        logger.info("Thread #" + Thread.currentThread().getId());
    }

    @Test
    public void getPosts() {

        response = given().get("/posts").
                then().log().body().assertThat().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("response/sampleArray.json")).
                body("size()", equalTo(100)).
                extract().response();

    }

    @Test
    public void getPostById() {

        given().basePath("/posts").
                pathParam("id", postId).
                when().get("/{id}").
                then().log().body().assertThat().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("response/sample.json")).
                body("id", equalTo(postId));
    }

    @Test
    public void getPostCommentsById() {

        response = given().basePath("/posts").
                pathParam("id", postId).
                when().get("/{id}/comments").
                then().log().body().assertThat().
                statusCode(200).
                extract().response();
        List<Integer> ids = response.path("postId");
        logger.info("Post #" + postId + " has " + ids.size() + " comments");
        ids.forEach(id -> assertThat(id, equalTo(postId)));
    }

    @Test
    public void getPostCommentsByQueryId() {

        response = given().queryParam("postId", postId).
                when().get("/comments").
                then().log().body().assertThat().
                statusCode(200).
                extract().response();
        List<Integer> ids = response.path("postId");
        logger.info("Post #" + postId + " has " + ids.size() + " comments");
        ids.forEach(id -> assertThat(id, equalTo(postId)));
    }

    @Test
    public void createPost() {

        String body = "{\"title\": \"%s\",\"body\": \"%s\",\"userId\": %d}";

        given().request().body(String.format(body, "silver", "is my lovely cat", 13)).
                header("Content-type", "application/json; charset=UTF-8").
                when().post("/posts").
                then().log().body().assertThat().
                statusCode(201).
                body("title", equalTo("silver")).
                body("body", equalTo("is my lovely cat")).
                body("userId", equalTo(13)).
                body("id", equalTo(101));
    }

    @Test
    public void updatePost() {

        String body = "{\"id\": %d, \"title\": \"%s\",\"userId\": %d}";

        given().request().body(String.format(body, postId, "silver", 1)).
                contentType(ContentType.JSON).
                when().put("/posts/" + postId).
                then().log().body().assertThat().
                statusCode(200).
                body("title", equalTo("silver")).
                body("userId", equalTo(1)).
                body("$", not(hasKey("body")));
    }

    @Test
    public void patchPost() {

        given().request().body("{\"body\": \"newBody\"}").
                when().patch("/posts/" + postId).
                then().log().body().assertThat().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("response/sample.json")).
                body("body", equalTo("newBody"));
    }

    @Test
    public void deletePost() {

        response = given().when().delete("/posts/" + postId).
                then().log().body().assertThat().
                statusCode(200).
                extract().response();

        assertThat(response.asString(), equalTo("{}"));
    }
}

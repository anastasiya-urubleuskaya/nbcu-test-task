package epam.com.tests;

import io.restassured.response.Response;
import org.springframework.web.bind.annotation.RequestMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class GetPostTest extends BaseApiTest{

    @Test
    public void getPosts() {

        Response response = restApiClient.sendRequest(RequestMethod.GET, "/posts", null, null, null, null);

        response.then().assertThat().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("response/sampleArray.json")).
                body("size()", equalTo(100));
    }

    @Parameters({"postId"})
    @Test
    public void getPostById(int postId) {

        HashMap<String, String> pathParams = new HashMap<>();
        pathParams.put("id", String.valueOf(postId));
        Response response = restApiClient.sendRequest(RequestMethod.GET, "/posts/{id}", null, null, null, pathParams);

        response.then().assertThat().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("response/sample.json")).
                body("id", equalTo(postId));
    }
}

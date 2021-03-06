package epam.com.tests;

import io.restassured.response.Response;
import org.springframework.web.bind.annotation.RequestMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetCommentsTest extends BaseApiTest{

//    retrieving comments by valid id (path parameter)
    @Parameters({"postId"})
    @Test(groups = {"positive"})
    public void getPostCommentsByPathId(int postId) {

        HashMap<String, String> pathParams = new HashMap<>();
        pathParams.put("id", String.valueOf(postId));
        Response response = restApiClient.sendRequest(RequestMethod.GET, "/posts/{id}/comments", null, null, null, pathParams);

        response.then().assertThat().statusCode(200);
        List<Integer> ids = response.path("postId");
        logger.info("Post #" + postId + " has " + ids.size() + " comments");
        ids.forEach(id -> assertThat(id, equalTo(postId)));
    }

//    retrieving comments by invalid id (path parameter)
    @Parameters({"invalidPostId"})
    @Test(groups = {"negative"})
    public void getPostCommentsByInvalidPathId(int postId) {

        HashMap<String, String> pathParams = new HashMap<>();
        pathParams.put("id", String.valueOf(postId));
        Response response = restApiClient.sendRequest(RequestMethod.GET, "/posts/{id}/comments", null, null, null, pathParams);

        response.then().assertThat().statusCode(404);
    }

//    retrieving comments by valid id (query parameter)
    @Parameters({"postId"})
    @Test(groups = {"positive"})
    public void getPostCommentsByQueryId(int postId) {

        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("postId", String.valueOf(postId));
        Response response = restApiClient.sendRequest(RequestMethod.GET, "/comments", null, null, queryParams, null);

        response.then().assertThat().statusCode(200);
        List<Integer> ids = response.path("postId");
        logger.info("Post #" + postId + " has " + ids.size() + " comments");
        ids.forEach(id -> assertThat(id, equalTo(postId)));
    }

//    retrieving comments by invalid id (query parameter)
    @Parameters({"invalidPostId"})
    @Test(groups = {"negative"})
    public void getPostCommentsByInvalidQueryId(int postId) {

        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("postId", String.valueOf(postId));
        Response response = restApiClient.sendRequest(RequestMethod.GET, "/comments", null, null, queryParams, null);

        response.then().assertThat().statusCode(404);
    }
}

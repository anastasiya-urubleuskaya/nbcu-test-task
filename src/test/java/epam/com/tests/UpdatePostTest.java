package epam.com.tests;

import epam.com.model.Post;
import epam.com.utils.JsonUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.web.bind.annotation.RequestMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UpdatePostTest extends BaseApiTest{

//    update post happy path
    @Parameters({"postId"})
    @Test(groups = {"positive"})
    public void updatePost(int postId) {

        String body = JsonUtil.getJsonFromObject(new Post.PostBuilder().setId(postId).setTitle("silver").setUserId(777).build());
        HashMap<String, String> pathParams = new HashMap<>();
        pathParams.put("id", String.valueOf(postId));

        Response response = restApiClient.sendRequest(RequestMethod.PUT, "/posts/{id}", body, null, null, pathParams);

        response.then().assertThat().
                statusCode(200).
                body("title", equalTo("silver")).
                body("userId", equalTo(777)).
                body("$", not(hasKey("body")));
    }

//    update post invalid id
    @Parameters({"postId", "invalidPostId"})
    @Test(groups = {"negative"})
    public void updatePostInvalidId(int postId, int invalidPostId) {

        String body = JsonUtil.getJsonFromObject(new Post.PostBuilder().setId(postId).build());
        HashMap<String, String> pathParams = new HashMap<>();
        pathParams.put("id", String.valueOf(invalidPostId));

        Response response = restApiClient.sendRequest(RequestMethod.PUT, "/posts/{id}", body, null, null, pathParams);
        response.then().assertThat().statusCode(404);
    }
}

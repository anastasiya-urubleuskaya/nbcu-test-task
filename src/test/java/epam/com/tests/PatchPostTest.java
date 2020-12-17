package epam.com.tests;

import epam.com.model.Post;
import epam.com.utils.JsonUtil;
import io.restassured.response.Response;
import org.springframework.web.bind.annotation.RequestMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class PatchPostTest extends BaseApiTest{

    @Parameters({"postId"})
    @Test
    public void patchPost(int postId) {

        String body = JsonUtil.getJsonFromObject(new Post.PostBuilder().setBody("red body").build());
        HashMap<String, String> pathParams = new HashMap<>();
        pathParams.put("id", String.valueOf(postId));

        Response response = restApiClient.sendRequest(RequestMethod.PATCH, "/posts/{id}", body, null, null, pathParams);

        response.then().assertThat().
                statusCode(200).
                body(matchesJsonSchemaInClasspath("response/sample.json")).
                body("body", equalTo("red body"));
    }
}
